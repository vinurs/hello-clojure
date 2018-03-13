(ns hello-clojure.funcs.some)


;; (some pred coll)
;; 将pred作为函数作用于coll里面的每一个元素，如果遇到逻辑真，那就返回那个值，后面的就不再测试
;; 否则返回nil


;; 2 is even, so `some` stops there, 3 and 4 are never tested
(some even? '(1 2 3 4))
;;=> true

;; they are all odd, so not true, i.e. nil
(some even? '(1 3 5 7))
;;=> nil

;; 直接返回1
(some + '(1 2 3 4 5))


(some true? [false false false])
;;=> nil

(some true? [false true false])
;;=> true

(some true? [true true true])
;;=> true


(some #{"init"} '("init" 1 2 3))


;; the hash (as function) returns a nil for the key of '3';
;; nil在逻辑里面表示假，所以继续寻找key为2的值，就是"two"
(some {2 "two" 3 nil} [nil 3 2])
;;=> "two"
