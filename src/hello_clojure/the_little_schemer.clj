(ns hello-clojure.the-little-schemer
  (:require
   [clojure.repl :as r :refer [doc]]
   [clojure.java.javadoc :refer [javadoc]])
  )


;; 学习《the little schemer》

;; lisp: (car '(1 2 3))
(first '(1 2 3))
(first '((a b c) x y z))
(first '())
(rest '())

(first '(((hotdogs)) (and) (pickle) relish))
(first (first '(((hotdogs)) (and) (pickle) relish)))

(rest '(a b c))
(rest '((a b c) x y z))
(rest '(hamburger))

(rest '((x) t r))

(rest '())

;; => nil
(rest nil)

(first (rest '((b) (x y) ((c)))))
(rest (rest '((b) (x y) ((c)))))

;; wrong
;; (rest (first '(a (b (c)) d)))

(cons 'peanut '(butter and jelly))

(cons '(banana and) '(peanut butter and jelly))
(cons '((help) this) '(is very ((hard) to learn)))
(cons '(a b (c)) '())
(cons 'a ())

(cons 'a '((b) c d))

(cons 'a (rest '((b) c d)))

(nil? ())
(nil? nil)

(empty? ())
(empty? nil)
(empty? '(a b c))

(list? '(a b c))

(defn my-atom?
  "判断一个输入是否为列表"
  [x]
  (and (not (list? x))
       (not (vector? x))
       (not (map? x))
       ))
(my-atom? 'a)
(my-atom? ['a 'b 'c])
(my-atom? '(Harry had a heap of apples))
(my-atom? (first '(Harry had a heap of apples)))
(my-atom? {'a 'b})

(my-atom? (rest '(Harry had a heap of apples)))

(my-atom? (rest '(Harry)))

(my-atom? nil)
(my-atom? ())
;; nil跟()只有在表示逻辑值的时候才表示假，在其它地方不是一样的东西

(my-atom? (first (rest '(swing low sweet cherry oat))))

(my-atom? (first (rest '(swing (low sweet) cherry oat))))

(def a1 'Harry)
(def a2 'Harry)
(= a1 a2)

(def a1 'margarine)
(def a2 'butter)
(= a1 a2)

(def l1 ())
(def l2 '(strawberry))
(= l1 l2)

;; 这里就表示nil跟()在clojure里面不是同一个东西
(= nil ())

(= 6 7)

(= 1 '(2))
;; 定义lat?函数
(defn lat?
  "查看一个list里面的元素是否都是原子的"
  [x]
  (cond (not (list? x)) false
        (empty? x) true
        (my-atom? (first x)) (lat? (rest x))
        true false
        ))

(lat? '(Jack Sprat could eat no chicken fat))
(lat? '((jack) 1 2))
(lat? ())
