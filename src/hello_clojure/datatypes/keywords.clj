(ns hello-clojure.datatypes.keywords)

;; 关键字


:a-keyword
;;=> :a-keyword

::also-a-keyword
;;=> :hello-clojure.datatypes.keywords/also-a-keyword


;; 关键字的求值就是他们自己

;; 关键字用在map里面作为key
(def population {:zombies 2700, :humans 9})
(get population :zombies)
;;=> 2700

(println (/ (get population :zombies)
            (get population :humans))
         "zombies per capita")
;; 300 zombies per capita


;; 关键字作为函数使用
;; 去搜索某个collection是否存在这个关键字
(:zombies population)
;;=> 2700


(println (/ (:zombies population)
            (:humans population))
         "zombies per capita")
;; 300 zombies per capita


;; 作为列举类型
:small
:medium
:large



;; ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; 关键字作为命令
;; ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; 作为cond条件语句的else语句来使用，其实也就是true，估计不使用:else，使用:else1也是可以的
(defn pour [lb ub]
  (cond
    (= ub :toujours) (iterate inc lb)
    :else (range lb ub)))

(cond nil "haha"
      :else1 "good"
      :nil "111")


;; 关键字不属于任何namespace
::not-in-ns
;;=> :hello-clojure.datatypes.keywords/not-in-ns
;; (ns test1)

;; 新建namespace
;; defn-与defn功能一致，都是用于定义函数的，但是defn-定义的函数作用域是私有的，而defn定义的函数是公有的，如下：
(ns test1)
(defn- foo [] "world")
(defn bar [] (str "hello " (foo)))
(foo)
(bar)
(ns test2)
(test1/bar)
;; (test1/foo)
