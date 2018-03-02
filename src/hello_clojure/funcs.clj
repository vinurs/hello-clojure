(ns hello-clojure.funcs
  (:require
   [clojure.repl :as r :refer [doc]]
   [clojure.java.javadoc :refer [javadoc]])
  (:gen-class))

;; 函数相关

;; 函数调用示例
(+ 1 2 3 4)
(* 1 2 3 4)
(first [1 2 3 4])

;; 返回另一个函数
(or + -)

;; 通过返回的函数来进行函数调用
((or + -) 1 2 3)
((and (= 1 1) +) 1 2 3)
((first [+ 0]) 1 2 3)

(inc 1.1)

;; map函数行为
;; map将一个参数(也就是函数)依次应用在第二个参数的每一个元素里面
;; 最后map返回的值是所有的结果组成的列表
(map inc '(1 2 3))
(map inc [1])
;; => (1 2 3 4)
(map inc [0 1 2 3])

;; 函数的调用过程，其实就是递归
;; 从左往右，从外往里，再从里往外，再向右继续计算
(+ (inc 199) (/ 100 (- 7 2)))
;; 先做遇到的第一个可以计算的(inc 199)
(+ 200 (/ 100 (- 7 2))) ; evaluated "(inc 199)"
;; 再寻找同一级里面后一个里面的可以计算的(- 7 2)
(+ 200 (/ 100 5)) ; evaluated (- 7 2)
;; 再往外找第一个可以计算的
(+ 200 20) ; evaluated (/ 100 5)
220 ; final evaluation


;; 特殊的函数调用形式，他们不会计算完所有的参数
;; 例如if，如果第一个为假，那么后面为真的语句是不会执行的
;; (if boolean-form then-form optional-else-form)


;; 函数定义
(defn too-enthusiastic
  "Return a cheer that might be a bit too enthusiastic"
  [name]
  (str "OH. MY. GOD! " name " YOU ARE MOST DEFINITELY LIKE THE BEST "
         "MAN SLASH WOMAN EVER I LOVE YOU AND WE SHOULD RUN AWAY SOMEWHERE"))
(too-enthusiastic "Zelda")

;; 查看map函数的用法
(doc map)

;; 函数的参数个数可以是任意个任意类型
(defn no-params
  []
  "I take no parameters!")
(defn one-param
  [x]
  (str "I take one parameter: " x))
(defn two-params
  [x y]
  (str "Two parameters! That's nothing! Pah! I will smoosh them "
       "together to spite you! " x y))

;; 同一个函数还可以同时有不同个参数，根据参数的个数来调用不同的部分
;; (defn multi-arity
;;   ;; 3-arity arguments and body
;;   ([first-arg second-arg third-arg]
;;    (do-things first-arg second-arg third-arg))
;;   ;; 2-arity arguments and body
;;   ([first-arg second-arg]
;;    (do-things first-arg second-arg))
;;   ;; 1-arity arguments and body
;;   ([first-arg]
;;    (do-things first-arg)))

(defn x-chop
  "Describe the kind of chop you're inflicting on someone"
  ([name chop-type]
   (str "I " chop-type " chop " name "! Take that!"))
  ([name]
   (x-chop name "karate")))
(x-chop "hello")
(x-chop "Kanye West" "slap")

(defn parting
  "returns a String parting"
  [name]
  (str "Goodbye, " name)) ; concatenation

(println (parting "Mark")) ; -> Goodbye, Mark

;; 函数必须先定义再使用。有时候可能做不到， 比如两个方法相互调用，
;; clojure采用了和C语言里面类似的做法： declare, 看例子:
;; (declare function-names)

;; 参数个数不定的函数的定义方式
(defn power [base & exponents]
  ;; Using java.lang.Math static method pow.
  (reduce #(Math/pow %1 %2) base exponents))
(power 2 3 4) ; 2 to the 3rd = 8; 8 to the 4th = 4096

(defn favorite-things
  [name & things]
  (str "Hi, " name ", here are my favorite things: "
       (clojure.string/join ", " things)))
(favorite-things "Doreen" "gum" "shoes" "kara-te")



;; 一个函数可以有不同个数的参数
(defn parting
  "returns a String parting in a given language"
  ([] (parting "World"))
  ([name] (parting name "en"))
  ([name language]
   ;; condp is similar to a case statement in other languages.
   ;; It is described in more detail later.
   ;; It is used here to take different actions based on whether the
   ;; parameter "language" is set to "en", "es" or something else.
   (condp = language
     "en" (str "Goodbye, " name)
     "es" (str "Adios, " name)
     (throw (IllegalArgumentException.
             (str "unsupported language " language))))))

(println (parting)) ; -> Goodbye, World
(println (parting "Mark")) ; -> Goodbye, Mark
(println (parting "Mark" "es")) ; -> Adios, Mark
(println (parting "Mark", "xy"))
;; -> java.lang.IllegalArgumentException: unsupported language xy

;; Return the first element of a collection
(defn my-first
  [[first-thing]] ; Notice that first-thing is within a vector
  first-thing)
(my-first ["oven" "bike" "war-axe"])


;; 解构？destructuring
;; 这个概念过会儿再理解吧




;; 匿名函数，可以不需要名字的函数
;; 匿名函数对于那些只在一个地方使用的函数比较有用
(def years [1940 1944 1961 1985 1987])
(filter (fn [year] (even? year)) years) ; long way w/ named arguments -> (1940 1944)
(filter #(even? %) years) ; short way where % refers to the argument

(map (fn [name]
       (str "Hi, " name))
     ["Darth Vader" "Mr. Magoo"])

;; 匿名函数的调用
((fn [x] (* x 3)) 8)

(def my-special-multiplier
  (fn [x] (* x 3)))
(my-special-multiplier 12)

;; 更紧凑的定义匿名函数的方法
(#(* % 3) 8)
(map #(str "Hi, " %)
     ["Darth Vader" "Mr. Magoo"])
;; 我们来看看紧凑的方法定义的步骤
;; 这是一个函数调用
(* 3 8)
;; 将这个函数调用转成紧凑匿名函数
#(* % 8)
;; %为占位符，标识参数，如果有多个参数的时候，那么用%1, %2来表示, %就表示%1, %&表示可以参数的剩余参数
(#(str %1 " and " %2) "cornbread" "butter beans")

(#(identity %&) 1 "blarg" :yip)

;; 不过还是用fn的方式来定义函数比较直观，这种紧凑的方法看起来太费事


;; 函数返回函数
(defn inc-maker
  "Create a custom incrementor"
  [inc-by]
  #(+ % inc-by))

(def inc3 (inc-maker 3))
(inc3 7)
