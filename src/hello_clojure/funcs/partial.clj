(ns hello-clojure.funcs.partial)


;; (partial f)(partial f arg1)(partial f arg1 arg2)(partial f arg1 arg2 arg3)(partial f arg1 arg2 arg3 & more)
;; partial返回一个新的函数，这个函数还是进行f调用，只不过是前几个参数使用的是我们预先提供的参数
;; 调用这个新函数的时候，只要提供后面剩余的几个参数就可以了

(def only-strings
  ;; 这里string?就是我们预先给filter提供的参数
  (partial filter string? ))
;;=> #'user/ only- strings

(only-strings ["a" 5 "b" 6])
;;=> ("a" "b")

(partial * 5)

(def new-assoc
  (partial assoc {}))
