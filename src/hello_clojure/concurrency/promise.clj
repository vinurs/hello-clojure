(ns hello-clojure.concurrency.promise)


;; promise只能设置一次，后面对这个的设置都是无效的
(def x (promise))

;; 通过deliver设置
(deliver x 101)
@x

(realized? x)

(deliver x 42)

(realized? x)
