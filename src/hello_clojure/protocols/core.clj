(ns hello-clojure.protocols.core)


(defprotocol Matrix
  "Protocol for working with 2d datastructures."
  (lookup [matrix i j])
  (update [matrix i j value])
  (rows [matrix])
  (cols [matrix])
  (dims [matrix]))


;; 扩展protocol
(extend-protocol Matrix
  clojure.lang.IPersistentVector
  (lookup [vov i j] (get-in vov [i j]))
  (update [vov i j value] (assoc-in vov [i j] value))
  (rows [vov] (seq vov))
  (cols [vov] (apply map vector vov))
  (dims [vov] [(count vov) (count (first vov))]))
