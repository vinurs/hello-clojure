(ns hello-clojure.datatypes.sets
  (:require [clojure.repl :as r :refer [doc]]
            [clojure.set])
  )



;; ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; 集合，专门用来存放唯一值的，集合里面的值是不能重复的
;; ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; 集合也分两种：hash sorted
#{"kurt vonnegut" 20 :icicle}

;; 通过函数创建集合
(hash-set 1 1 2 2)

;; 向集合里面增加一个已经存在的值是加不进去的
(conj #{:a :b} :b)
;; => #{:a :b}

(hash-set [1 1 2 3] [2 2 45])


;; 从向量创建集合
(set [3 3 3 4 4])

;; 从字符串创建集合
(set "hello")

;; 从list创建集合
(set '(1 1 3 3))

;; 从map创建集合，每一对组成一个新的元素
(set {1 1 2 3})

;; 不过这个貌似就不成功
;; (set {1 2 1 3 4 5})

;; 可以从上面的例子看出，set其实所做的动作就是分解后面的数据结构，变成单个元素，然后放入集合


#{1 {1 2}}
#{ 1 [1]}
#{1 '(1)}
#{1 #{1}}

;; 查看某个集合中是否存在某个元素
(contains? #{:a :b} :a)
(contains? #{:a :b} :c)
(contains? #{:a :b} 3)
(contains? #{nil} nil)


;; 通过关键字对集合进行操作
(:a #{:a :b} "bb")
(:d #{:a :b} "bb")

(get #{:a :b} :a)
(get #{:a :b} "kurt vonnegut")



;; 如果不存在，返回一个默认的返回值
(get #{:a :b} "kurt vonnegut" "hhhh")

;; 通过get来判断一个集合里面是否有nil是不行的
;; 要通过contains?来判断
(get #{:a nil} nil)
;; => true
(contains? #{:a nil} nil)

;; 集合，存储唯一的数据值，数据值不能重复
#{:red :blue :white :pink}
;; 集合在创建的时候是不允许有重复的
;; #{:red :blue :white :pink :pink}


;; 对集合可以做下面的操作：并集、差集、交集
;; 并集
(clojure.set/union #{:r :b :w} #{:w :p :y})
;; 从第一个集合里面减去跟第二个合集里面相同的元素
(clojure.set/difference #{:r :b :w} #{:w :p :y})
;; 交集
(clojure.set/intersection #{:r :b :w} #{:w :p :y})

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
::hello
(dissoc hello :jam1)
hello
