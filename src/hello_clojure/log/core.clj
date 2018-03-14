(ns hello-clojure.log.core
  (:require [taoensso.timbre :as timbre
             :refer [log  trace  debug  info  warn  error  fatal  report
                     logf tracef debugf infof warnf errorf fatalf reportf
                     spy get-env]]
            [taoensso.timbre.appenders.core :as appenders]
            [clojure.java.io :as io])
  )



;; 输出到文件
;; (timbre/merge-config!
;;  {:appenders {:spit (appenders/spit-appender {:fname "/Users/vinurs/my-file.log"})}})

;; ;; 打印日志到文件的时候去掉颜色
;; (timbre/merge-config!
;;  {:output-fn (partial timbre/default-output-fn {:stacktrace-fonts {}})})

(info "This will print")
;; => nil

(spy :info (* 5 4 3 2 1))
(defn my-mult [x y] (info "Lexical env:" (get-env)) (* x y))
;; => #'my-mult
(my-mult 4 7)
;; => 28

(trace "This won't print due to insufficient log level")

(info (Exception. "Oh noes") "arg1" "arg2")

;; (def log-file-name "log.txt")
;; (timbre/set-config! {:appenders {:spit (appenders/spit-appender {:fname log-file-name})}})
