(ns hello-clojure.funcs.memoize
  (:require [clojure.repl :as r :refer [doc]]
            [clojure.set])
  )


;; 因为函数式编程对于相同的输入输出都是一样的，所以我们可以有时候用来缓存结果
;; 这时候就用到了memoize

(doc memoize)
;; memoize返回一个函数的memorize版本

;; 先看一个没有用到memoize的例子
(defn sleepy-identity
  "Returns the given value after 1 second"
  [x]
  (Thread/sleep 1000)
  x)

(sleepy-identity "Mr. Fantastico")
;; => "Mr. Fantastico" after 1 second

(sleepy-identity "Mr. Fantastico")
;; => "Mr. Fantastico" after 1 second

;; 上面的函数每次调用都需要等一秒钟

;; 我们来用memoize对他优化
(def memo-sleepy-identity (memoize sleepy-identity))

(memo-sleepy-identity "Mr. Fantastico")
;; => "Mr. Fantastico" after 1 second
;; 这里再调用就会立即返回了
(memo-sleepy-identity "Mr. Fantastico")
;; => "Mr. Fantastico" immediately
