(ns hello-clojure.destructure)

;; ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; 解构，优雅的代码
;; ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;



;; 为什么需要解构呢?
;; 因为我们的参数一般都是map、vector、list之类的，我们需要取里面的某个位置的值
;; 手动做太麻烦，如果能自动做就更好了，这就有了解构

;; 手动解构，用函数来获取每一个collection里面的值
(def my-line [[5 10] [10 20]])
;; 如果没有解构，但是我们需要获取里面的元素的话只能按照下面的方法来，比较丑陋
;; 不紧丑陋，而且容易出错
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

;; 这个对于一级collection效果还不明显，如果collection是嵌套的，那么这个好处就显而易见了

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



;; ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; 支持解构的地方有: let/defn/fn/loop
;; 解构有两种：顺序解构/map解构
;; 顺序解构: list/vector/seq/map/java数组/字符串
;; map解构: map
;; ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(def v [42 "foo" 99.2 [5 12]])
(let [[x y z] v]
  (+ x z))

;; 嵌套解构
(let [[x _ _ [y z]] v]
  (+ x y z))

;; 除了依次解构完成，还支持剩余的概念
(let [[x & rest] v]
  rest)
;; 剩余的被组成了一个list

;; 解构的同时还要保持之前的值完好，同样绑定到一个本地绑定
(let [[x _ z & rest :as original-vector] v]
  (conj original-vector (+ x z)))


;; map解构
(def m {:a 5 :b 6
        :c [7 8 9]
        :d {:e 10 :f 11}
        "foo" 88
        42 false})
(let [{a :a b :b} m]
  (+ a b))

(let [{f "foo"} m]
  (+ f 12))

(let [{v 42} m]
  (if v 1 0))


;; 如果用map解构来解构数组、字符串的话，那么解析的key就是数组的下标
(let [{x 3 y 8} [12 0 0 -18 44 6 0 0 1]]
  (+ x y))

;; 不能用map解构列表
;; (let [{x 3 y 8} '(12 0 0 -18 44 6 0 0 1)]
;;   (+ x y))

;; map嵌套解构
(let [{{e :e} :d} m]
  (* 2 e))

;; 顺序解构跟map解构相结合
(let [{[a b c] :c} m]
  (* a b c))

(def map-in-vector ["james" {:birthday "1987-09-06"}])
(let [[name {bd :birthday}] map-in-vector]
  (str name "was born on " bd))

;; 解构的同时保持原来的值
(let [{r1 :x r2 :y :as randoms}
      (zipmap [:x :y :z] (repeatedly (partial rand-int 10)))]
  (assoc randoms :sum (+ r1 r2)))

;; 如果map里面没有对应的key，那么我们可以用:or来给一个默认值
(let [{k :unknown x :a
       :or {k 50}} m]
  (+ k x))

;; 上面的代码还需要简化
(let [{k :unknown x :a} m
      k (or k 50)]
  (+ k x))


;; or能区分一个bind到底有没有值
(let [{opt1 :option} {:option false}
      opt1 (or opt1 true)
      {opt2 :option :or {opt2 true}} {:option false}]
  {:opt1 opt1 :opt2 opt2})

;; 如果map里面的key都是用的clojure的关键字类型的话，那么我们在解构的时候可以简化
;; 例如下面，可以看出每个关键字我们都写了两遍
(def chas {:name "chas" :age 31 :location "massachuets"})
(let [{name :name age :age location :location} chas]
  (format "%s is %s years old, and live in %s." name age location))

;; 进行简化，用:keys来表示里面的同名的key
(let [{:keys [name age location]} chas]
  (format "%s is %s years old, and live in %s." name age location))

;; 由于clojure里面的key可以是任意类型，所以除了关键字类型，还可以是string类型
;; 如下
(def christophe {"name" "chirosto" "age" 31 "location" "british hello"})
(let [{:strs [name age location]} christophe]
  (format "%s is %s years old, and live in %s." name age location))


;; 如果key是符号类型，那么我们可以通过:syms来进行调用
(def christophe {'name "chirosto" 'age 31 'location "british hello"})
(let [{:syms [name age location]} christophe]
  (format "%s is %s years old, and live in %s." name age location))

;; 当然，一般我们map里面的key更习惯用clojure的关键字类型
;; 所以:keys用得比较多


;; 我们还可以对顺序集合的声誉部分使用map解构来进行操作
(def user-info ["robbrt" 2011 :name "Bob" :city "boston"])
(let [[username  account-year & extra-info] user-info
      {:keys [name city]} (apply hash-map extra-info)]
  (format "%s is in %s" name city))

;; 如果剩余个数是奇数呢，那么下面的代码就会出错，就是hash-map会出错
;; (def user-info ["robbrt" 2011 :name "Bob" :city "boston" 1])
;; (let [[username  account-year & extra-info] user-info
;;       {:keys [name city]} (apply hash-map extra-info)]
;;   (format "%s is in %s" name city))

;; hash-map遇到奇数会出错
;; (hash-map 1 2 3 )

;; 如果是偶数，那么就可以直接让他当做map来处理
(def user-info ["robbrt" 2011 :name "Bob" :city "boston"])
(let [[username  account-year & {:keys [name city]}] user-info]
  (format "%s is in %s" name city))

;; 同样的，如果是奇数个，就会出错
;; (def user-info ["robbrt" 2011 :name "Bob" :city "boston" 1])
;; (let [[username  account-year & {:keys [name city]}] user-info]
;;   (format "%s is in %s" name city))


;; 如果要解构map，那么就要用map的形式来作为解构参数
;; 对于map的解构，解构的是对应的key的值
;; 将:lat的值解构到lat里面，同样将:lng的值解构到lng里面
(defn announce-treasure-location
  [{lat :lat lng :lng}]
  (println (str "Treasure lat: " lat))
  (println (str "Treasure lng: " lng)))
(announce-treasure-location {:lat 28.22 :lng 81.33})
(announce-treasure-location {28.22 :lat :lng 81.33})
(:lat {:lat 28.22 })


;; 一种更简单的解构map的方式
(defn announce-treasure-location
  [{:keys [lat lng]}]
  (println (str "Treasure lat: " lat))
  (println (str "Treasure lng: " lng)))
(announce-treasure-location {:lat 28.22 :lng 81.33})




;; 解构的同时保留原来的map
;; (defn receive-treasure-location
;;   [{:keys [lat lng] :as treasure-location}]
;;   (println (str "Treasure lat: " lat))
;;   (println (str "Treasure lng: " lng))
;;   ;; One would assume that this would put in new coordinates for your ship
;;   (steer-ship! treasure-location))


;; 关于解构的理解
;; 参考这里https://wizardforcel.gitbooks.io/clojure-fpftj/content/26.html
;; http://blog.csdn.net/lord_is_layuping/article/details/47061287
;; https://clojure.org/guides/destructuring

(defn summer-sales-percentage
  ;; The keywords below indicate the keys whose values
  ;; should be extracted by destructuring.
  ;; The non-keywords are the local bindings
  ;; into which the values are placed.
  [{june :june july :july august :august :as all}]
  (let [summer-sales (+ june july august)
        all-sales (apply + (vals all))]
    (/ summer-sales all-sales)))

(def sales {
            :january   100 :february 200 :march      0 :april    300
            :may       200 :june     100 :july     400 :august   500
            :september 200 :october  300 :november 400 :december 600})
;; ratio reduced from 1000/3300 -> 10/33
(summer-sales-percentage sales)

;; 从上面可以看出，解构只用在函数或者宏的参数里面或者是let变量里面
;; 向量、list、map都可以用来解构
;; 向量依次解构里面每个元素
;; map依次解构里面的key



;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; http://blog.brunobonacci.com/2014/11/16/clojure-complete-guide-to-destructuring/#cheatsheet
;; 是这里的例子
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; 返回当前的经纬度信息
(defn current-position []
  [51.503331, -0.119500])

;; 打印当前的经纬度
(defn geohash [lat lng]
  ;; this function take two separate values as params.
  ;; and it return a geohash for that position
  (println "geohash:" lat lng))

;; 如果没有解构，那么我们需要这样处理参数
;; 只能一个个把参数解出来
(let [coord (current-position)
      lat   (first coord)
      lng   (second coord)]
  (geohash lat lng))

;; 解构就是让你把一个结构化的数据对应位置的数据提取出来
;; 有了解构以后，代码就清晰多了
(let [[lat lng] (current-position)]
  (geohash lat lng))

;; 解构vector
(let [[one two three] [1 2 3]]
  (println "one:" one)
  (println "two:" two)
  (println "three:" three))

;; 解构list
(let [[one two three] '(1 2 3)]
  (println "one:" one)
  (println "two:" two)
  (println "three:" three))

;; 解构list
(let [[one two three] (range 1 4)]
  (println "one:" one)
  (println "two:" two)
  (println "three:" three))

;; 有时候有些位置的数据我们是不需要的
;; 那么这时候我们可以只解构特定的位置
(let [[_ _ three] (range 1 10)]
  three)

;; 解构剩余参数到某个变量里面
(let [[_ _ three & numbers] (range 1 10)]
  numbers)
;;=> (4 5 6 7 8 9)

;; 如果想解构的同时还保留着原始的数据
;; 那么，用关键字来引用原始的数据
(let [[_ _ three & numbers :as all-numbers] (range 1 10)]
  all-numbers)
;;=> (1 2 3 4 5 6 7 8 9)

;; map解构
(defn current-position []
  {:lat 51.503331, :lng -0.119500
   "a" "bbb" 1 2})

;; 没有解构的map
(let [coord (current-position)
      lat   (:lat coord)
      lng   (:lng coord)]
  (geohash lat lng))

;; 有了解构以后的map
;; map解构就是一个变量一个key
(let [{lat :lat, lng :lng a "a" v1 1} (current-position)]
  (geohash lat lng)
  (println a v1))

;; 对于关键字还可以统一解构
;; 例如下面的lat就会去寻找:lat关键字
(let [{:keys [lat lng]
       a "a" v1 1} (current-position)]
  (geohash lat lng)
  (println a v1))

;; 解构的同时保留原始数据
(let [{lat :lat, lng :lng :as coord} (current-position)]
  (println "calculating geohash for coordinates: " coord)
  (geohash lat lng))

(let [{:keys [:lat :lng] :as coord} (current-position)]
  (println "calculating geohash for coordinates: " coord)
  (geohash lat lng))


(let [{:keys [lat lng] :as coord} (current-position)]
  (println "calculating geohash for coordinates: " coord)
  (geohash lat lng))


;; 如果map的key是str，那么就要用strs来进行解构
;; 对于key是str的解构
(let [{:strs [lat lng] :as coord} {"lat" 51.503331, "lng" -0.119500}]
  (println "calculating geohash for coordinates: " coord)
  (geohash lat lng))


;; key跟str混合解构
(let [{:strs [lng] :keys [lat] :as coord} {:lat 51.503331, "lng" -0.119500}]
  (println "calculating geohash for coordinates: " coord)
  (println lng lat)
  (geohash lat lng))


;; 同样的map的key也有可能是符号，那么用syms来进行解构
;; key是符号的解构
(let [{:syms [lat lng] :as coord} {'lat 51.503331, 'lng -0.119500}]
  (println "calculating geohash for coordinates: " coord)
  (geohash lat lng))


;; 有时候map里面可能没有一些key，这时候我们在想最好这些能有一个默认值，
;; 那么:or就是用来做这个的
;; map解构的时候可以对缺失的key加上默认值
(defn connect-db [{:keys [host port db-name username password]
                   :or   {host     "localhost"
                          port     12345
                          db-name  "my-db"
                          username "db-user"
                          password "secret"}
                   :as cfg}]
  (println "connecting to:" host "port:" port "db-name:" db-name
           "username:" username "password:" password))

(connect-db {:host "server"})
;; => connecting to: server port: 12345 db-name: my-db username: db-user password: secret

(connect-db {:host "server" :username "user2" :password "Passowrd1"})
;; => connecting to: server port: 12345 db-name: my-db username: user2 password: Passowrd1


;; 函数参数其实本身也就是一种解构
;; 利用解构对函数可变参数的解构，不过这个情况比较特殊，很少用
;; 剩余参数要能配对key-map出现
(defn connect-db [host ; mandatory parameter
                  & {:keys [port db-name username password]
                     :or   {port     12345
                            db-name  "my-db"
                            username "db-user"
                            password "secret"}}]
  (println "connecting to:" host "port:" port "db-name:" db-name
           "username:" username "password:" password))

(connect-db "server")
;; connecting to: server port: 12345 db-name: my-db username: db-user password: secret

(connect-db "server" :username "user2" :password "Passowrd1")
;; connecting to: server port: 12345 db-name: my-db username: user2 password: Passowrd1


(def contact
  {:firstname "John"
   :lastname  "Smith"
   :age       25
   :phone     "+44.123.456.789"
   :emails    "jsmith@company.com"})

(let [{firstname :firstname age :age} contact]
  (str "Hi, I'm " firstname " and I'm " age " years old."))
;;=> "Hi, I'm John and I'm 25 years old."

;; 解构的时候不跟key同名
(let [{name :firstname years-old :age} contact]
  (str "Hi, I'm " name " and I'm " years-old " years old."))
;;=> "Hi, I'm John and I'm 25 years old."


(defn distance [{x1 :x y1 :y} {x2 :x y2 :y}]
  (let [square (fn [n] (*' n n))]
    (Math/sqrt
     (+ (square (- x1 x2))
        (square (- y1 y2))))))

(distance {:x 3, :y 2} {:x 9, :y 7})
;;=> 7.810249675906654

;; map其实也可以看做一个sequence，每一个元素都是一个key-value
(map (fn [[k v]] (str k " -> " v)) contact)


;; 嵌套解构
;; source: wikipedia
(def inventor-of-the-day
  ["John" "McCarthy"
   "1927-09-04"
   "LISP"
   ["Turing Award (1971)"
    "Computer Pioneer Award (1985)"
    "Kyoto Prize (1988)"
    "National Medal of Science (1990)"
    "Benjamin Franklin Medal (2003)"]])

;; 嵌套解构vector跟一层解构一样理解就行
(let [[firstname lastname _ _ [first-award & other-awards]] inventor-of-the-day]
  (str firstname ", " lastname "'s first notable award was: " first-award))
;;=> "John, McCarthy's first notable award was: Turing Award (1971)"


;; 嵌套解构map
(def contact
  {:firstname "John"
   :lastname  "Smith"
   :age       25
   :contacts {:phone "+44.123.456.789"
              :emails {:work "jsmith@company.com"
                       :personal "jsmith@some-email.com"}}})

;; Just the top level
(let [{lastname :lastname} contact]
  (println lastname ))
;; Smith

;; One nested level
(let [{lastname :lastname
       {phone :phone} :contacts} contact]
  (println lastname phone))
;; Smith +44.123.456.789

(let [{:keys [firstname lastname]
       {:keys [phone] } :contacts} contact]
  (println firstname lastname phone ))
;; John Smith +44.123.456.789

(let [{:keys [firstname lastname]
       {:keys [phone]
        {:keys [work personal]} :emails } :contacts} contact]
  (println firstname lastname phone work personal))
;;John Smith +44.123.456.789 jsmith@company.com jsmith@some-email.com


;; 这里还有一些不常用的解构的形式
;; 把vector当做map来解构的时候key其实就是下标
(let [{one 1 two 2} [0 1 2]]
  (println one two))
;; 1 2

;; 如果想获取第100跟200个元素，那么就直接这样解构就好了
(let [{v1 100 v2 200} (apply vector (range 500))]
  (println v1 v2))
;; 100 200


;; sets可以当做map解构，他们的key就是他们自己
(let [{:strs [blue white black]} #{"blue" "white" "red" "green"}]
  (println blue white black))
;; blue white nil


(defn ls [path & flags]
  (let [{:keys [all long-format human-readable sort-by-time]}
        (set flags)]
    ;; now you can test your flags
    (when long-format (comment do someting))
    ;; ....
    ))

;; 解构有namespace限定的key
;; namespaced keys
(def contact
  {:firstname          "John"
   :lastname           "Smith"
   :age                25
   :corporate/id       "LDF123"
   :corporate/position "CEO"})

;; notice how the namespaced `:corporate/position` is extracted
;; the symbol which is bound to the value has no namespace
(let [{:keys [lastname corporate/position]} contact]
  (println lastname "-" position))
;; Smith - CEO


;; like for normal keys, the vector of symbols can be
;; replaced with a vector of keywords
(let [{:keys [:lastname :corporate/position]} contact]
  (println lastname "-" position))
;; Smith - CEO


;; Clojure 1.9
;; a default value might be provided
;; 提供默认值
(let [{:keys [lastname corporate/position]
       :or {position "Employee"}} contact]
  (println lastname "-" position))

;; Clojure 1.8 or previous
;; a default value might be provided
;; (let [{:keys [lastname corporate/position]
;;        :or {corporate/position "Employee"}} contact]
;;   (println lastname "-" position))
;; Smith - CEO


(def contact
  {:firstname "John"
   :lastname  "Smith"
   :age       25
   ::id       "LDF123"
   ::position "CEO"})

;; Clojure 1.9
(let [{:keys [lastname ::position]
       :or {position "Employee"}} contact]
  (println lastname "-" position))
;; Smith - CEO

(let [{:keys [lastname position]
       :or {position "Employee"}} contact]
  (println lastname "-" position))


;; Clojure 1.8 or previous
;; (let [{:keys [lastname ::position]
;;        :or {::position "Employee"}} contact]
;;   (println lastname "-" position))
;; Smith - CEO


;; 同样的有namespace限定的符号解构也是一样的
;; 有namespace限定的符号解构
(def contact
  {'firstname          "John"
   'lastname           "Smith"
   'age                25
   'corporate/id       "LDF123"
   'corporate/position "CEO"})

(let [{:syms [lastname corporate/position]} contact]
  (println lastname "-" position))
;; Smith - CEO

(def contact
  {:firstname          "John"
   :lastname           "Smith"
   :age                25
   :corporate/id       "LDF123"
   :corporate/position "CEO"})

(defn contact-line
  ;; map destructuring
  [{:keys [firstname lastname corporate/position] :as contact}]
  ;; seq destructuring
  (let [initial firstname]
    (str "Mr " initial ". " lastname ", " position)))

(contact-line contact)
;;=> "Mr John. Smith, CEO"



;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Clojure destructuring cheatsheet
;; all the following destructuring forms can be used in any of the
;; Clojure's `let` derived bindings such as function's parameters,
;; `let`, `loop`, `binding`, `for`, `doseq`, etc.

;; list, vectors and sequences
;; [zero _ _ three & four-and-more :as numbers] (range)
;; zero = 0, three = 3, four-and-more = (4 5 6 7 ...),
;; numbers = (0 1 2 3 4 5 6 7 ...)

;; maps and sets
;; {:keys [firstname lastname] :as person} {:firstname "John"  :lastname "Smith"}
;; {:keys [:firstname :lastname] :as person} {:firstname "John"  :lastname "Smith"}
;; {:strs [firstname lastname] :as person} {"firstname" "John" "lastname" "Smith"}
;; {:syms [firstname lastname] :as person} {'firstname "John"  'lastname "Smith"}
;; firstname = John, lastname = Smith, person = {:firstname "John" :lastname "Smith"}

;; maps destructuring with different local vars names
;; {name :firstname family-name :lastname :as person} {:firstname "John"  :lastname "Smith"}
;; name = John, family-name = Smith, person = {:firstname "John" :lastname "Smith"}

;; default values
;; {:keys [firstname lastname] :as person
;;  :or {firstname "Jane"  :lastname "Bloggs"}} {:firstname "John"}
;; firstname = John, lastname = Bloggs, person = {:firstname "John"}

;; nested destructuring
;; [[x1 y1] [x2 y2] [_ _ z]]  [[2 3] [5 6] [9 8 7]]
;; x1 = 2, y1 = 3, x2 = 5, y2 = 6, z = 7

;; {:keys [firstname lastname]
;;     {:keys [phone]} :contact} {:firstname "John" :lastname "Smith" :contact {:phone "0987654321"}}
;; firstname = John, lastname = Smith, phone = 0987654321

;; namespaced keys in maps and sets
;; {:keys [contact/firstname contact/lastname] :as person}     {:contact/firstname "John" :contact/lastname "Smith"}
;; {:keys [:contact/firstname :contact/lastname] :as person}   {:contact/firstname "John" :contact/lastname "Smith"}
;; {:keys [::firstname ::lastname] :as person}                 {::firstname "John"        ::lastname "Smith"}
;; {:syms [contact/firstname contact/lastname] :as person}     {'contact/firstname "John"     'contact/lastname "Smith"}
;; firstname = John, lastname = Smith, person = {:firstname "John" :lastname "Smith"}
