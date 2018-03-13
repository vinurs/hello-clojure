(ns hello-clojure.macros.read-string)

;; 通过read-string产生一个list代码
(read-string "(+ 1 2 3 4 5)")
;;=> (+ 1 2 3 4 5)

;; 通过read-string出来以后的list就可以直接送给eval去进行求值计算了

(class (read-string "(+ 1 2 3 4 5)"))
;;=> clojure.lang.PersistentList
