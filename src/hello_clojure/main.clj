(ns hello-clojure.main
  (:require [hello-clojure.basic :as basic]
            [taoensso.timbre :as log])

  (:gen-class)
  )



;; 主函数入口
(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (log/info "hello")
  (println "Hello, World!" args)
  ;; (basic/follow-the-rabbit)
  )
