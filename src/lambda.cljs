(ns lambda
  (:require [cljsjs.console :refer [spy log]]
            [oops.core :refer [oget oset!]]
            [promesa.core :as p]))

(def ^js aws (js/require "aws-sdk"))
(def ^js s3 (new (.-S3 aws)))
(def ^js iam (new (.-IAM aws)))

(defn handler [event context callback]
   (log event context)
    (callback
     nil
     #js{:statusCode 200
         :body        "Hello from CLJS Lambda!"
         :header      #js{}}))

(p/plet [buckets (spy (.. s3 (listBuckets #js{}) promise))
         roles (spy (.. iam (listRoles #js{}) promise))]
  (println (oget buckets "Buckets"))
  (println (oget roles "Roles.?10.RoleName")))

(comment

  (handler #js{:event "foo"}
           #js{:context "bar"}
           (fn [_err result]
             (spy result)))

  'comment)