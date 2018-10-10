(ns hello-clojure.datatypes.spec
  (:require [clojure.spec.alpha :as s]
            [clojure.repl :as r :refer [doc]])
  (:import (java.util Date)))

;; spec就是通过某一个函数来判断某一个数据类型是否符合定义
;; 任何接受一个参数然后返回true/false的函数都可以作为判断函数
(println "hello")

(s/conform even? 1000)

;; 如果条件不满足的话，那么:clojure.spec.alpha/invalid就会被返回
(s/conform even? 1001)

;; valid直接通过true/false来表明是否满足条件
(s/valid? even? 10)
(s/valid? even? 11)


(s/valid? nil? nil)
;; true
(s/valid? string? "abc")
;; true

(s/valid? string? 12)
;; false

(s/valid? #(> % 5) 10)
;; true
(s/valid? #(> % 5) 0)
;; false


(s/valid? inst? (Date.))
;; true



;; Sets can also be used as predicates that match one or more literal values:
(s/valid? #{:club :diamond :heart :spade} :club)
;; true
(s/valid? #{:club :diamond :heart :spade} 42)
;; false

(s/valid? #{42} 42) ;; true



;; (even? "a")
;; (s/conform even? "a")

;; 定义我们自己的spec，用::来防止keyword冲突
(s/def ::date inst?)
(s/def ::suit #{:club :diamond :heart :spade})
;; 定义好了就开始使用
(s/valid? ::date (Date.))
;;=> true
(s/conform ::suit :club)
;;=> :club

;; (doc ::date)
;; (doc ::suit)

;; 组合各种predicates函数
(s/def ::big-even (s/and int? even? #(> % 1000)))
(s/valid? ::big-even :foo)
;; false
(s/valid? ::big-even 10)
;; false
(s/valid? ::big-even 100000)
;; true
