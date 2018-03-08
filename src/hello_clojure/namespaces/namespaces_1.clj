(ns hello-clojure.namespaces.namespaces-1)

;; 测试namespace

(def x 1)
x


(def x "hello")
x

*ns*

;; 创建一个新的namespace
(ns foo)
*ns*

;; 获取特定的命名空间里面的变量值
hello-clojure.namespaces.namespaces-1/x

;; 每个命名空间里面都会引用clojure.core中所有的var，所以这里可以用filter
filter
