(ns hello-clojure.funcs.apply)

;; (apply f args)(apply f x args)
;; (apply f x y args)(apply f x y z args)(apply f a b c d & args)
;; 传一个函数，后面的所有参数以及collection都作为参数传给这个函数进行调用
(apply hash-map [:a 5 :b 6])

(def args [2 -2 10])
(apply * 0.5 3 args)


;; 下面的调用都会出错，也就是最后一个只能是collection，并且只能有一个collection
;; (apply * [1 2] 3 4 5)
;; (apply * 3 4 5 [1 2] [6 5])
