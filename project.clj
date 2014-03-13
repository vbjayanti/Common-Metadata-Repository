(defproject nasa-cmr/cmr-common-lib "0.1.1-SNAPSHOT"
  :description "Provides common utility code for CMR projects."
  :url "***REMOVED***projects/CMR/repos/cmr-common-lib/browse"
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [com.taoensso/timbre "3.1.3"]
                 [ring/ring-jetty-adapter "1.2.1"]
                 [org.clojure/test.check "0.5.7"]]

  :profiles
  {:dev {:dependencies [[org.clojure/tools.namespace "0.2.4"]
                        [org.clojars.gjahad/debug-repl "0.3.3"]]
         :source-paths ["src" "dev" "test"]}})


