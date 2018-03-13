(ns hello-clojure.concurrency.atoms
  (:require [clojure.repl :as r :refer [doc]]))

*ns*

;; 创建原子，初始值为:caterpillar
(def who-atom (atom :caterpillar))
@who-atom
;; (doc atom)

;; (def a who-atom)
;; a
;; @a

;; 修改原子的值，这个跟用define有什么区别呢？那是define定义的类型就不是atom类型了
;; 将原子设置成新的值，并返回新值
(reset! who-atom :chrysalis)
@who-atom

(reset! who-atom :chrysalis2)

;; 原子类型可以独立地被修改为任何状态


(defn change [state]
  (case state
    :caterpillar :chrysalis
    :chrysalis :butterfly
    :butterfly))

;; swap!形式在这个原子的旧值上应用一个函数，并把结果作为新值
(swap! who-atom change)
@who-atom


;; 创建新的线程来执行代码
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

;; 创建一个原子类型

(def who-atom (atom :caterpillar))
who-atom
@who-atom


;; 修改原子的值
(reset! who-atom :chrysalis)
@who-atom


;; atom, 原子类型
@(atom 12)

;; swap!总是返回原子类型的更新值
