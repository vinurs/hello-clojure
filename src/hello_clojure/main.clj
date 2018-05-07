(ns hello-clojure.main
  (:require [hello-clojure.basic :as basic]
            [taoensso.timbre :as log]
            [taoensso.timbre.appenders.core :as appenders]
            )

  (:gen-class)
  )



;; 主函数入口
(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (log/info "hello")
  (log/merge-config!
   {:appenders {:spit                     ;自带的appender，可以输出到文件
                (appenders/spit-appender {:fname "/Users/vinurs/my-file.log"})}})


  (log/info "hello111")
  (println "Hello, World!" args)
  ;; (basic/follow-the-rabbit)
  )
