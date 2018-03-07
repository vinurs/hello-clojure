(ns hello-clojure.basic
  ;; 导入clojure.set库
  (:require [clojure.repl :as r :refer [doc]]
            [clojure.set])

  (:import (java.net InetAddress))
  (:gen-class))


;; 返回当前的namespace的字符串名字
(ns-name *ns*)
;; 类似java，文件名用下划线，命名空间用横线

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


;; 函数调用
(+ 1 1)
(+ 1 (+ 8 3))

;; 变量
;; Clojure里面是不支持变量的。它跟变量有点像，但是在被赋值之前是不允许改的，包括：全局binding, 线程本地(thread local)binding， 以及函数内的本地binding， 以及一个表达式内部的binding。
;; def除了定义全局binding，还可以用来修改bindings

;; 函数的参数是只在这个函数内可见的本地binding

;; let 这个special form 创建局限于一个 当前form的bindings
;; let 是串行的赋值的, 所以后面的binding可以用前面binding的值,
(let [hello 1
      ;; 这里可以看出vinurs利用了hello的值
      vinurs (+ 1 hello)]
  hello
  vinurs)


;; clojure里面def表示把一个名字绑定到一个值，可以看出名字是跟着值走，不是值跟着名字走
;; clojure数据类型都是不可变数据类型，所以一旦数据定义好了，那是不能改的
;; 如果要修改值，那就是重新创建值，然后再给这个新创建的值绑定一个名字

(def failed-protagonist-names
  ["Larry Potter" "Doreen the Explorer" "The Incredible Bulk"])
failed-protagonist-names

(def severity :mild)
(def error-message "OH GOD! IT'S A DISASTER! WE'RE ")
(defn error-message
  [severity]
  (str "OH GOD! IT'S A DISASTER! WE'RE "
       (if (= severity :mild)
         "MILDLY INCONVENIENCED!"
         "DOOOOOOOMED!")))
(error-message :mild)

*ns*

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


;; 斐波那契数列
(defn fib
  [n] (cond
        (= n 0) 0
        (= n 1) 1
        :else (+ (fib (- n 1))
                 (fib (- n 2)))))

(println "hi")
;; (time (fib 42))
"Elapsed time: 11184.49583 msecs" 267914296


(def memoized-fib
  (memoize
   (fn [n] (cond
             (= n 0) 0
             (= n 1) 1
             :else (+ (fib (- n 1))
                      (fib (- n 2)))))))
;; (time (memoized-fib 42))
;; (time (memoized-fib 42))

(defn factorial
  [n] (if (= n 1)
        1
        (* n (factorial (- n 1)))))

(factorial 6)
(* 6 (factorial 5))
(* 6 (* 5 (factorial 4)))


;; recur可以很好地来实现尾递归
;; 用尾递归来实现
(defn factorial2
  [n]
  (loop [count n
         acc 1]
    (if (zero? count) acc
        (recur (dec count) (* acc count)))))
(factorial2 6)


(declare my-odd? my-even?)

(defn my-odd?
  [n]
  (if (= n 0) false
      (my-even? (dec n))))
(defn my-even?
  [n] (if (= n 0)
        true
        (my-odd? (dec n))))

(declare my-odd? my-even?)
(defn my-odd?
  [n] (if (= n 0)
        false
        #(my-even? (dec n))))

;; (defn my-even?
;;   [n]
;;   (if (= n 0) true
;;       *(my-odd? (dec n))))
