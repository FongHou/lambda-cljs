(ns lambda
  (:require [cljsjs.console :refer [spy log]]
            [oops.core :refer [oget oset!]]
            [promesa.core :as p]
            ["@aws-sdk/client-s3"
             :refer [S3Client ListBucketsCommand  ListObjectsCommand
                     CreateBucketCommand PutObjectCommand]]))

(. (js/require "source-map-support") install)

(def s3 (S3Client. #js{:region "us-east-1"}))

(defn ^:export handler [event context]
  (js/console.log event)
  (js/console.log context)
  (->
   (p/let [bucket-name "lambda-cljs"
           new-bucket (.send s3 (CreateBucketCommand. #js{:Bucket bucket-name}))
           buckets (.send s3 (ListBucketsCommand. #js{}))
           new-object (.send s3 (PutObjectCommand. #js{:Bucket bucket-name
                                                       :Key    "test"
                                                       :Body   "Hello World!"}))
           objects (.send s3 (ListObjectsCommand. #js{:Bucket bucket-name}))]
     (spy new-bucket)
     (spy new-object)
     (println (oget objects "Contents.?0"))
     (println (oget buckets "Buckets.?0")))
   (p/catch #(js/console.log (.-stack %))))
  (p/promise #js{:message "Hello from CLJS World!"}))


(comment

  (handler #js{:event "Hello, World!"} #js{})

  'comment)