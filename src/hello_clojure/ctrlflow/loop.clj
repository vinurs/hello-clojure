(ns hello-clojure.ctrlflow.loop)


;; ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; 循环
;; loop&recur/dotimes/doseq/map/reduce/for
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


(defn sum-down-from
  [initial-x]
  (loop [sum 0, x initial-x] ;; Set up recursion target
    (if (pos? x)
      (recur (+ sum x) (dec x)) ;; Jump to recursion target
      sum)))
(sum-down-from 10)


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
