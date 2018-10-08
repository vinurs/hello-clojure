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



;; var的root绑定
;; 每一个var都有一个root binding或者一个线程banding
(def x 1)
x
;; 重新绑定了root binding
(def x 2)
x

;; 默认的时候var是静态变量，这是不是意味着在别的线程里面不能通过binding宏来进行绑定
(def y 10)
;; 因为x、y没有设置为动态变量，那么就是不能用binding来进行绑定，下面的绑定就会报错
;; (binding [x 2 y 3]
;;   (+ x y))
;; 只有设置为动态变量的var才能用binding在每个线程里面进行绑定

(def ^{:dynamic true} foo 1)
foo
;; 新开一个线程，可以看到值也是1
(.start (Thread. #(println foo)))
(binding [foo 2] foo)
(defn print-foo [] (println foo))
;; 而且不能binding一个不存在root binding的变量
(binding [foo 2]
  (print-foo))
;; print-foo不是根据参数传递的，而是从context里面取的，所以foo这里还是1
;; 这就是let跟binding的差别
(let [foo 2] (print-foo))
;; let是一个静态绑定，绑定以后的值是不可以修改的,所以下面的会报错
;; (let [foo 2] (set! foo 3))

;; 那么多个binding存在的情况呢

;; (binding [foo 2]
;;   (binding [foo 3]
;;     (print-foo))
;;   (print-foo))
