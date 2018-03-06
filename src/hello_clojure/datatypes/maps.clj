(ns hello-clojure.datatypes.maps)


;; ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; 字典/映射，key-value
;; ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
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

;; 第一个表示key，后面的表示value
{1 2 2 1}

(get {1 2 2 1} 1)

;; 虽然map里面的key也可以是任意类型的，但是一般使用的时候都是直接用clojure里面的key数据类型
;; 不会另辟蹊径

;; 通过函数创建字典
(hash-map :a 1 :b 2)

;; 通过key在字典里面查找value
(get {:a 0 :b 1} :b)
(get {0 :a 1 :b} 0)
(get {:a 0 :b {:c "ho hum"}} :b)

(get {1 2 2 1} 1)

;; 如果找不到对应的key，那么就返回nil，注意了nil就是表示什么也没有，不是false
(get {:a 0 :b 1} :c)

;; 如果找不到对应的key，那么我们可以自己设定一个默认返回值
(get {:a 0 :b 1} :c "unicorns?")

;; 另外一种查找字典里面的key的方法，就是可以直接将这个字典当做函数来看待，参数就跟get一样，可以一个可以两个
;; 不过这种方法知道就好，用起来破坏了语法的美感啊
;; ({:name "The Human Coffeepot"} :name 1 2)

;; 嵌套查找，查找字典里面的字典的key对应的值
(get-in {:a 0 :b {:c "ho hum"}} [:b :c])
(get-in {:a 0 :b {:c {:d "this is d"}}} [:b :c :d])


;; map本身作为函数来查找某个key
({:name "The Human Coffeepot"} :name)
;; => "The Human Coffeepot"

({:name "The Human Coffeepot"} :name1 "hello")


;; 空map
{}


{:first-name "Charlie"
 :last-name "McFishwich"}

{"string-key" +}

{:name {:first "John"
        :middle "Jacob"
        :last "Jingleheimerschmidt"}}


;; 创建hash map
(hash-map :a 1 :b 2)
;; => {:a 1 :b 2}


;; 根据key查找map中的对应的key的值
(get {:a 0 :b 1} :b)
;; => 1

(get {:a 0 :b {:c "ho hum"}} :b)
;; => {:c "ho hum"}

;; 如果对应的key不存在，那么返回nil
(get {:a 0 :b 1} :c)
;; => nil

;; 对应的key不存在，可以给一个默认的返回值
(get {:a 0 :b 1} :c "unicorns?")
;; => "unicorns?"

;; 嵌套查找
(get-in {:a 0 :b {:c "ho hum"}} [:b :c])
;; => "ho hum"

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
