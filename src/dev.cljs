(ns dev
  (:require [flow-storm.api :as fsa]
            [lambda]))


(defn ^:dev/after-load start []
  (fsa/connect)
  (println "node-repl started"))
