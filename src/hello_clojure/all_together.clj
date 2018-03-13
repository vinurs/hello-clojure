(ns hello-clojure.all-together
  (:require
   [clojure.repl :as r :refer [doc]]
   [clojure.java.javadoc :refer [javadoc]])
  (:gen-class))


;; 定义了人体各个部位，仅仅包含左侧
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


;; 匹配某个部位，如果是以left-开头，那么创建对应的right-部位
(defn matching-part
  [part]
  ;; 返回一个map
  {:name (clojure.string/replace (:name part) #"^left-" "right-")
   :size (:size part)})

;; 构建人体的所有部位
(defn symmetrize-body-parts
  "Expects a seq of maps that have a :name and :size"
  [asym-body-parts]
  ;; 依次遍历整个vector
  (loop [remaining-asym-parts asym-body-parts
         final-body-parts []]
    (if (empty? remaining-asym-parts)
      final-body-parts
      (let [[part & remaining] remaining-asym-parts]
        ;; 进入最近的loop
        (recur remaining
               (into final-body-parts
                     ;; 合并新建立的到final-body-parts里面去
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

(let [[pongo & dalmatians] dalmatian-list]
  [pongo dalmatians])


;; 循环
(loop [iteration 0]
  (println (str "Iteration " iteration))
  (if (> iteration 3)
    (println "Goodbye!")
    (recur (inc iteration))))
;; loop初始化一个binding，然后通过recur再次进入这个loop部分


(defn recursive-printer
  ([]
   (recursive-printer 0))
  ([iteration]
   (println iteration)
   (if (> iteration 3)
     (println "Goodbye!")
     (recursive-printer (inc iteration)))))
(recursive-printer)


;; 正则表达式，用来进行模式匹配
;; 正则表达式其实就是字符串，在clojure里面以#""表示正则
#"a"

(defn matching-part
  [part]
  {:name (clojure.string/replace (:name part) #"^left-" "right-")
   :size (:size part)})

(matching-part {:name "left-eye" :size 1})
(matching-part {:name "head" :size 3})

;; sum with reduce
(reduce + [1 2 3 4])


(defn my-reduce
  ([f initial coll]
   (loop [result initial
          remaining coll]
     (if (empty? remaining)
       result
       (recur (f result (first remaining)) (rest remaining)))))
  ([f [head & tail]]
   (my-reduce f head tail)))

(defn better-symmetrize-body-parts
  "Expects a seq of maps that have a :name and :size"
  [asym-body-parts]
  (reduce (fn [final-body-parts part]
            (into final-body-parts
                  (set [part (matching-part part)])))
          []
          asym-body-parts))
