;; # 👨‍🌾 How does your garden grow? 
^{:nextjournal.clerk/visibility :hide-ns}
(ns garden.garden 
  (:require [nextjournal.clerk :as clerk]
            [portal.api :as p]))

;; [The Siren's Call (What is that annoying noise?)](siren-call.clj)
;; [Bread and Butter Functions](bread_butter.clj)

(comment
  (add-tap #'p/submit)
  (p/clear) ; Clear all values
  (prn @p) ; bring selected value back into repl
  (remove-tap #'p/submit) ; Remove portal from tap> targetset
  (p/close) ; Close the inspector when done
  (p/docs) ; View docs locally via Portal - jvm / node only 
  :rcf)
