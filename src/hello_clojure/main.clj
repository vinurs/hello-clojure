(ns hello-clojure.main
  (:require [hello-clojure.basic :as basic])

  (:gen-class)
  )



;; 主函数入口
(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!" args)
  ;; (basic/follow-the-rabbit)
  )
