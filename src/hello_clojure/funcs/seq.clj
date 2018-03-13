(ns hello-clojure.funcs.seq)

(seq '(1 2 3))
;; => (1 2 3)

(seq [1 2 3])
;; => (1 2 3)

(seq #{1 2 3})
;; => (1 2 3)

(seq {:name "Bill Compton" :occupation "Dead mopey guy"})
;; => ([:name "Bill Compton"] [:occupation "Dead mopey guy"])


(into {} (seq {:a 1 :b 2 :c 3}))
;; => {:a 1, :c 3, :b 2}
