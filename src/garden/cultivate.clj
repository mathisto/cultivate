(ns cultivate.cultivate
  "FIXME: my new org.corfield.new/scratch project.")

(defn exec
  "Invoke me with clojure -X cultivate.cultivate/exec"
  [opts]
  (println "exec with" opts))

(defn -main
  "Invoke me with clojure -M -m cultivate.cultivate"
  [& args]
  (println "-main with" args))
