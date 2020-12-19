(ns lambda-test
  (:require [cljs.spec.alpha :as s]
            [cljs.test :refer [deftest is]]
            [com.fulcrologic.guardrails.core :refer [>defn | ? =>]]))

(s/def ::thing (s/or :i int? :s string?))

(>defn f [i]
  [::thing => int?]
  (if (string? i) 0 (inc i)))

(deftest test-1
  (is (== (f 3) 4)))