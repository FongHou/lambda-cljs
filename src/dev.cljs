(ns dev
  (:require [flow-storm.api :as fsa]
            [lambda]))

(enable-console-print!)

(defn ^:dev/after-load start []
  (fsa/connect)
  (println "node-repl started"))
