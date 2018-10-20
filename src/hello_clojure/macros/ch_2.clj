(ns hello-clojure.macros.ch-2)


;; 宏就是在编译的时候运行的，跟函数的功能类似但是又不完全相同
;; 第一章是写宏的基本方法，不过方法用起来很笨重


;; 根据第一章教的宏的写法，我们定义assert就这么写
(defmacro vinurs-assert [x]
  (when *assert* ;; check the dynamic var `clojure.core/*assert*` to make sure
    ;;   assertions are enabled
    (list 'when-not x (list 'throw
                            (list 'new 'AssertionError
                                  (list 'str "Assert failed: "
                                        (list 'pr-str (list 'quote x))))))))

;; (vinurs-assert (= 1 2))
;; (vinurs-assert (= 1 1))

(macroexpand '(vinurs-assert (= 1 2)))

;; 但是上面的写法太繁琐了，我们能不能写宏的时候生成的代码就跟最终生成的代码一样呢？
;; 是可以的，那就是利用`来进行，然后用~来对里面的符号进行解引用

(def a 4)
'(1 2 3 a 5)
(list 1 2 3 a 5)
`(1 2 3 a 5)
`(1 2 3 ~a 5)
(def b (symbol "b"))
`(1 2 3 '~b 5)

;; 在`里面，所有的符号都会被加上namespace
`(+ a b c)
;; 想要去掉namespace，那么就用这种方法
`(* ~'x ~'x)

(defn test-1
  [x]
  `(+ x 1))
;; (eval (test-1 2))
;; 这里会报错，可以看出对于`里面的符号肯定是会绑定namespace的，就算是在函数里面使用也是一样的

(defn test-2
  [x]
  `(+ ~x 1))
(macroexpand '(test-2 2))
;; 加上了解引用以后，x就会被当前作用域里面真正的参数锁替代了
(test-2 2)
(eval (test-2 '(+ 1 2)))
(eval (test-2 (+ 1 2)))
;; 加上了解引用以后，就会变成2了


(defmacro test-3
  [x]
  `(+ ~x 3))
(macroexpand '(test-3 3))
(test-3 3)


(def other-numbers '(4 5 6 7 8))
`(1 2 3 ~other-numbers 9 10)
(concat '(1 2 3) other-numbers '(9 10))
;;=> (1 2 3 4 5 6 7 8 9 10)


;; TODO: ~@用来做什么的
(def other-numbers '(4 5 6 7 8))
`(1 2 3 ~@other-numbers 9 10)


`(* ~'x ~'x)

(defn ab [x]
  `(+ 1 ~x))
;; (eval (ab 1))

;; ~用来解析某个符号
`(1 2 3 '~a 5)

(defn ab [x]
  `(+ 1 x))
(ab 1)


(def a [10])
`(+ ~@a)

(defmacro aa [a]
  `(+ ~a))

(defn test1
  [a]
  (aa a))
(test1 10)


(defmacro squares [xs]
  (list 'map '#(* % %) xs))
(macroexpand '(squares [2]))

(squares  (range 10))

;; 但是有时候我们要定义宏啊，在宏里面会定义参数，而`默认会被符号加上namespace
;; 因此，后面我们就要想办法去掉符号里面的namespace限制了
(defmacro squares [xs]
  `(map (fn [x] (* x x)) ~xs))

(macroexpand '(squares [10]))
;; (squares (range 10))

;; 那么我们如果直接使用'呢?这个会去掉吗？下面的代码执行的结果可以看出namespace没有去掉
(defmacro squares-1 [xs]
  `(map (fn ['x] (* 'x 'x)) ~xs))
(macroexpand '(squares-1 [10]))


`(* ~'x ~'x)

;; 通过~'x就能把namespace去掉了
(defmacro squares [xs]
  `(map (fn [~'x] (* ~'x ~'x)) ~xs))
(macroexpand '(squares [10]))

(squares (range 10))

;; 如果在函数里面使用这些符号呢
(defn hello-1 [xs]
  `(map (fn [x] (* x x)) ~xs))
(macroexpand '(hello-1 [10]))
(hello-1 [10])
;; 可以看出，无论在宏里面还是函数里面，`的行为都是一致的

(defn hello-2 [xs]
  `(map (fn [~'x] (* ~'x ~'x)) ~xs))
(macroexpand '(hello-2 [10]))
(hello-2 [10])

(defn hello-3 [xs x]
  `(map (fn [~x] (* ~x ~x)) ~xs))
(macroexpand '(hello-1 [10]))
(hello-3 10 1)


(defn hello-4 [xs]
  `(+ ~@xs))
(eval (hello-4 [1 2 3 4]))


(defn hello-5 [xs]
  (+ ~@xs))
;; (hello-5 [1 2 3 4])
;; 这里会报错，~@一定要跟`配合使用




(defn hello-plus
  [xs]
  `(+ ~@xs))
(hello-plus [1 2 3])
(eval (hello-plus [1 2 3]))


;; 其实写宏的时候要注意的问题就是参数的替换跟符号的namespace的问题，别的都跟写函数没什么差别


(defmacro make-adder [x]
  `(fn [~'y] (+ ~x ~'y)))
((make-adder 2) 3)
