(ns hello-clojure.funcs.reduce)

;; reduce对后面的函数，依次调用，并且前一次的返回值作为后一次调用的参数
;; 如果没有给函数初始参数，那么后面的函数会用collection里面的前两个作为参数
;; 如果有给了初始参数，那么就只会用collection的第一个元素作为参数

(reduce max [1 6 3 4 5])


(reduce + 1 '(1 23 4 5))

(reduce * '(1 2 3))
