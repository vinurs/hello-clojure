(ns hello-clojure.concurrency.concurrent
  (:require [clojure.repl :as r :refer [doc]]
            [clojure.java.javadoc :refer [javadoc]]))

;; 并发，多线程

*ns*
;; 创建原子，初始值为:caterpillar
(def who-atom (atom :caterpillar))
@who-atom
;; (doc atom)

;; 修改原子的值，这个跟用define有什么区别呢？
;; 将原子设置成新的值，并返回新值
(reset! who-atom :chrysalis)
@who-atom



(def who-atom (atom :caterpillar))
@who-atom

(defn change [state]
  (case state
    :caterpillar :chrysalis
    :chrysalis :butterfly
    :butterfly))

(swap! who-atom change)
@who-atom



;; 创建新的县城来执行代码
(def counter (atom 0))
@counter
(let [n 5]
  (future (dotimes [- n] (swap! counter inc)))
  (future (dotimes [- n] (swap! counter inc)))
  (future (dotimes [- n] (swap! counter inc)))
  )
@counter


;; 引入副作用
(def counter (atom 0))
(defn inc-print [val]
  (println val)
  (inc val))
(swap! counter inc-print)


;; 打印
(def counter (atom 0))
(let [n 2]
  (future (dotimes [_ n] (swap! counter inc-print)))
  (future (dotimes [_ n] (swap! counter inc-print)))
  (future (dotimes [_ n] (swap! counter inc-print)))
  )
