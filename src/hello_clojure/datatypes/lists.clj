(ns hello-clojure.datatypes.lists)

;; ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; 列表，相当于C语言里面的链表，最神奇的数据结构，数据即代码
;; ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

'(1 2 3 4)

;; 获取列表中的元素要用nth，不能用get
(nth '(:a :b :c) 0)
(nth '(:a :b :c) 2)

;; 超过了就会出错
;; (nth '(:a :b :c) 4)

;; 通过函数创建列表
(list 1 "two" {3 4})

;; 向列表里面增加元素，对于列表增加元素是加在开头的
(conj '(1 2 3) 4)

;; 列表跟向量的要关注的一个点
;; 通过nth在列表里面获取某个位置的元素比用get在向量里面获取某个位置的元素要慢
;; 因为list要遍历整个list才能拿到，而向量可以直接定位到那个位置
;; 这其实也就是数组跟链表的区别


;; 列表，相当于C语言里面的链表吧
;; 对列表操作
;; 最简单直接的创建list的方式
'(1 2 "jam" :marmalade-jar)
;; clojure里面可以使用逗号来隔开元素，但是会被忽略，一般的写法都是用空格来隔开就好
'(1, 2, "jam", :bee)

;; 其它的创建列表的方式
(def stooges (list "Moe" "Larry" "Curly"))
stooges
(def stooges (quote ("Moe" "Larry" "Curly")))
stooges
(def stooges '("Moe" "Larry" "Curly"))
stooges

;; 往列表里面添加元素，构建一个新的列表
stooges
(def more-stooges (conj stooges "Shemp"))
more-stooges
(conj stooges "Shemp")
(cons "Shemp" stooges)

;; remove 函数创建一个只包含所指定的谓词函数测试结果为false的元素的集合:
;; -> ("Shemp" "Moe" "Larry")
(def less-stooges (remove #(= % "Curly") more-stooges))
less-stooges

;; into 函数把两个list里面的元素合并成一个新的大list
(def kids-of-mike '("Greg" "Peter" "Bobby"))
(def kids-of-carol '("Marcia" "Jan" "Cindy"))
(def brady-bunch (into kids-of-mike kids-of-carol))
(println brady-bunch) ; -> (Cindy Jan Marcia Greg Peter Bobby)

;; 获得列表第一个元素
(peek kids-of-mike)
;; 获得除第一个元素以外的剩下的列表
(pop kids-of-mike)
;; 其实这个也就相当于first rest

;; 获取列表第一个元素
(first '(:rabbit :pocket-watch :marmalade :door))
;; 获取列表中除了第一个元素剩下的列表
(rest '(:rabbit :pocket-watch :marmalade :door))

;; 嵌套first跟rest
(first (rest '(:rabbit :pocket-watch :marmalade :door)))
;; -> :pocket-watch

(first (rest (rest '(:rabbit :pocket-watch :marmalade :door))))
;; -> :marmalade

(first (rest (rest (rest '(:rabbit :pocket-watch :marmalade :door)))))
;; -> :door

;; 列表结束返回的就是nil
(first (rest (rest (rest (rest '(:rabbit :pocket-watch :marmalade :door))))))

;; 构建列表
(cons 5 '())
(cons 5 nil)
(cons nil nil)
;; nil就表示空列表?

(cons 4 (cons 5 nil))
(cons 3 (cons 4 (cons 5 nil)))
(cons 2(cons 3 (cons 4 (cons 5 nil))))
;; list直接构建列表
(list 2 3 4 5)
(list 2 nil)
