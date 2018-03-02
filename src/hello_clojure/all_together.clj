(ns hello-clojure.all-together
  (:require
   [clojure.repl :as r :refer [doc]]
   [clojure.java.javadoc :refer [javadoc]])
  (:gen-class))


(def asym-hobbit-body-parts [{:name "head" :size 3}
                             {:name "left-eye" :size 1}
                             {:name "left-ear" :size 1}
                             {:name "mouth" :size 1}
                             {:name "nose" :size 1}
                             {:name "neck" :size 2}
                             {:name "left-shoulder" :size 3}
                             {:name "left-upper-arm" :size 3}
                             {:name "chest" :size 10}
                             {:name "back" :size 10}
                             {:name "left-forearm" :size 3}
                             {:name "abdomen" :size 6}
                             {:name "left-kidney" :size 1}
                             {:name "left-hand" :size 2}
                             {:name "left-knee" :size 2}
                             {:name "left-thigh" :size 4}
                             {:name "left-lower-leg" :size 3}
                             {:name "left-achilles" :size 1}
                             {:name "left-foot" :size 2}])


(defn matching-part
  [part]
  ;; 返回一个map
  {:name (clojure.string/replace (:name part) #"^left-" "right-")
   :size (:size part)})

(defn symmetrize-body-parts
  "Expects a seq of maps that have a :name and :size"
  [asym-body-parts]
  (loop [remaining-asym-parts asym-body-parts
         final-body-parts []]
    (if (empty? remaining-asym-parts)
      final-body-parts
      (let [[part & remaining] remaining-asym-parts]
        (recur remaining
               (into final-body-parts
                     (set [part (matching-part part)])))))))

(symmetrize-body-parts asym-hobbit-body-parts)

;; 上面是一个比较复杂的例子了，我们来慢慢详解这个例子

;; let用来定义局部binding
(let [x 3] x)
(def dalmatian-list
  ["Pongo" "Perdita" "Puppy 1" "Puppy 2"])
dalmatian-list
(let [dalmatians (take 2 dalmatian-list)]
  dalmatians)

(def x 0)
;; => 0
x

;; => 1
(let [x 1] x)

;; => 1
(let [x (inc x) y x]
  x)

(let [[pongo & dalmatians]
      dalmatian-list]
  [pongo dalmatians])
