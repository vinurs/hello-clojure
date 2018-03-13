(ns hello-clojure.functional-programming.core
  ;; 导入clojure.set库
  (:require [clojure.set])
  )

;; 什么是纯函数编程
;; 对于相同的参数，函数的返回值始终一样
;; 函数不会修改任何的外部资源，没有副作用

(defn wisdom
  [words]
  (str words ", Daniel-san"))
(wisdom "Always bathe on Fridays")


;; 这个函数就不是纯函数编程了
(defn year-end-evaluation
  []
  (if (> (rand) 0.5)
    "You get a raise!"
    "Better luck next year!"))
(year-end-evaluation)


(def great-baby-name "Rosanthony")

(let [great-baby-name "Bloodthunder"]
  great-baby-name)

great-baby-name


;; (defn my-sum
;;   ([vals] (sum vals 0))

;;   ([vals accumulating-total]
;;    (if (empty? vals)
;;      accumulating-total
;;      (my-sum (rest vals) (+ (first vals) accumulating-total)))))
;; (sum [39 5 1]) ; single-arity body calls two-arity body
;; (sum [39 5 1] 0)
;; (sum [5 1] 39)
;; (sum [1] 44)
;; (sum [] 45) ; base case is reached, so return accumulating-total
;;                                         ; => 45
;; ()
