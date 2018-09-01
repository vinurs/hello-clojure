(ns hello-clojure.funcs.core)

(+ 1 2 3 4)
(* 1 2 3 4)
(first [1 2 3 4])

(or + -)

((or + -) 1 2 3)

((and (= 1 1) +) 1 2 3)

((first [+ 0]) 1 2 3)


;; error
;; (1 2 3 4)
;; ("test" 1 2 3)

(inc 1.1)

(map inc [0 1 2 3])
