(ns hello-clojure.main
  (:gen-class)
  )
;; 文件名用下划线，命名空间用横线

;; 最基本的运行方式，读取-计算-打印-循环
;; 基本类型
;; 整数
1
42
-14

;; 小数
12.43

;; 分数
1/3
;; 分数表示法只能针对整数，对小数就会出错
;; 4.0/2
;; 有公约数的分数会自行处理
4/2

;; 有小数的除法，结果会是小数
(/ 1 3.0)
(/ 3.0 7)

;; 整数相除，除不尽的话会用分数来表示，除得尽就是整数
(/ 7 3)
(/ 2 4)
(/ 6 3)
(/ 6 4)


;; 字符串
"jam"

;; 字符
\j
\[

;; 关键字
:jam


;; 布尔值
true
false

;; 表示缺失值
nil

;; 函数调用
(+ 1 1)
(+ 1 (+ 8 3))


;; 组织数据，容器
;; 列表
'(1 2 "jam" :marmalade-jar)
;; clojure里面可以使用逗号来隔开元素，但是会被忽略，一般的写法都是用空格来隔开就好
'(1, 2, "jam", :bee)

;; 对列表操作
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


;; 向量vector
[:jar1  1 2 3 :jar2]
;; first跟rest同样适用vector
(first [:jar1  1 2 3 :jar2])
(rest [:jar1  1 2 3 :jar2])

(nth [:jar1  1 2 3 :jar2] 0)
(nth [:jar1  1 2 3 :jar2] 2)
;; nth对list同样适用，只不过就是速度的问题
(nth '(2 3 4 5 6) 2)

;; 返回vector中最后一个元素
(last [:jar1  1 2 3 :jar2])
;; last对list同样适用
(last '(:rabbit :pocket-watch :marmalade))

;; 尽管有些函数对list跟vector都适用，但是在存储的时候list是链表形式，而vector应该是数组形式
;; 所以vector直接索引到某个元素更快一点

;; 计算容器的元素个数
(count [1 2 3 4])
(count '(1 2 3 4 5 6))
(count '((1 2) 3 (4 5)))
;; -> 3

;; 给集合增加元素
;; 对于list，是加入到list的头部
(conj '(:toast :butter) :jam)
(conj '(:toast :butter) '(1 2))
;; 对于vector，是加入到vector的结尾
(conj [:toast :butter] :jam)
(conj [:toast :butter] [1 2])


;; map, key-value形式的容器，是不是有点类似redis了
{:jam1 "strawberry" :jam2 "blackberry"}
;; map的key-value必须一一配对，否则会抛出异常
;; {:jam1 "strawberry" :jam2 }
;; map里面是不许有相同的key的
;; {:jam1 "hello" :jam1 "world"}

;; 获取map中某一个key的值
(get {:jam1 "strawberry" :jam2 "blackberry"} :jam2 )
;; 没有这key就返回nil
(get {:jam1 "strawberry" :jam2 "blackberry"} :jam3)
;; 没有这个key的时候自定义一个返回值
(get {:jam1 "strawberry" :jam2 "blackberry"} :jam3 "not found vinurs")

;; 直接使用key的值来调用函数，不过这样代码的可读性感觉差了，还是显式调用get比较好
(:jam2 {:jam1 "strawberry" :jam2 "blackberry"} "not found vinurs")
(:jam3 {:jam1 "strawberry" :jam2 "blackberry"} "not found vinurs")

;; 用keys、vals返回map中的所有key或者value
(keys {:jam1 "strawberry" :jam2 "blackberry" :jam3 "marmalade"})
(keys {:jam1 "strawberry" :jam2 "blackberry" "hello" "marmalade"})
(vals {:jam1 "strawberry" :jam2 "blackberry" :jam3 "marmalade"})

;; 更新map里面的值
(assoc {:jam1 "red" :jam2 "black"} :jam1 "orange")
;; 移除一个映射
(dissoc {:jam1 "red" :jam2 "black"} :jam1)

;; 合并两个map，如果有相同的key，那么后一个会覆盖前一个
(merge {:jam1 "red" :jam2 "black"}
       {:jam1 "orange" :jam3 "red"}
       {:jam4 "blue" :jam1 "blue"})


;; 集合，存储唯一的数据值，数据值不能重复
#{:red :blue :white :pink}
;; 集合在创建的时候是不允许有重复的
;; #{:red :blue :white :pink :pink}


;; 对集合可以做下面的操作：并集、差集、交集
;; ;; 并集
;; (clojure.set/union #{:r :b :w} #{:w :p :y})
;; ;; 从第一个集合里面减去跟第二个合集里面相同的元素
;; (clojure.set/difference #{:r :b :w} #{:w :p :y})
;; ;; 交集
;; (clojure.set/intersection #{:r :b :w} #{:w :p :y})

;; 通过set将其它类型的容器转成集合
(set [:rabbit :rabbit :watch :door])
(set '(1 1 2))
(set {1 2 3 4})

;; 通过get查看一个元素是否在一个集合里面
(get #{1 2 3 4} 5)
(get #{1 2 3 4} 1)
;; 如果元素是关键字，那么可以直接调用关键字，不过还是觉得get靠谱点
(:rabbit #{ 1 2 3 :rabbit})
;; 集合本身也可以作为函数来查看一个元素是否在集合里面
(#{1 2 3 4} 2)
(#{1 2 3 4} 0)
;; 通过contains?函数来查看一个元素是否在集合里面
(contains? #{:rabbit :door :watch} :rabbit)
(contains? #{:rabbit :door :watch} :jam)

;; 给集合增加元素
(conj #{:rabbit :door} :jam)
(conj #{:rabbit :door} :door)
;; 移除集合中的某个元素
(disj #{:rabbit :door :jam} :door)


;; 所有的容器都是不可变且持久化，意味着容器里面的内容是不会改变了，
;; 我们的对容器的所有的操作返回的都是这个结构的的一个新版本，通过下面的函数就可以看出来了
(def hello {:jam1 "red" :jam2 "black"})
hello
(dissoc hello :jam1)
hello


;; 符号
(def developer "Alice")


;; TODO: clojure里面nil跟'()有区别吗
(rest nil)
(first nil)
(first '(nil))
(rest '(nil))
(rest '(nil nil))



;; 主函数入口
(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))
