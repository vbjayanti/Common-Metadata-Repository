(ns cmr.umm-spec.test.umm-to-xml-mappings.iso19115-2
  "Tests to verify that ISO19115-2 is generated correctly."
  (:require [cmr.umm-spec.umm-to-xml-mappings.iso19115-2 :as iso]
            [cmr.umm-spec.models.collection :as coll]
            [clojure.test :refer :all]
            [clojure.data.xml :as x]
            [clojure.java.io :as io]
            [cmr.common.util :refer [are3]]
            [cmr.common.xml :as xml]
            [cmr.common.xml.xslt :as xslt]))

(def iso-no-use-constraints "<?xml version=\"1.0\" encoding=\"UTF-8\"?>
<gmi:MI_Metadata xmlns:gmi=\"http://www.isotc211.org/2005/gmi\" xmlns:eos=\"http://earthdata.nasa.gov/schema/eos\" xmlns:gco=\"http://www.isotc211.org/2005/gco\" xmlns:gmd=\"http://www.isotc211.org/2005/gmd\" xmlns:gml=\"http://www.opengis.net/gml/3.2\" xmlns:gmx=\"http://www.isotc211.org/2005/gmx\" xmlns:gsr=\"http://www.isotc211.org/2005/gsr\" xmlns:gss=\"http://www.isotc211.org/2005/gss\" xmlns:gts=\"http://www.isotc211.org/2005/gts\" xmlns:srv=\"http://www.isotc211.org/2005/srv\" xmlns:swe=\"http://schemas.opengis.net/sweCommon/2.0/\" xmlns:xlink=\"http://www.w3.org/1999/xlink\" xmlns:xs=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">
   <!--Other Properties, all:0, coi:0,ii:0,si:0,pli:0,pri:0,qi:0,gi:0,ci:0,dk:0,pcc:0,icc:0,scc:0-->
   <gmd:fileIdentifier>
      <gco:CharacterString>gov.nasa.echo:A minimal valid collection V 1</gco:CharacterString>
   </gmd:fileIdentifier>
   <gmd:language>
      <gco:CharacterString>eng</gco:CharacterString>
   </gmd:language>
   <gmd:characterSet>
      <gmd:MD_CharacterSetCode codeList=\"http://www.ngdc.noaa.gov/metadata/published/xsd/schema/resources/Codelist/gmxCodelists.xml#MD_CharacterSetCode\" codeListValue=\"utf8\">utf8</gmd:MD_CharacterSetCode>
   </gmd:characterSet>
   <gmd:hierarchyLevel>
      <gmd:MD_ScopeCode codeList=\"http://www.ngdc.noaa.gov/metadata/published/xsd/schema/resources/Codelist/gmxCodelists.xml#MD_ScopeCode\" codeListValue=\"series\">series</gmd:MD_ScopeCode>
   </gmd:hierarchyLevel>
   <gmd:contact gco:nilReason=\"missing\" />
   <gmd:dateStamp>
      <gco:DateTime>2016-07-06T18:15:49.058-04:00</gco:DateTime>
   </gmd:dateStamp>
   <gmd:metadataStandardName>
      <gco:CharacterString>ISO 19115-2 Geographic Information - Metadata Part 2 Extensions for imagery and gridded data</gco:CharacterString>
   </gmd:metadataStandardName>
   <gmd:metadataStandardVersion>
      <gco:CharacterString>ISO 19115-2:2009(E)</gco:CharacterString>
   </gmd:metadataStandardVersion>
   <gmd:identificationInfo>
      <gmd:MD_DataIdentification>
         <gmd:citation>
            <gmd:CI_Citation>
               <gmd:title>
                  <gco:CharacterString>MINIMAL &gt; A minimal valid collection</gco:CharacterString>
               </gmd:title>
               <gmd:date>
                  <gmd:CI_Date>
                     <gmd:date>
                        <gco:DateTime>1999-12-31T19:00:00-05:00</gco:DateTime>
                     </gmd:date>
                     <gmd:dateType>
                        <gmd:CI_DateTypeCode codeList=\"http://www.ngdc.noaa.gov/metadata/published/xsd/schema/resources/Codelist/gmxCodelists.xml#CI_DateTypeCode\" codeListValue=\"revision\">revision</gmd:CI_DateTypeCode>
                     </gmd:dateType>
                  </gmd:CI_Date>
               </gmd:date>
               <gmd:date>
                  <gmd:CI_Date>
                     <gmd:date>
                        <gco:DateTime>1999-12-31T19:00:00-05:00</gco:DateTime>
                     </gmd:date>
                     <gmd:dateType>
                        <gmd:CI_DateTypeCode codeList=\"http://www.ngdc.noaa.gov/metadata/published/xsd/schema/resources/Codelist/gmxCodelists.xml#CI_DateTypeCode\" codeListValue=\"creation\">creation</gmd:CI_DateTypeCode>
                     </gmd:dateType>
                  </gmd:CI_Date>
               </gmd:date>
               <gmd:edition>
                  <gco:CharacterString>1</gco:CharacterString>
               </gmd:edition>
               <gmd:identifier>
                  <gmd:MD_Identifier>
                     <gmd:code>
                        <gco:CharacterString>MINIMAL</gco:CharacterString>
                     </gmd:code>
                     <gmd:description>
                        <gco:CharacterString>A minimal valid collection</gco:CharacterString>
                     </gmd:description>
                  </gmd:MD_Identifier>
               </gmd:identifier>
               <gmd:otherCitationDetails>
                  <gco:CharacterString />
               </gmd:otherCitationDetails>
            </gmd:CI_Citation>
         </gmd:citation>
         <gmd:abstract>
            <gco:CharacterString>A minimal valid collection</gco:CharacterString>
         </gmd:abstract>
         <gmd:purpose gco:nilReason=\"missing\" />
         <gmd:language>
            <gco:CharacterString>eng</gco:CharacterString>
         </gmd:language>
         <gmd:characterSet>
            <gmd:MD_CharacterSetCode codeList=\"http://www.ngdc.noaa.gov/metadata/published/xsd/schema/resources/Codelist/gmxCodelists.xml#MD_CharacterSetCode\" codeListValue=\"utf8\">utf8</gmd:MD_CharacterSetCode>
         </gmd:characterSet>
         <gmd:extent>
            <gmd:EX_Extent id=\"boundingExtent\">
               <gmd:description gco:nilReason=\"unknown\" />
            </gmd:EX_Extent>
         </gmd:extent>
         <gmd:supplementalInformation />
         <gmd:processingLevel>
            <gmd:MD_Identifier>
               <gmd:code>
                  <gco:CharacterString />
               </gmd:code>
               <gmd:description>
                  <gco:CharacterString />
               </gmd:description>
            </gmd:MD_Identifier>
         </gmd:processingLevel>
      </gmd:MD_DataIdentification>
   </gmd:identificationInfo>
   <gmd:distributionInfo>
      <gmd:MD_Distribution>
         <gmd:distributor>
            <gmd:MD_Distributor>
               <gmd:distributorContact gco:nilReason=\"missing\" />
               <gmd:distributionOrderProcess>
                  <gmd:MD_StandardOrderProcess>
                     <gmd:fees gco:nilReason=\"missing\" />
                  </gmd:MD_StandardOrderProcess>
               </gmd:distributionOrderProcess>
               <gmd:distributorTransferOptions>
                  <gmd:MD_DigitalTransferOptions />
               </gmd:distributorTransferOptions>
            </gmd:MD_Distributor>
         </gmd:distributor>
      </gmd:MD_Distribution>
   </gmd:distributionInfo>
   <gmd:dataQualityInfo>
      <gmd:DQ_DataQuality>
         <gmd:scope>
            <gmd:DQ_Scope>
               <gmd:level>
                  <gmd:MD_ScopeCode codeList=\"http://www.ngdc.noaa.gov/metadata/published/xsd/schema/resources/Codelist/gmxCodelists.xml#MD_ScopeCode\" codeListValue=\"series\">series</gmd:MD_ScopeCode>
               </gmd:level>
            </gmd:DQ_Scope>
         </gmd:scope>
         <gmd:lineage>
            <gmd:LI_Lineage>
               <gmd:processStep>
                  <gmi:LE_ProcessStep>
                     <gmd:description gco:nilReason=\"unknown\" />
                  </gmi:LE_ProcessStep>
               </gmd:processStep>
            </gmd:LI_Lineage>
         </gmd:lineage>
      </gmd:DQ_DataQuality>
   </gmd:dataQualityInfo>
   <gmd:metadataMaintenance>
      <gmd:MD_MaintenanceInformation>
         <gmd:maintenanceAndUpdateFrequency>
            <gmd:MD_MaintenanceFrequencyCode codeList=\"http://www.ngdc.noaa.gov/metadata/published/xsd/schema/resources/Codelist/gmxCodelists.xml#MD_MaintenanceFrequencyCode\" codeListValue=\"irregular\">irregular</gmd:MD_MaintenanceFrequencyCode>
         </gmd:maintenanceAndUpdateFrequency>
         <gmd:maintenanceNote>
            <gco:CharacterString>Translated from ECHO using ECHOToISO.xsl Version: 1.32 (Dec. 9, 2015)</gco:CharacterString>
         </gmd:maintenanceNote>
      </gmd:MD_MaintenanceInformation>
   </gmd:metadataMaintenance>
   <gmi:acquisitionInformation>
      <gmi:MI_AcquisitionInformation />
   </gmi:acquisitionInformation>
</gmi:MI_Metadata>")

(def iso-with-use-constraints
  "<?xml version=\"1.0\" encoding=\"UTF-8\"?>
  <gmi:MI_Metadata xmlns:gmi=\"http://www.isotc211.org/2005/gmi\" xmlns:eos=\"http://earthdata.nasa.gov/schema/eos\" xmlns:gco=\"http://www.isotc211.org/2005/gco\" xmlns:gmd=\"http://www.isotc211.org/2005/gmd\" xmlns:gml=\"http://www.opengis.net/gml/3.2\" xmlns:gmx=\"http://www.isotc211.org/2005/gmx\" xmlns:gsr=\"http://www.isotc211.org/2005/gsr\" xmlns:gss=\"http://www.isotc211.org/2005/gss\" xmlns:gts=\"http://www.isotc211.org/2005/gts\" xmlns:srv=\"http://www.isotc211.org/2005/srv\" xmlns:swe=\"http://schemas.opengis.net/sweCommon/2.0/\" xmlns:xlink=\"http://www.w3.org/1999/xlink\" xmlns:xs=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">
     <!--Other Properties, all:0, coi:0,ii:0,si:0,pli:0,pri:0,qi:0,gi:0,ci:0,dk:0,pcc:0,icc:0,scc:0-->
     <gmd:fileIdentifier>
        <gco:CharacterString>gov.nasa.echo:A minimal valid collection V 1</gco:CharacterString>
     </gmd:fileIdentifier>
     <gmd:language>
        <gco:CharacterString>eng</gco:CharacterString>
     </gmd:language>
     <gmd:characterSet>
        <gmd:MD_CharacterSetCode codeList=\"http://www.ngdc.noaa.gov/metadata/published/xsd/schema/resources/Codelist/gmxCodelists.xml#MD_CharacterSetCode\" codeListValue=\"utf8\">utf8</gmd:MD_CharacterSetCode>
     </gmd:characterSet>
     <gmd:hierarchyLevel>
        <gmd:MD_ScopeCode codeList=\"http://www.ngdc.noaa.gov/metadata/published/xsd/schema/resources/Codelist/gmxCodelists.xml#MD_ScopeCode\" codeListValue=\"series\">series</gmd:MD_ScopeCode>
     </gmd:hierarchyLevel>
     <gmd:contact gco:nilReason=\"missing\" />
     <gmd:dateStamp>
        <gco:DateTime>2016-07-06T18:15:49.058-04:00</gco:DateTime>
     </gmd:dateStamp>
     <gmd:metadataStandardName>
        <gco:CharacterString>ISO 19115-2 Geographic Information - Metadata Part 2 Extensions for imagery and gridded data</gco:CharacterString>
     </gmd:metadataStandardName>
     <gmd:metadataStandardVersion>
        <gco:CharacterString>ISO 19115-2:2009(E)</gco:CharacterString>
     </gmd:metadataStandardVersion>
     <gmd:identificationInfo>
        <gmd:MD_DataIdentification>
           <gmd:citation>
              <gmd:CI_Citation>
                 <gmd:title>
                    <gco:CharacterString>MINIMAL &gt; A minimal valid collection</gco:CharacterString>
                 </gmd:title>
                 <gmd:date>
                    <gmd:CI_Date>
                       <gmd:date>
                          <gco:DateTime>1999-12-31T19:00:00-05:00</gco:DateTime>
                       </gmd:date>
                       <gmd:dateType>
                          <gmd:CI_DateTypeCode codeList=\"http://www.ngdc.noaa.gov/metadata/published/xsd/schema/resources/Codelist/gmxCodelists.xml#CI_DateTypeCode\" codeListValue=\"revision\">revision</gmd:CI_DateTypeCode>
                       </gmd:dateType>
                    </gmd:CI_Date>
                 </gmd:date>
                 <gmd:date>
                    <gmd:CI_Date>
                       <gmd:date>
                          <gco:DateTime>1999-12-31T19:00:00-05:00</gco:DateTime>
                       </gmd:date>
                       <gmd:dateType>
                          <gmd:CI_DateTypeCode codeList=\"http://www.ngdc.noaa.gov/metadata/published/xsd/schema/resources/Codelist/gmxCodelists.xml#CI_DateTypeCode\" codeListValue=\"creation\">creation</gmd:CI_DateTypeCode>
                       </gmd:dateType>
                    </gmd:CI_Date>
                 </gmd:date>
                 <gmd:edition>
                    <gco:CharacterString>1</gco:CharacterString>
                 </gmd:edition>
                 <gmd:identifier>
                    <gmd:MD_Identifier>
                       <gmd:code>
                          <gco:CharacterString>MINIMAL</gco:CharacterString>
                       </gmd:code>
                       <gmd:description>
                          <gco:CharacterString>A minimal valid collection</gco:CharacterString>
                       </gmd:description>
                    </gmd:MD_Identifier>
                 </gmd:identifier>
                 <gmd:otherCitationDetails>
                    <gco:CharacterString />
                 </gmd:otherCitationDetails>
              </gmd:CI_Citation>
           </gmd:citation>
           <gmd:abstract>
              <gco:CharacterString>A minimal valid collection</gco:CharacterString>
           </gmd:abstract>
           <gmd:purpose gco:nilReason=\"missing\" />
           <gmd:resourceConstraints>
              <gmd:MD_LegalConstraints>
                 <gmd:useLimitation>
                    <gco:CharacterString>Restriction Comment: Dummy Comment</gco:CharacterString>
                 </gmd:useLimitation>
                 <gmd:otherConstraints>
                    <gco:CharacterString>Restriction Flag:0</gco:CharacterString>
                 </gmd:otherConstraints>
              </gmd:MD_LegalConstraints>
           </gmd:resourceConstraints>
           <gmd:language>
              <gco:CharacterString>eng</gco:CharacterString>
           </gmd:language>
           <gmd:characterSet>
              <gmd:MD_CharacterSetCode codeList=\"http://www.ngdc.noaa.gov/metadata/published/xsd/schema/resources/Codelist/gmxCodelists.xml#MD_CharacterSetCode\" codeListValue=\"utf8\">utf8</gmd:MD_CharacterSetCode>
           </gmd:characterSet>
           <gmd:extent>
              <gmd:EX_Extent id=\"boundingExtent\">
                 <gmd:description gco:nilReason=\"unknown\" />
              </gmd:EX_Extent>
           </gmd:extent>
           <gmd:supplementalInformation />
           <gmd:processingLevel>
              <gmd:MD_Identifier>
                 <gmd:code>
                    <gco:CharacterString />
                 </gmd:code>
                 <gmd:description>
                    <gco:CharacterString />
                 </gmd:description>
              </gmd:MD_Identifier>
           </gmd:processingLevel>
        </gmd:MD_DataIdentification>
     </gmd:identificationInfo>
     <gmd:distributionInfo>
        <gmd:MD_Distribution>
           <gmd:distributor>
              <gmd:MD_Distributor>
                 <gmd:distributorContact gco:nilReason=\"missing\" />
                 <gmd:distributionOrderProcess>
                    <gmd:MD_StandardOrderProcess>
                       <gmd:fees gco:nilReason=\"missing\" />
                    </gmd:MD_StandardOrderProcess>
                 </gmd:distributionOrderProcess>
                 <gmd:distributorTransferOptions>
                    <gmd:MD_DigitalTransferOptions />
                 </gmd:distributorTransferOptions>
              </gmd:MD_Distributor>
           </gmd:distributor>
        </gmd:MD_Distribution>
     </gmd:distributionInfo>
     <gmd:dataQualityInfo>
        <gmd:DQ_DataQuality>
           <gmd:scope>
              <gmd:DQ_Scope>
                 <gmd:level>
                    <gmd:MD_ScopeCode codeList=\"http://www.ngdc.noaa.gov/metadata/published/xsd/schema/resources/Codelist/gmxCodelists.xml#MD_ScopeCode\" codeListValue=\"series\">series</gmd:MD_ScopeCode>
                 </gmd:level>
              </gmd:DQ_Scope>
           </gmd:scope>
           <gmd:lineage>
              <gmd:LI_Lineage>
                 <gmd:processStep>
                    <gmi:LE_ProcessStep>
                       <gmd:description gco:nilReason=\"unknown\" />
                    </gmi:LE_ProcessStep>
                 </gmd:processStep>
              </gmd:LI_Lineage>
           </gmd:lineage>
        </gmd:DQ_DataQuality>
     </gmd:dataQualityInfo>
     <gmd:metadataMaintenance>
        <gmd:MD_MaintenanceInformation>
           <gmd:maintenanceAndUpdateFrequency>
              <gmd:MD_MaintenanceFrequencyCode codeList=\"http://www.ngdc.noaa.gov/metadata/published/xsd/schema/resources/Codelist/gmxCodelists.xml#MD_MaintenanceFrequencyCode\" codeListValue=\"irregular\">irregular</gmd:MD_MaintenanceFrequencyCode>
           </gmd:maintenanceAndUpdateFrequency>
           <gmd:maintenanceNote>
              <gco:CharacterString>Translated from ECHO using ECHOToISO.xsl Version: 1.32 (Dec. 9, 2015)</gco:CharacterString>
           </gmd:maintenanceNote>
        </gmd:MD_MaintenanceInformation>
     </gmd:metadataMaintenance>
     <gmi:acquisitionInformation>
        <gmi:MI_AcquisitionInformation />
     </gmi:acquisitionInformation>
  </gmi:MI_Metadata>")

(def constraints-path [:identificationInfo :MD_DataIdentification :resourceConstraints])

(deftest iso-constraints
  (testing "Use constraints"
   (are3 [umm-map expected-iso]
     (let [expected-iso-parsed (x/parse-str expected-iso)
           expected-iso-constraints (xml/element-at-path expected-iso-parsed constraints-path)
           generated-iso (iso/umm-c-to-iso19115-2-xml (coll/map->UMM-C umm-map))
           generated-iso-parsed (x/parse-str generated-iso)
           generated-iso-constraints (xml/element-at-path generated-iso-parsed constraints-path)]
       (is (= expected-iso-constraints generated-iso-constraints)))

    "No use constraints"
    {} iso-no-use-constraints

    "With use constraints"
    {:AccessConstraints {:Description "Dummy Comment" :Value 0}} iso-with-use-constraints)))