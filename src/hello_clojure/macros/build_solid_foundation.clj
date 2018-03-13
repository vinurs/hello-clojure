(ns hello-clojure.macros.build-solid-foundation
  (:require [clojure.repl :as r :refer [doc]]
            [clojure.set])

  )


(let [expression (read-string "(+ 1 2 3 4 5)")]
  (cons (read-string "*")
        (rest expression)))
;;=> (* 1 2 3 4 5)

;; *1保存了上一次计算的结果
(eval *1)
;; *1 holds the result of the previous REPL evaluation ;=> 120


(let [expression (quote (+ 1 2 3 4 5))]
  (cons (quote *)
        (rest expression)))
;;=> (* 1 2 3 4 5)


(defn print-with-asterisks [printable-argument]
  (print "*****")
  (print printable-argument)
  (println "*****"))
(print-with-asterisks "hi")

(quote 1)

(quote "hello")
;;=> "hello"

(quote :kthx)
;;=> :kthx

(quote kthx)
;;=> kthx


(defmacro squares [xs] (list 'map '#(* % %) xs))

;; 宏真的是太强大了，条件也可以在里面进行判断
(defmacro test-hello
  [x]
  (if (= 3 x)
    (list + 1 8)
    (list / 4 2)))

(test-hello 3)
(test-hello /)

;; TODO: 是不是宏定义的东西先交给宏系统进行计算，然后再交给eval进行计算
;; The difference between functions and macros is that functions execute to return a value, whereas macros execute to return s-expressions that in turn are evaluated to return a value.
;; 函数跟宏的区别，函数计算返回一个值，而宏计算返回的是一个s-表达式，然后这个s-表达式会再次被计算
;; 也就是宏会被求值两次，第一次的结果会被作为一个s-表达式再次被求值

;; 宏的参数在传给宏的时候并不会被求值
(comment
  hihi
  这个就用来进行注释吧)

(defmacro comment [& body])

(doc comment)


;; 如果查看一个symbol到底是函数还是宏，就看里面的元数据，如果:macro是true，则表示是宏
((meta (var and)) :macro)
