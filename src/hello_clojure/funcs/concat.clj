(ns hello-clojure.funcs.concat)

;; 连接两个多个collection
(concat [1 2] [3 4])
;; => (1 2 3 4)

(concat '(1 2) [3 4] [5 6] {:a 1 :c 2})
