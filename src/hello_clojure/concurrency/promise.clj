(ns hello-clojure.concurrency.promise)


;; promise只能设置一次，后面对这个的设置都是无效的
(def x (promise))

;; 通过deliver设置
(deliver x 101)
;; 解引用的时候最好加上超时，要不然会被阻塞的
;; (deref x 100 :un)
;; @x

;; 通过realized?来检查这个变量有没有被deliver
(realized? x)

;; (deliver x 42)

;; (realized? x)
