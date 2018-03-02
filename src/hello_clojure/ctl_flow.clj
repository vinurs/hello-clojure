(ns hello-clojure.ctl-flow
  ;; 导入clojure.set库
  (:require [clojure.set])
  )


;; clojure控制流

(if true
  "By Zeus's hammer!"
  "By Aquaman's trident!")


(if false
  "By Zeus's hammer!"
  "By Aquaman's trident!")

;; 如果没有设置else部分，那么if直接返回nil
(if false
  "By Odin's Elbow!")


;; do执行多行语句，返回最后一个语句的返回值
(if true
  (do (println "Success!")
      "By Zeus's hammer!")
  (do (println "Failure!")
      "By Aquaman's trident!"))

;; when，当判断条件为真的时候，就执行下面的语句，相当于if and do的结合，可以用来执行多条语句
;; 当判断条件为假的时候就返回nil
(when true
  (println "Success!")
  "abra cadabra")

;; 对于clojure而言，有true和false，nil用来表示什么值都没有，也就是说nil跟false不是一样的
;; 但是nil跟false都用来表示逻辑假，其它的值的字面值都表示逻辑真
;; 但是nil跟false字面上的值并不相等
(if "bears eat beets"
  "bears beets Battlestar Galactica")

(if nil
  "This won't be the result because nil is falsey"
  "nil is falsey")

(nil? 1)
(nil? nil)
(nil? false)
(false? false)
(false? nil)

(= 1 1)
;; => true

(= nil nil)
;; => true

;; =>:large_I_mean_venti
(or false nil :large_I_mean_venti :why_cant_I_just_say_large)

;; => false
(or (= 0 1) (= "yes" "no"))

;; => 3
(or (= 0 1) (= "yes" "no") 3)

;; => nil
(or nil)

;; => false
(or nil false)

;; 总结一下or的行为:
;; 判断后面的每一个测试语句，直到遇到true的语句，返回他的值
;; 如果一直遇不到为true的测试语句，那么返回最后一个语句的返回值


;; => :hot_coffee
(and :free_wifi :hot_coffee)

;; => nil
(and :feelin_super_cool nil false)

;; => false
(and :feelin_super_cool false nil)

(false? nil)

;; 总结一下and的行为:
;; 判断后面的每一条语句，直到遇到一个值为false的，然后返回这条语句的值，其实返回值要么是nil要么是false
;; 如果一直遇到不为false的语句，那么返回最后一条语句的值


;; 循环
;; This program displays Hello World
(defn Example []
  (dotimes [n 5]
    (println "hi")))
(Example)

;; This program displays Hello World
(defn Example []
  (def x (atom 1))
  (while ( < @x 5 )
    (do
      (println @x)
      (swap! x inc))))
(Example)
x


;; 递归
