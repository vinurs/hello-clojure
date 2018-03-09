(ns hello-clojure.concurrency.delay)


;; delay是一种让代码延迟执行的技术，代码只会在显式地调用deref的时候才会被执行

(def d
  (delay (println "Running...")
         :done))
(deref d)

;; 当然了，上面的例子我们也说我们可以用函数来进行执行，但是delay提供了一种技术
;; 里面的代码只执行一次，后面再执行的话就直接返回值

;; 这个如果所有的线程都来请求d的时候，会阻塞，直到有一个返回值了
