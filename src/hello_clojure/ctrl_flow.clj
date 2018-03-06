(ns hello-clojure.ctrl-flow
  ;; 导入clojure.set库
  (:require [clojure.repl :as r :refer [doc]]
            [clojure.set])
  )


;; clojure控制流

*ns*


;; ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; if
;; ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

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


;; clojure里面的逻辑真跟逻辑假包含哪些？
;; true表示真，false表示假
;; nil一般情况下表示什么值都没有，但是在逻辑谓词中表示逻辑假
;; 所以nil跟false表示逻辑假，别的都表示为逻辑真
;; ()表示空列表，但是在逻辑测试表达式里面表示逻辑真
;; 那么clojure有哪些逻辑测试表达式呢？
;; 有if/and/or/when/=/</>/not/
;; 也就是说，对于逻辑测试表达式，只可能返回四种值，false/nil/true/其它

;; ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; not
;; ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(not nil)
(not ())
(doc not)
(if (not ())
  true
  false)
(and () 1)


;; ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; when
;; ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(when nil
  "nothing")

;; 这里()就是表示真
(when ()
  "()")

;; 这里说明()在逻辑表达式里面表示真
(or nil ())
(or () nil)

(if (not nil)
  true
  false)
(false? ())
(false? nil)

(doc false?)

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

(true? "h")

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


;; ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; let
;; ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(let [x 3] x)
;; => 3

(def dalmatian-list
  ["Pongo" "Perdita" "Puppy 1" "Puppy 2"])

(let [dalmatians (take 2 dalmatian-list)]
  dalmatians)
;; => ("Pongo" "Perdita")


(def x 0)
;; let引入了一个新的作用域
(let [x 1] x)
;; => 1

(def x 0)
;; let里面引用已经定义的全局变量
(let [x (inc x)] x)
;; => 1

;; let的解构用法，参见解构部分
(let [[pongo & dalmatians] dalmatian-list]
  [pongo dalmatians])
;; => ["Pongo" ("Perdita" "Puppy 1" "Puppy 2")]


;; ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; 循环
;; ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; loop/recur
(loop [iteration 0]
  (println (str "Iteration " iteration))
  (if (> iteration 3)
    (println "Goodbye!")
    (recur (inc iteration))))

;; loop跟recur配合起来使用就类似于定义了一个函数，然后递归调用这个函数
;; recur总是会跳转到最近的loop或者fn或者defn
;; recur最好跟尾递归结合，要不然很容易就出现栈溢出的问题
;; 比如下面的定义，就类似一个循环
(defn recursive-printer
  ([]
   (recursive-printer 0))
  ([iteration]
   (println iteration)
   (if (> iteration 3)
     (println "Goodbye!")
     (recursive-printer (inc iteration)))))
(recursive-printer)

;; 循环的本质就是一个初始化判断条件，然后再次递归调用直到某个条件不满足


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

;; 尾递归
(defn print-down-from [x]
  (when (pos? x)
    (println x)
    (recur (dec x))))
(print-down-from 10)

(defn sum-down-from
  [sum x]
  (if (pos? x)
    (recur (+ sum x) (dec x))
    sum))
(sum-down-from 0 10)


(defn sum-down-from
  [initial-x]
  (loop [sum 0, x initial-x] ;; Set up recursion target
    (if (pos? x)
      (recur (+ sum x) (dec x)) ;; Jump to recursion target
      sum)))
(sum-down-from 10)
