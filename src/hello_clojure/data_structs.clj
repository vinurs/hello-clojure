(ns hello-clojure.data-structs)


;; clojure基础数据结构

;; 数字
93
1.2
1/3

;; 字符串
"Lord Voldemort"
"\"He who must not be named\""
"\"Great cow of Moscow!\" - Hermes Conrad"

;; 字符串连接操作
(def my-name "Chewbacca")
(str "\"Uggllglglglglglglglll\" - " name)

;; 字典/映射，key-value
;; 在clojure里面存在两种字典，hash、sorted

;; 空字典
{}

{:first-name "Charlie"
 :last-name "McFishwich"}

{"string-key" +}

;; 嵌套字典
{:name {:first "John"
        :middle "Jacob"
        :last "Jingleheimerschmidt"}}

;; 字典里面无论是key或者是value都可以是任意类型的值，这太强大了
(get {{:first "John"
        :middle "Jacob"
       :last "Jingleheimerschmidt"} :name }
     {:first "John"
      :middle "Jacob"
      :last "Jingleheimerschmidt"})

;; 通过函数创建字典
(hash-map :a 1 :b 2)

;; 通过key在字典里面查找value
(get {:a 0 :b 1} :b)
(get {0 :a 1 :b} 0)
(get {:a 0 :b {:c "ho hum"}} :b)

;; 如果找不到对应的key，那么就返回nil，注意了nil就是表示什么也没有，不是false
(get {:a 0 :b 1} :c)

;; 如果找不到对应的key，那么我们可以自己设定一个默认返回值
(get {:a 0 :b 1} :c "unicorns?")

;; 另外一种查找字典里面的key的方法，就是可以直接将这个字典当做函数来看待，参数就跟get一样，可以一个可以两个
;; 不过这种方法知道就好，用起来破坏了语法的美感啊
({:name "The Human Coffeepot"} :name 1 2)

;; 嵌套查找，查找字典里面的字典的key对应的值
(get-in {:a 0 :b {:c "ho hum"}} [:b :c])
(get-in {:a 0 :b {:c {:d "this is d"}}} [:b :c :d])


;; 关键字，以冒号开头
;; 在clojure还有一种基本数据类型就是关键字，用得最多的就是在字典里面当做key来用
:a
:rumplestiltsken
:34
:_?

;; 关键字本身还可以用来作为函数去查找在另一个数据结构里面是否存在这个关键字
;; 被查找的数据类型不限
(:a {:a 1 :b 2 :c 3} "hello")
(:d {:a 1 :b 2 :c 3} "hello")
;; 由于list跟vector不是key-value的形式，所以查出来的值都是nil，这时候我们可以给一个默认的返回值
(:a '(1 2 :a) "hi")
(:a [1 2 :a] "b")
(:a "hello" "ddd")
(:a :a "nihao")


;; 向量，类似C语言里面的数组
[3 2 1]

;; 向量相关操作
;; 获取向量的第一个元素
(get [3 2 1] 0)
;; 获取向量的第二个元素
(get ["a" {:name "Pugsley Winterbottom"} "c"] 1)

;; 通过函数创建向量
(vector "creepy" "full" "moon")

;; 往向量里面增加一个元素，对于向量增加元素是加在结尾的
(def a [1 2 3])
(conj a 4)
;; 从这里可以看出，clojure里面的数据类型的值都是不可修改的，除非重新被赋值
a


;; 列表，相当于C语言里面的链表，最神奇的数据结构，数据即代码
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


;; 集合，专门用来存放唯一值的，集合里面的值是不能重复的
;; 集合也分两种：hash sorted
#{"kurt vonnegut" 20 :icicle }

;; 通过函数创建集合
(hash-set 1 1 2 2)
(conj #{:a :b} :b)

;; 从向量创建集合
(set [3 3 3 4 4])
;; 从list创建集合
(set '(1 1 3 3))
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
