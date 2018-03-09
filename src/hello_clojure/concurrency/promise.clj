(ns hello-clojure.concurrency.promise)


(def p (promise))

(realized? p)

(deliver p 42)

(realized? p)
