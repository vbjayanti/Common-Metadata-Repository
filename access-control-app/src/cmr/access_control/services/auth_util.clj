(ns cmr.access-control.services.auth-util
  (:require [cmr.common.services.errors :as errors]
            [cmr.acl.core :as acl]
            [cmr.common-app.services.search.query-model :as qm]
            [cmr.common-app.services.search.group-query-conditions :as gc]
            [cmr.common-app.services.search.query-execution :as qe]))

(defn- get-system-acls
  "Returns ACLs which grant the given permission to the context user for system-level groups."
  [context permission]
  (seq (acl/get-permitting-acls context :system-object "GROUP" permission)))

(defn- get-all-provider-acls
  "Returns all ACLs that grant given permission to context user for any provider-level groups."
  [context permission]
  (acl/get-permitting-acls context :provider-object "GROUP" permission))

(defn- get-provider-acls
  "Returns any ACLs that grant the given permission to the context user for the specified group object."
  [context permission group]
  (when-let [provider-id (:provider-id group)]
    (when-not (= "CMR" provider-id)
      (seq
        (filter #(= provider-id (-> % :provider-object-identity :provider-id))
                (get-all-provider-acls context permission))))))

(defn- get-instance-acls
  "Returns any ACLs that grant the given permission to the context user on a specific group by its :legacy-guid."
  [context permission group]
  (when-let [target-guid (:legacy-guid group)]
    (seq
      (filter #(= target-guid (-> % :single-instance-object-identity :target-guid))
              (acl/get-permitting-acls context :single-instance-object "GROUP" permission)))))

(defn- get-group-acls
  "Returns any group ACLs that grant the context user the given permission on group."
  [context permission group]
  (or (get-instance-acls context permission group)
      (get-provider-acls context permission group)
      (get-system-acls context permission)))

(defn- describe-group
  [group]
  (let [{:keys [provider-id]} group]
    (if (and provider-id (not= "CMR" provider-id))
      (format "access control group [%s] in provider [%s]" (:name group) provider-id)
      (format "system-level access control group [%s]" (:name group)))))

(defn- throw-group-permission-error
  [permission group]
  (errors/throw-service-error
    :unauthorized
    (format "You do not have permission to %s %s."
            (name permission)
            (describe-group group))))

(defn- verify-group-permission
  "Throws a permission service error if no ACLs exist that grant the desired permission to the
  context user on group."
  [context permission group]
  (when-not (get-group-acls context permission group)
    (throw-group-permission-error permission group)))

(defn verify-can-create-group
  "Throws a service error if the context user cannot create a group under provider-id."
  [context group]
  (verify-group-permission context :create group))

(defn verify-can-read-group
  "Throws a service error if the context user cannot read the access control group represented by
   the group map."
  [context group]
  (verify-group-permission context :read group))

(defn verify-can-delete-group
  "Throws a service error of context user cannot delete access control group represented by given
   group map."
  [context group]
  (verify-group-permission context :delete group))

(defn verify-can-update-group
  "Throws service error if context user does not have permission to delete group map."
  [context group]
  (verify-group-permission context :update group))

;;; For Search/Indexing

;; The following multimethod is automatically called by the query execution service when executing a query.

(defmethod qe/add-acl-conditions-to-query :access-group
  [context query]
  (let [system-condition (when (get-system-acls context :read)
                           (qm/negated-condition (qm/exist-condition :provider-id)))
        provider-ids (map #(-> % :provider-object-identity :provider-id)
                          (get-all-provider-acls context :read))
        provider-condition (when (seq provider-ids)
                             (qm/string-conditions :provider-id provider-ids))
        acl-conditions (gc/or-conds (remove nil? [system-condition provider-condition]))]
    (update-in query [:condition] #(gc/and-conds [acl-conditions %]))))
