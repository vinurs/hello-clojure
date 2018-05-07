(ns hello-clojure.log.slf4j
  (:require [taoensso.timbre :as timbre
             :refer [log  trace  debug  info  warn  error  fatal  report
                     logf tracef debugf infof warnf errorf fatalf reportf
                     spy get-env]]
            [taoensso.timbre.appenders.core :as appenders]
            [clojure.java.io :as io])
  )

;; slf4j 是规范/接口

;; (log :info "hello slf4j")

;; timbre/default-output-fn
