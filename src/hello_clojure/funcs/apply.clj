(ns hello-clojure.funcs.apply)

;; 传一个函数，后面的所有参数以及collection都作为参数传给这个函数进行调用
(apply hash-map [:a 5 :b 6])

(def args [2 -2 10])
(apply * 0.5 3 args)
