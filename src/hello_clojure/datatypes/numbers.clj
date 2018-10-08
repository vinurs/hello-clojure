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
-100

;; big int
991778647261948849222819828311491035886734385827028118707676848307166514

;; big decimal
0.01M
(class 0.01M)
(class 0.01)
6.0221415e23
(class 6.0221415e23)

;; 在clojure里面数值默认的使用的是java的long类型
(class 5)
(class 28888888888888888888888888888888888888888)

[127 0x7F 0177 32r3V 2r01111111]

;; 浮点数

;; 小数
12.43
;; 小数默认的是使用的java的double类型
(class 12.1)

(class 12.11111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111)

;; => 12.11111111111111
;; 14位有效数字？
12.11111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111
;; 也就是下溢了

;; 通过在后面加上M可以来表示任意精度，这样就不会出现下溢的情况
12.11111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111M

;; TODO: 下面这个为什么会报错
;; (/ 1 12.11111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111M)

(/ 1 1.3)

;; 分数

;; 分数
1/3
(class 1/3)

;; 分数表示法只能针对整数，对小数就会出错
;; 4.0/2
;; 有公约数的分数会自行处理
4/2
(class 4/2)
(class (/ 1 3.0))

42N
(class 42N)

;; 任意精度
(class 0.01M)

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



;; 任意进制的表示方法
;; BrN: B表示基数，最多到36，由0-9以及a-z组成
11ra
