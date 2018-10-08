(ns hello-clojure.macros.ch-1)


(read-string "(+ 1 2 3 4 5)")

(class (read-string "(+ 1 2 3 4 5)"))
;;=> clojure.lang.PersistentList

(eval (read-string "(+ 1 2 3 4 5)"))
;;=> 15

(eval '(+ 1 2))
(eval "(+ 1 2)")
;; eval执行的一定要是clojure的datastruct

(class (eval (read-string "(+ 1 2 3 4 5)")))
;;=> java.lang.Long

(+ 1 2 3 4 5)
;;=> 15

(let [expression (read-string "(+ 1 2 3 4 5)")]
  (cons (read-string "*")
        (rest expression)))
;;=> (* 1 2 3 4 5)
(eval *1)
;; *1 holds the result of the previous REPL evaluation
;;=> 120


;; (let [expression (+ 1 2 3 4 5)] ;; expression is bound to 15
;;   (cons
;;    (read-string "*") ;; *
;;    (rest expression))) ;; (rest 15)
;; IllegalArgumentException Don't know how to create ISeq from: java.lang.Long ; clojure.lang.RT.seqFrom (RT.java:505)


(let [expression (quote (+ 1 2 3 4 5))]
  (cons (quote *)
        (rest expression)))


(defn print-with-asterisks [printable-argument]
  (print "*****")
  (print printable-argument)
  (println "*****"))
(print-with-asterisks "hi")
;; *****hi*****
;;=> nil


(print-with-asterisks
 (do (println "in argument expression")
     "hi"))
;; in argument expression
;; *****hi*****
;;=> nil


(quote 1)

(quote "hello")
(quote :kthx)
(quote kthx)

;; 更简单的方法
'(+ 1 2 3 4 5)
'map

(let [expression '(+ 1 2 3 4 5)]
  (cons '* (rest expression)))
;;=> (* 1 2 3 4 5)


(when (= 2 (+ 1 1))
  (print "You got")
  (print " the touch!")
  (println))


;; 每次都手动展开宏太麻烦了，clojure提供了函数来展开
(macroexpand-1 '(when (= 1 2) (println "math is broken")))

(macroexpand-1 nil)

(macroexpand-1 '(+ 1 2))

(macroexpand-1 (when (= 1 2) (println "math is broken")))
;; surprise，因为macroexpan-1是函数，所以参数会先被求值


;;
(defmacro broken-when [test & body]
  (list test (cons 'do body)))
;; (broken-when (= 1 1) (println "Math works!"))
;; 报错了

;; 看看这个宏展开以后的结果
(macroexpand-1
 '(broken-when (= 1 1) (println "Math works!")))
;; => ((= 1 1) (do (println "Math works!")))
;; 明显错误的表达式，不能运行，所以会报错


;; 宏的参数要不要求值，是由宏自己决定的，并不会在执行body之前就先求值宏的参数
(defmacro when-falsy [test & body]
  (list 'when (list 'not test)
        (cons 'do body)))


(macroexpand-1 '(when-falsy (= 1 2) (println "hi!")))
;;=> (when (not (= 1 2)) (do (println "hi!")))


;; 上面的macroexpand-1只是展开了一层，如果里面还嵌套了宏，那么我们可以用下面的函数来展开
;; macroexpand展开得更加彻底
(macroexpand '(when-falsy (= 1 2) (println "hi!")))
;;=> (if (not (= 1 2)) (do (do (println "hi!"))))
