(ns hello-clojure.macros.eval-expression)

;; 将read-string产生的数据结构，送给eval求值
(eval (read-string "(+ 1 2 3 4 5)"))
;;=> 15


(class (eval (read-string "(+ 1 2 3 4 5)")))
;;=> java.lang.Long

(+ 1 2 3 4 5)
;;=> 15
