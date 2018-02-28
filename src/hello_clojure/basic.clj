(ns hello-clojure.basic
  ;; 导入clojure.set库
  (:require [clojure.set])

  (:import (java.net InetAddress))
  (:gen-class))

;; 文件名用下划线，命名空间用横线
;; 文件名用下划线，命名空间用横线

;; 这个是单行注释
;; 下面的是多行注释
(comment "

...我是...

...多行...
...注释...

")
;; 不过像下面这样嵌入多行注释还是有问题的，所以还是用分号来得靠谱
;; (+ 1
;;     (comment
;;       "这个
;;  是多行注释")
;;    2)

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

;; 变量
;; Clojure里面是不支持变量的。它跟变量有点像，但是在被赋值之前是不允许改的，包括：全局binding, 线程本地(thread local)binding， 以及函数内的本地binding， 以及一个表达式内部的binding。
;; def定义全局bindings
(def hello-vinurs "hello ,vinurs")
hello-vinurs
;; ::用来引用当前命名空间的关键字
::hello-vinurs
;; def除了定义全局binding，还可以用来修改bindings

;; 函数的参数是只在这个函数内可见的本地binding

;; let 这个special form 创建局限于一个 当前form的bindings
;; let 是串行的赋值的, 所以后面的binding可以用前面binding的值,
(let [hello 1
      ;; 这里可以看出vinurs利用了hello的值
      vinurs (+ 1 hello)]
  hello
  vinurs)

;; 下面的例子详细介绍了 def , let 和 binding 的用法。
(def ^:dynamic v 1) ; v is a global binding
v
(defn f1 []
  (println "f1: v =" v)) ; global binding
(f1)
(defn f2 []
  (println "f2: before let v =" v) ; global binding
  (let [v 2] ; creates local binding v that shadows global one
    (def v 8)
    ;; 从这里也可以看出def貌似只能用来修改全局变量
    (println "f2: in let, v =" v) ; local binding
    (f1))
  (println "f2: after let v =" v)) ; global binding
(f2)

(defn f3 []
  (println "f3: before binding v =" v) ; global binding
  (binding [v 3] ; same global binding with new, temporary value
    ;; 这时候全局绑定临时的值变成了3
    (println "f3: in binding, v =" v) ; global binding
    (f1))
  (println "f3: after binding v =" v)) ; global binding
(f3)
(defn f4 []
  (def v 4)) ; changes the value of the global binding
(f4)
(println "after calling f4, v =" v)





;; 组织数据，容器   开始
;; 所有的clojure集合是不可修改的、异源的以及持久的。
;; 不可修改的意味着一旦一个集合产生之后，你不能从集合里面删除一个元素，也往集合里面添加一个元素
;; 异源的意味着一个集合里面可以装进任何东西（而不必须要这些东西的类型一样）
;; 持久的以为着当一个集合新的版本产生之后，旧的版本还是在的。

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


;; 向量vector，相当于C语言里面的数组，访问第几个元素直接就是O(1)，很高效
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

;; 组织数据，容器   结束
;; ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; 所有的容器都是不可变且持久化，意味着容器里面的内容是不会改变了，
;; 我们的对容器的所有的操作返回的都是这个结构的的一个新版本，通过下面的函数就可以看出来了
(def hello {:jam1 "red" :jam2 "black"})
hello
::hello
(dissoc hello :jam1)
hello

(def n 2)

;; 符号
;; 全局符号
(def developer "Alice")
hello-clojure.basic/developer
developer

;; 局部符号
(let [developer "Alice in Wonderland"]
  developer)
;; 这个还是全局的
developer
;; let的局部符号要配对
(let [hello 1
      good nil]
  hello
  good)
(let [developer "Alice in Wonderland"
      rabbit "white rabbit"]
  [developer rabbit])
;; 全局符号里面没有rabbit
;; rabbit


;; 创建我们自己的函数
;; 参数为空
(defn follow-the-rabbit []
  (println "Off we go!")
  )
follow-the-rabbit
;; (follow-the-rabbit)

;; 带两个参数
(defn shop-for-jams [jam1 jam2]
  {:name "jam-basket"
   :jam1 jam1
   :jam2 jam2}
  )
(shop-for-jams "hello" "world")

;; 定义匿名函数
(fn []
  (str "off we go" "!"))
;; 调用匿名函数，简单地加上括号就可以了
((fn []
   (str "off we go" "!")))
;; 快捷方式定义匿名函数
(#(str "off we go" "!"))
;; 带一个参数的匿名函数快捷定义方式
(#(str "of we go" "!"  " - " % ) "again")
;; 带多个参数的匿名函数快捷定义方式
(#(str "of we go" "!"  " - " %1 " " %2 ) "again" "hello")
;; 不过上面这种方式不好，不能很好得表示出参数的名字

;; 查看当前所处的命名空间
*ns*

(def fav-food "strawberry jam")
fav-food
hello-clojure.basic/fav-food

;; 切换命名空间
(ns rabbit.favfoods)
;; 这里调用就会出错，因为没有这个命名空间，自然也没有这个符号的定义了
;; fav-food
;; 直接引用全路径的命名空间调用就可以
hello-clojure.basic/fav-food
(ns hello-clojure.basic)
fav-food


;; 逻辑值
;; clojure里面的true跟false就是java里面的boolen类型
(class true)
(class false)

;; 如果一个函数返回boolen值，那么起名的时候最好就以问号结尾
(true? true)
(true? false)
(false? false)
(false? true)
(nil? nil)
(nil? 1)

(not true)
(not false)
(not 1)
(not nil)
(not "hi")

(= :drinkme :drinkme)
(= :drinkme 4)

(= '(:drinkme :bottle) [:drinkme :bottle])
(not= :drinkme 4)



;; TODO: clojure里面nil跟'()有区别吗
(rest nil)
(first nil)
(first '(nil))
(rest '(nil))
(rest '(nil nil))


;; 逻辑测试
(empty? [:table :door :key])

(empty? [])
(empty? {})
(empty? '())
(empty? nil)

(seq [1 2 3])
(class [1 2 3])
(class (seq [1 2 3]))
(seq [])

(every? odd? [1 3 5])
(every? odd? [1 2 3 4 5])

(defn drinkable? [x]
  (= x :drinkme))
(drinkable? 'hello)
(drinkable? :drinkme)

(every? drinkable? [:drinkme :drinkme])
(every? drinkable? [:drinkme :poison])
(every? (fn [x] (= x :drinkme)) [:drinkme :posion])
(every? (fn [x] (= x :drinkme)) [:drinkme :drinkme])


;; 流控制
(if true "it is true"
    ;; else
    "it is false")

(if false "it is true"
    ;; else
    "it is false")

(if nil "it is true"
    ;; else
    "it is false")

(if 1 "it is true"
    ;; else
    "it is false")
(if '() "it is true"
    ;; else
    "it is false")
;; 所以只有nil跟false是false

(if (= :drinkme :drinkme)
  "Try it"
  "Dont't try it")

(let [need-to-grow-small (> 5 3)]
  (if need-to-grow-small
    "drink bottle"
    "don't drink bottle"))

;; 将一个boolen绑定到一个符号，如果为真，那么执行第一条语句；否则执行第二条语句
(if-let [need-to-grow-small (> 5 3)]
  "drink bottle"
  "don't drink bottle")

;; when，当一个条件为真的时候才执行下面的语句
(defn drink [need-to-grow-small]
  (when need-to-grow-small
    "drink bottle"))
(drink true)
(drink nil)

(when-let [need-to-grow-small true]
  "drink bottle")
(when-let [need-to-grow-small false]
  "drink bottle")

;; cond测试更多的条件，一旦一个测试返回true，其它的测试子句都不会被尝试
(let [bottle "drinkme"]
  (cond
    (= bottle "poison") "don't touch"
    (= bottle "drinkme") "grow smaller"
    (= bottle "empty") "all gone"))


(let [x 2]
  (cond
    (> x 10) "bigger than 10"
    (> x 4) "bigger than 4"
    (> x 3) "bigger than 3"
    :else "< 3"))

;; case
(let [bottle "drinkme"]
  (case bottle
    "poison" "don't touch"
    "drinkme" "grow smaller"
    "empty" "all gone"
    "unknown"))
(let [bottle "hehe"]
  (case bottle
    "poison" "don't touch"
    "drinkme" "grow smaller"
    "empty" "all gone"
    "unknown"))


;; 创建函数
(defn grow [name direction]
  (if (= direction :small)
    (str name " is growing smaller")
    (str name " is growing bigger")))
(grow "Alice" :small)
(grow "Alice" :big)
(partial grow "Alice")


;; 跟java进行交互，用句点
(. "caterpillar" toUpperCase)

;; .是不是被重定义的
;; (defn . []
;;   "good")
;; (hello-clojure.basic/.)
;; (.)

;; 用new来创建java对象实例
(new String "Hi!")
;; 快捷方式
(String. "Hi!")

;; 调用java的方法
(InetAddress/getByName "localhost")

(class 5)
(class 28888888888888888888888888888888888888888)
