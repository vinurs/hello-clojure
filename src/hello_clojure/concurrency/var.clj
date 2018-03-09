(ns hello-clojure.concurrency.vars)


;; 将一个var定义一个常量，这样这个值在用到的地方在编译的时候就被替换掉了，而不会被重新计算
(def ^:const everything 42)
everything

(def max-value 255)
(defn valid-value?
  [v]
  (<= v max-value))

(valid-value? 19)
;; => true
(valid-value? 500)
;; => false

(def max-value 500)
(valid-value? 500)
;; => true 这里就变成了true了

;; 如果我们把max-value定义成常量
(def ^:const max-value 255)
(defn valid-value?
  [v]
  (<= v max-value))

(valid-value? 19)
;; => true
(valid-value? 500)
;; => false

(def max-value 500)
(valid-value? 500)
;; => false 这里依然会是false

;; :const表示这个值在编译期间就会被替换掉，编译到代码里面去了
;; 应该类似于C语言里面的预处理器的替换


;; 动态作用域
(def ^:dynamic *max-value* 255)
(defn valid-value?
  [v]
  (<= v *max-value*))

;; 只有在binding里面，这个才被改掉了
(binding [*max-value* 500]
  (valid-value? 299))

;; 在binding外面还是没有被改掉
(valid-value? 299)

;; 动态作用域其实就是给函数提示了一个隐式的参数
