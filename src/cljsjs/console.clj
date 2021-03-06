(ns cljsjs.console
  "Wrappers for the JavaScript console API."
  (:refer-clojure :exclude [assert time])
  (:require
   [cljs.env :as env]))

(defn node-js?
  "Returns true if the ClojureScript compiler :target option is
  :node-js."
  []
  (= :nodejs (:target @env/*compiler*)))

(defmacro js-apply [f target args]
  `(.apply ~f ~target (to-array ~args)))

(defmacro when-debug [& body]
  `(when ^boolean goog.DEBUG
     ~@body))

;; ---------------------------------------------------------------------
;; Logging

(defmacro log
  "Display messages to the console."
  [& args]
  `(when-debug (.log js/console ~@args)))

(defmacro log*
  "Like `log` but takes a collection and uses apply."
  [coll]
  `(when-debug (js-apply (.-log js/console) js/console ~coll)))

(defmacro debug
  "Like `log` but marks the output as debugging information."
  [& args]
  `(when-debug (.debug js/console ~@args)))

(defmacro debug*
  "Like `debug` but takes a collection and uses apply."
  [coll]
  `(when-debug (js-apply (.-debug js/console) js/console ~coll)))

(defmacro info
  "Like `log` but marks the output as an informative message."
  [& args]
  `(when-debug (.info js/console ~@args)))

(defmacro info*
  "Like `info` but takes a collection and uses apply."
  [coll]
  `(when-debug (js-apply (.-info js/console) js/console ~coll)))

(defmacro warn
  "Like `log` but marks the output as a warning."
  [& args]
  `(when-debug (.warn js/console ~@args)))

(defmacro warn*
  "Like `warn` but takes a collection and uses apply."
  [coll]
  `(when-debug (js-apply (.-warn js/console) js/console ~coll)))

(defmacro error
  "Like `log` but marks the output as an error."
  [& args]
  `(when-debug (.error js/console ~@args)))

(defmacro error*
  "Like `error` but takes a collection and uses apply."
  [coll]
  `(when-debug (js-apply (.-error js/console) js/console ~coll)))


;; ---------------------------------------------------------------------
;; Message grouping (browser only)

(defmacro group-start
  "Begin a console message group."
  [& args]
  (when-not (node-js?)
    `(when-debug (.group js/console ~@args))))

(defmacro group-start-collapsed
  "Begin a console message group in a collapsed state."
  [& args]
  (when-not (node-js?)
    `(when-debug (.groupCollapsed js/console ~@args))))

(defmacro group-end
  "End a console message group."
  []
  (when-not (node-js?)
    `(when-debug (.groupEnd js/console))))

(defmacro with-group
  "Evaluate body within a new console group with title. Returns the
  value of body."
  [title & body]
  (when-not (node-js?)
    `(when-debug
      (.group js/console ~title)
      (let [result# (do ~@body)]
        (.groupEnd js/console)
        result#))))

(defmacro with-group-collapsed
  "Evaluate body within a new console group (collapsed) with
  title. Returns the value of body."
  [title & body]
  (when-not (node-js?)
    `(when-debug
      (.groupCollapsed js/console ~title)
      (let [result# (do ~@body)]
        (.groupEnd js/console)
        result#))))


;; ---------------------------------------------------------------------
;; Assertion

(defmacro assert [& args]
  `(when-debug (.assert js/console ~@args)))

;; ---------------------------------------------------------------------
;; Inspection

(defmacro dir [obj]
  `(when-debug (.dir js/console ~obj)))

(defmacro dirxml [node]
  (when-not (node-js?)
    `(when-debug (.dirxml js/console ~node))))

(defmacro trace []
  `(when-debug (.trace js/console)))

;; ---------------------------------------------------------------------
;; Profiling (browser only)

(defmacro profile-start
  "Begin a profile named title. Browser only."
  [title]
  (when-not (node-js?)
    `(when-debug (.profile js/console ~title))))

(defmacro profile-end
  "End a profile. Browser only."
  []
  (when-not (node-js?)
    `(when-debug (.profileEnd js/console))))

(defmacro with-profile
  "Evaluate body within a new console profile with title. Returns the
  value of body. Browser only."
  [title & body]
  (when-not (node-js?)
    `(when-debug
      (.profile js/console ~title)
      (let [result# (do ~@body)]
        (.profileEnd js/console)
        result#))))


;; ---------------------------------------------------------------------
;; Timing

(defmacro time-start [id]
  `(when-debug (.time js/console ~id)))

(defmacro time-end [id]
  `(when-debug (.timeEnd js/console ~id)))

(defmacro time
  "Evaluate body and time its execution. Returns the value of body."
  [& body]
  `(when-debug
    (.time js/console "Elapsed time")
    (let [result# (do ~@body)]
      (.timeEnd js/console "Elapsed time")
      result#)))

(defmacro with-time
  "Evaluate body and time its execution with title. Returns the value
  of body."
  [title & body]
  `(when-debug
    (let [title# ~title]
      (.time js/console title#)
      (let [result# (do ~@body)]
        (.timeEnd js/console title#)
        result#))))

;; ---------------------------------------------------------------------
;; Extra

(defmacro spy
  "Log and return a value."
  [expr & args]
  `(let [ret# ~expr]
     (when-debug 
      (.log js/console ~@args ret#)
      (tap> ret#))
     ret#))