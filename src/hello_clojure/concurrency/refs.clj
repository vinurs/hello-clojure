(ns hello-clojure.concurrency.refs)

;; ref是clojure提供的协调引用类型，可以保证多个线程交互地对这个ref进行操作
(def alice-height (ref 3))
(def right-hand-bites (ref 10))

@alice-height
@right-hand-bites

(defn eat-from-right-hand []
  (dosync (when (pos? @right-hand-bites)
    (alter right-hand-bites dec)
    (alter alice-height #(+ % 24)))))

(dosync (eat-from-right-hand))
@alice-height
@right-hand-bites


(let [n 2]
  (future (dotimes [_ n] (eat-from-right-hand)))
  (future (dotimes [_ n] (eat-from-right-hand)))
  (future (dotimes [_ n] (eat-from-right-hand))))
@alice-height
@right-hand-bites
