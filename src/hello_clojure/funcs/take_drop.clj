(ns hello-clojure.funcs.take-drop)


;; 获取一个序列的前三个组成一个list
(take 3 [1 2 3 4 5 6 7 8 9 10])
;; => (1 2 3)

;; 获取一个序列的除去前三个，剩下的组成一个list
(drop 3 [1 2 3 4 5 6 7 8 9 10])
;; => (4 5 6 7 8 9 10)

(def food-journal
  [{:month 1 :day 1 :human 5.3 :critter 2.3}
   {:month 1 :day 2 :human 5.1 :critter 2.0}
   {:month 2 :day 1 :human 4.9 :critter 2.1}
   {:month 2 :day 2 :human 5.0 :critter 2.5}
   {:month 3 :day 1 :human 4.2 :critter 3.3}
   {:month 3 :day 2 :human 4.0 :critter 3.8}
   {:month 4 :day 1 :human 3.7 :critter 3.9}
   {:month 4 :day 2 :human 3.7 :critter 3.6}])

;; 提供一个函数，只有返回逻辑真的才会被组成一个新的list，遇到第一个为假的就会停止后面的处理
(take-while #(< (:month %) 3) food-journal)

;; 满足条件的会被丢掉,一直遇到一个不满足条件的，然后后面的就被留下来了
(drop-while #(< (:month %) 3) food-journal)
;; => ({:month 3 :day 1 :human 4.2 :critter 3.3}
;; {:month 3 :day 2 :human 4.0 :critter 3.8}
;; {:month 4 :day 1 :human 3.7 :critter 3.9}
;; {:month 4 :day 2 :human 3.7 :critter 3.6})


(take-while #(< (:month %) 4)
            (drop-while #(< (:month %) 2) food-journal))


;; filter是满足条件的都会被留下来
(filter #(< (:month %) 3) food-journal)
