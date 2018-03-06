(ns hello-clojure.datatypes.numbers)


;; 数字

;; 整数
93

;; 小数
1.2

;; 分数
1/5

;; 整数
1
42
-14

;; 小数
12.43

;; 分数
1/3

;; 分数表示法只能针对整数，对小数就会出错
;; 4.0/2
;; 有公约数的分数会自行处理
4/2

;; 有小数的除法，结果会是小数
(/ 1 3.0)
(/ 3.0 7)

;; 整数相除，除不尽的话会用分数来表示，除得尽就是整数
(/ 7 3)
(/ 2 4)
(/ 6 3)
(/ 6 4)


(+ 1 2 3)
;; ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; 数字
;; ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; 整数/小数/分数
93
1.2
1/3

(def clueless 9)
(class clueless)
;;=> java.lang.Long
(class (+ clueless 9000000000000000))
;;=> java.lang.Long
(class (+ clueless 90000000000000000000))
;;=> clojure.lang.BigInt

(class (+ clueless 9.0))
;;=> java.lang.Double

;; 溢出，上溢
;; (+ Long/MAX_VALUE Long/MAX_VALUE)
;;=> java.lang.ArithmeticException: integer overflow

(unchecked-add (Long/MAX_VALUE) (Long/MAX_VALUE))
;;=> -2

;; 下溢
(float 0.0000000000000000000000000000000000000000000001)
;;=> 0.0
1.0E-430
;;=> 0.0

(def a (rationalize 1.0e50))
(def b (rationalize -1.0e50))
(def c (rationalize 17.0e00))

(+ (+ a b) c)
;;=> 17N

(+ a (+ b c))
;;=> 17N
