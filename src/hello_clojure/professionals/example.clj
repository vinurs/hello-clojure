(ns hello-clojure.professionals.example)

(defn square [a]
  (* a a))

(defn sum-of-squares [a b]
  (+ (square a) (square b)))

;; evaluate the expression (sum-of-squares 4 5)
(sum-of-squares 4 5)

;; (+ (square 4) (square 5)) (+ (* 4 4) (* 5 5))
;; (+ 16 25)
;; 41


;; 斐波那契数列
(defn fib [n]
  (cond
    (= n 0) 0
    (= n 1) 1
    :else (+ (fib (- n 1))
             (fib (- n 2)))))
;; 上面的不是尾递归，所以开销应该会很大

;; 计算5就很快
(fib 5)

;; 计算42就很慢了
;; (time (fib 42))
;; "Elapsed time: 96966.967774 msecs"
;; 267914296

;; 通过memoize函数来重新定义斐波那契数列函数
(def memoized-fib
  (memoize (fn
             [n]
             (cond
               (= n 0) 0
               (= n 1) 1
               :else (+ (fib (- n 1))
                        (fib (- n 2)))))))

;; 通过memoize函数重新定义以后再次执行这个函数
;; (time (memoized-fib 42))
;; "Elapsed time: 91793.464344 msecs"

;; 再次执行这个就很快了
;; (time (memoized-fib 42))
;; "Elapsed time: 0.048753 msecs"

;; 换个数求也很快
;; (time (memoized-fib 21))
;; "Elapsed time: 8.401496 msecs"

;; 43没求过，所以不会被缓存，比较慢
;; (time (memoized-fib 43))
;; "Elapsed time: 152303.74089 msecs"


;; ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; 通过递归思想来思考问题
;; ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; 我们来看一个求阶层的例子
(defn factorial [n]
  (if (= n 1)
    1
    (* n (factorial (- n 1)))))
;; 不过这个不是尾递归

(factorial 6)
;; (* 6 (factorial 5))
;; (* 6 (* 5 (factorial 4)))

;; 通过loop/recur来将这个函数转成尾递归来实现
(defn factorial2 [n]
  (loop [count n acc 1]
    (if (zero? count) acc
        (recur (dec count) (* acc count)))))
(factorial2 6)



;; 相互交叉引用的递归
;; 先声明了两个函数
(declare my-odd? my-even?)
;; 奇数判断
(defn my-odd? [n]
  (if (= n 0) false
      (my-even? (dec n))))
;; 偶数判断
(defn my-even? [n]
  (if (= n 0)
    true
    (my-odd? (dec n))))


;; (declare my-odd? my-even?)
;; (defn my-odd? [n]
;;   (if (= n 0)
;;     false
;;     #(my-even? (dec n))))

;; (defn my-even? [n]
;;   (if (= n 0)
;;     true
;;     *(my-odd? (dec n))))

;; ;; TODO: 通过这个可以防止栈溢出
;; (trampoline my-even? 42)
;; 不过这里有个问题，每次都要手动调用 trampoline函数，能不能自动呢？

;; 重新定义一下
(defn my-even? [n]
  (letfn [(e? [n]
            (if (= n 0)
              true
              #(o? (dec n))))
          (o? [n]
            (if (= n 0)
              false
              #(e? (dec n))))]
    (trampoline e? n)))

(defn my-odd? [n]
  (not (my-even? n)))



;; ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; 高阶函数
;; ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(def customers [{:state "CA" :name "Todd"}
                {:state "MI" :name "Jeremy"}
                {:state "CA" :name "Lisa"}
                {:state "NC" :name "Rich"}])

;; 通过filter过滤出state事CA的成员
(filter #(= "CA" (:state %)) customers)

;; filter作用于collection



;; Partials
