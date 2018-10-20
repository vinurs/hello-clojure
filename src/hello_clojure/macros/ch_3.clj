(ns hello-clojure.macros.ch-3)


;; 函数定义square
(defn square [x] (* x x))
;; => #'hello-clojure.macros.ch-3/square

(map square (range 10))
;; => (0 1 4 9 16 25 36 49 64 81)

;; 宏定义square
(defmacro square [x] `(* ~x ~x))
;; (map square (range 10))
;; 这里会报错，宏不能作为map的参数

;; 那么就需要这样包装一下
;; (map (fn [n] (square n)) (range 10))


(defmacro our-and ([] true)
  ([x] x)
  ([x & next]
   `(if ~x (our-and ~@next) ~x)))

;; (our-and true true)
