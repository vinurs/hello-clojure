(ns hello-clojure.concurrency.timer
  (:require [tools-timer.core :as timer]))

;; 每隔5秒钟执行一次
;; (timer/run-task! #(println "Say hello every 5 seconds.") :period 5000)

;; 延迟2秒执行
;; (timer/run-task! #(println "Say hello after 2 seconds.") :delay 5000)


;; 在一个固定的时间执行
;; (timer/run-task! #(println "Say hello at 2013-01-01T00:00:00 in beijing.") :at #inst "2018-08-05T05:16:20+08:00")

;; 一个timer执行多次任务
;; (def greeting-timer (timer/timer :name "The timer for greeting"))
;; (timer/run-task! #(println "Say hello after 2 seconds.")
;;                  :delay 2000 :by greeting-timer)
;; (timer/run-task! #(println "Say hello every 5 seconds.")
;;                  :period 5000 :by greeting-timer)

;; 取消一个timer
;; (timer/cancel! greeting-timer)
;; (timer/timer)

;; (defn on-exception [e]
;;   (println (.toString e)))

;; (timer/run-task! #(/ 1 0)
;;                  :period 5000 :by greeting-timer
;;                  :on-exception on-exception)

;; (on-exception "a")

;; (/ 1 0)
