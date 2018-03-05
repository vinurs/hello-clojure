(ns hello-clojure.destructure)

;; ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; 解构
;; ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;


(def my-line [[5 10] [10 20]])

;; 手动解构，用函数来获取每一个collection里面的值
(let [p1 (first my-line)
      p2 (second my-line)
      x1 (first p1)
      y1 (second p1)
      x2 (first p2)
      y2 (second p2)]
  (println "Line from (" x1 "," y1 ") to (" x2 ", " y2 ")"))

;; 通过自动解构来完成获取里面的每一个元素
(let [[p1 p2] my-line
      [x1 y1] p1
      [x2 y2] p2]
  (println "Line from (" x1 "," y1 ") to (" x2 ", " y2 ")"))


;; 对list/vector解构用[]
;; 对map解构用{}


;; ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; 序列解构: vector/list/strings
;; ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;


(def my-vector [1 2 3])
(def my-list '(1 2 3))
(def my-string "abc")

;;= It should come as no surprise that this will print out 1 2 3
(let [[x y z] my-vector]
  (println x y z))
;;= 1 2 3

;;= We can also use a similar technique to destructure a list
(let [[x y z] my-list]
  (println x y z))
;;= 1 2 3

;;= For strings, the elements are destructured by character.
(let [[x y z] my-string]
  (println x y z)
  (map type [x y z]))
;;= a b c
;;= (java.lang.Character java.lang.Character java.lang.Character)

;; 解构的数目比实际参数多的时候，剩下的解构全部设置为nil
(def small-list '(1 2 3))
(let [[a b c d e f g] small-list]
  (println a b c d e f g))
;;= 1 2 3 nil nil nil nil

;; 解构的数目比实际参数少，那么自动忽略多余的参数
(def large-list '(1 2 3 4 5 6 7 8 9 10))
(let [[a b c] large-list]
  (println a b c))
;;= 1 2 3


;; 解构专门用于有bind作用的地方，例如let，函数参数


(def names ["Michael" "Amber" "Aaron" "Nick" "Earl" "Joe"])
(let [[item1 item2 item3 item4 item5 item6] names]
  (println item1)
  (println item2 item3 item4 item5 item6))
;;= Michael
;;= Amber Aaron Nick Earl Joe

;; 使用&来解构剩余参数
(let [[item1 & remaining] names]
  (println item1)
  (apply println remaining))
;;= Michael
;;= Amber Aaron Nick Earl Joe

;; 解构的时候忽略某些位置的参数
(let [[item1 _ item3 _ item5 _] names]
  (println "Odd names:" item1 item3 item5))
;;= Odd names: Michael Aaron Earl


;; 解构的同时还保留原来整体的参数
(let [[item1 :as all] names]
  (println "The first name from" all "is" item1))

(def numbers [1 2 3 4 5])
(let [[x & remaining :as all] numbers]
  (apply prn [remaining all]))
;;= (2 3 4 5) [1 2 3 4 5]

(def word "Clojure")
(let [[x & remaining :as all] word]
  (apply prn [x remaining all]))
;;= \C (\l \o \j \u \r \e) "Clojure"


(def fruits ["apple" "orange" "strawberry" "peach" "pear" "lemon"])
(let [[item1 _ item3 & remaining :as all-fruits] fruits]
  (println "The first and third fruits are" item1 "and" item3)
  (println "These were taken from" all-fruits)
  (println "The fruits after them are" remaining))
;;= The first and third fruits are apple and strawberry
;;= These were taken from [apple orange strawberry peach pear lemon]
;;= The fruits after them are (peach pear lemon)


;; 嵌套解构
(def my-line [[5 10] [10 20]])
(let [[[x1 y1][x2 y2]] my-line]
  (println "Line from (" x1 "," y1 ") to (" x2 ", " y2 ")"))
;;= "Line from ( 5 , 10 ) to ( 10 , 20 )"

(let [[[a b :as group1] [c d :as group2]] my-line]
  (println a b group1)
  (println c d group2))
;;= 5 10 [5 10]
;;= 10 20 [10 20]




;; ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; 关联解构: map
;; ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; 手动解构map
(def client {:name "Super Co."
             :location "Philadelphia"
             :description "The worldwide leader in plastic tableware."})

(let [name (:name client)
      location (:location client)
      description (:description client)]
  (println name location "-" description))
;;= Super Co. Philadelphia - The worldwide leader in plastic tableware.


;; 自动解构map
;; 更简洁好看了
(let [{name :name
       location :location
       description :description} client]
  (println name location "-" description))
;;= Super Co. Philadelphia - The worldwide leader in plastic tableware.


;; 解构不存在的key，自动设置为nil
(let [{category :category} client]
  (println category))
;;= nil

;; 解构map的时候，如果这个key不存在，那么可以设置为nil
(let [{category :category, :or {category "Category not found"}} client]
  (println category))
;;= Category not found


(let [{category :category, a :a ,
       :or {category "Category not found" a "a not found"}} client]
  (println category a))
;;= Category not found


;; 解构的同时保留原来的map
(let [{name :name :as all} client]
  (println "The name from" all "is" name))
;;= The name from {:name Super Co., :location Philadelphia, :description The world wide leader in plastic table-ware.} is Super Co.


(def my-map {:a "A" :b "B" :c 3 :d 4})
(let [{a :a, x :x, :or {x "Not found!"}, :as all} my-map]
  (println "I got" a "from" all)
  (println "Where is x?" x))
;;= I got A from {:a "A" :b "B" :c 3 :d 4}
;;= Where is x? Not found!


;; 更简单的解构key的方法
(let [{:keys [name location description]} client]
  (println name location "-" description))
;;= Super Co. Philadelphia - The worldwide leader in plastic tableware.



(def string-keys {"first-name" "Joe" "last-name" "Smith"})

(let [{:strs [first-name last-name]} string-keys]
  (println first-name last-name))
;;= Joe Smith

(def symbol-keys {'first-name "Jane" 'last-name "Doe"})

(let [{:syms [first-name last-name]} symbol-keys]
  (println first-name last-name))
;;= Jane Doe

;; keys对关键字不是key类型的不起作用？
(let [{:keys [first-name last-name]} symbol-keys]
  (println first-name last-name))


;; 嵌套解构
(def multiplayer-game-state
  {:joe {:class "Ranger"
         :weapon "Longbow"
         :score 100}
   :jane {:class "Knight"
          :weapon "Greatsword"
          :score 140}
   :ryan {:class "Wizard"
          :weapon "Mystic Staff"
          :score 150}})

(let [{{:keys [class weapon]} :joe} multiplayer-game-state]
  (println "Joe is a" class "wielding a" weapon))
;;= Joe is a Ranger wielding a Longbow


;; 解构函数参数
(defn configure [val options]
  (let [{:keys [debug verbose] :or {debug false, verbose false}} options]
    (println "val =" val " debug =" debug " verbose =" verbose)))

(configure 12 {:debug true})
;;val = 12  debug = true  verbose = false


(defn configure
  [val & {:keys [debug verbose]
          :or {debug false, verbose false}}]
  (println "val =" val " debug =" debug " verbose =" verbose))

(configure 10)
;;val = 10  debug = false  verbose = false

(configure 5 :debug true)
;;val = 5  debug = true  verbose = false

;; Note that any order is ok for the kwargs
(configure 12 :verbose true :debug true)
;;val = 12  debug = true  verbose = true


(def human {:person/name "Franklin"
            :person/age 25
            :hobby/hobbies "running"})
(let [{:keys [person/name person/age hobby/hobbies]} human]
  (println name "is" age "and likes" hobbies))
;;= Franklin is 25 and likes running


;; ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; 哪些地方可以使用解构呢
;; 显示地或者隐式地调用let进行bind的地方：let变量，函数参数
;; ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn print-coordinates-1 [point]
  (let [x (first point)
        y (second point)
        z (last point)]
    (println "x:" x ", y:" y ", z:" z)))


(defn print-coordinates-2 [point]
  (let [[x y z] point]
    (println "x:" x ", y:" y ", z:" z)))


(defn print-coordinates-3 [[x y z]]
  (println "x:" x ", y:" y ", z:" z))


(def john-smith {:f-name "John"
                 :l-name "Smith"
                 :phone "555-555-5555"
                 :company "Functional Industries"
                 :title "Sith Lord of Git"})


(defn print-contact-info [{:keys [f-name l-name phone company title]}]
  (println f-name l-name "is the" title "at" company)
  (println "You can reach him at" phone))

(print-contact-info john-smith)
;;= John Smith is the Sith Lord of Git at Functional Industries
;;= You can reach him at 555-555-5555



(def john-smith {:f-name "John"
                 :l-name "Smith"
                 :phone "555-555-5555"
                 :address {:street "452 Lisp Ln."
                           :city "Macroville"
                           :state "Kentucky"
                           :zip "81321"}
                 :hobbies ["running" "hiking" "basketball"]
                 :company "Functional Industries"
                 :title "Sith Lord of Git"})


(defn print-contact-info
  [{:keys [f-name l-name phone company title]
    {:keys [street city state zip]} :address
    [fav-hobby second-hobby] :hobbies}]
  (println f-name l-name "is the" title "at" company)
  (println "You can reach him at" phone)
  (println "He lives at" street city state zip)
  (println "Maybe you can write to him about" fav-hobby "or" second-hobby))

(print-contact-info john-smith)
;;= John Smith is the Sith Lord of Git at Functional Industries
;;= You can reach him at 555-555-5555
;;= He lives at 452 Lisp Ln. Macroville Kentucky 81321
;;= Maybe you can write to him about running or hiking
