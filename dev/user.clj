(ns user
  (:require [nextjournal.clerk :as clerk]
            [portal.api :as p]))

;; Serve all clj and md files in src dir as clerk notebooks
(clerk/serve! {:browse? true :watch-paths ["src"]})

(def p "The Portal data visualizer API object"
  (p/open {:launcher :vs-code}))
(add-tap #'p/submit)

(comment
  ;; Clear all values in portal inspector window
  (p/clear)

  (def tokyonight "WIP Portal Color Theme"
    {::text       "#a9b1d6"
     ::background  "#1a1b26"
     ::background2 "#24283b"
     ::boolean     "#ff9e64"
     ::string      "#9ece6a"
     ::keyword     "#f7768e"
     ::namespace   "#bb9af7"
     ::tag         "#2ac3de"
     ::symbol      "#7aa2f7"
     ::number      "#ff9e64"
     ::uri         "#73daca"
     ::border      "#565f89"
     ::package     "#c0caf5"
     ::exception   "#f7768e"
     ::diff-add    "#485e30"
     ::diff-remove "#8c4351"})
  (p/open [:theme :material-ui])
  (prn @p) ; bring selected value back into repl
  (remove-tap #'p/submit) ; Remove portal from tap> targetset
  (p/close) ; Close the inspector when done
  (p/docs) ; View docs locally via Portal - jvm / node only
  :rcf)
