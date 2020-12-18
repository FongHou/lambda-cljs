(ns lambda
  (:require [cljsjs.console :refer [spy log]]))

#trace
(defn handler [event context callback]
   (log event context)
    (callback
     nil
     #js{:statusCode 200
         :body        "Hello from CLJS Lambda!"
         :header      #js{}}))

(comment

  (handler #js{:event "foo"}
           #js{:context "bar"}
           (fn [_err result]
             (spy result)))

  'comment)