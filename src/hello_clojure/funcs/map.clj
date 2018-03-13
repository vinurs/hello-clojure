(ns hello-clojure.funcs.map)
;; map。 map接受一个函数、一个或者多个集合作为参数，返回一个序列（ sequence）作为结果，
;; 这个返回的序列是把这个函数应用到所有集合对应元素所得结果的一个序列。
;; 任何的map调用：(map ƒ [a b c]) 和[(ƒ a) (ƒ b) (ƒ c)] 是 等价 的，

(map clojure.string/lower-case
     ["Java" "Imperative" "Weeping" "Clojure" "Learning" "Peace"])
;;= ("java" "imperative" "weeping" "clojure" "learning" "peace")

(map * [1 2 3 4] [5 6 7 8])


(map + '(1 2 3))

(map inc [1 2 3])

;; 如果集合参数不对等，那就取少的
(map + '(1 2 3) '(2 3))
;; => (3 5)

(map str ["a" "b" "c"] ["A" "B" "C"])
;; => ("aA" "bB" "cC")

(def human-consumption   [8.1 7.3 6.6 5.0])
(def critter-consumption [0.0 0.2 0.3 1.1])
(defn unify-diet-data
  [human critter]
  {:human human
   :critter critter})

(map unify-diet-data human-consumption critter-consumption)


(def sum #(reduce + %))
(def avg #(/ (sum %) (count %)))
(defn stats
  [numbers]
  (map #(% numbers) [sum count avg]))
(stats [3 4 10])
;; => (17 3 17/3)
(stats [80 1 44 13 6])
;; => (144 5 144/5)

(def identities
  [{:alias "Batman" :real "Bruce Wayne"}
   {:alias "Spider-Man" :real "Peter Parker"}
   {:alias "Santa" :real "Your mom"}
   {:alias "Easter Bunny" :real "Your dad"}])
(map :real identities)
;; => ("Bruce Wayne" "Peter Parker" "Your mom" "Your dad")
