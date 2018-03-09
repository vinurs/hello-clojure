(ns hello-clojure.concurrency.atom)

;; atom, 原子类型
@(atom 12)

;; swap!总是返回原子类型的更新值
