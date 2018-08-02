(ns hello-clojure.concurrency.future)

;; future会新开一个线程来处理代码
(def long-calculation (future (apply + (range 1e8))))
;; 对于future的调用会立即返回，里面的代码会在另外一个线程里面执行
;; 可以通过解引用来获取这个值
;; long-calculation
;; @long-calculation

;; 如果在future还没有执行结束的时候去解引用这个东西，那么会导致我们阻塞
;; @(future (Thread/sleep 5000) :done)

;; future的返回值是什么？future的返回类型是一个future对象
;; future会保存代码里面的最后的返回值，这个返回值可以通过街引用进行访问

;; future可以在解引用的指定一个超时时间以及一个超时返回值，超时返回值会在解引用超时的时候返回
;; (deref (future (Thread/sleep 5000) :done)
;;        1000
;;        :impatient)


;; (do (future (println "haha"))
;;     (println "hehe"))
