(ns hello-clojure.funcs.comp)

;; comp就是专门用来组合多个函数的
;; 为什么需要徐鹤函数，因为我们经常用函数式编程，这样的话一个函数的结果作为另一个函数的输入就很正常，
;; 这样我们就可以把多个函数组合起来，然他们自己去调用，我们给定最初的参数就可以，
;; 所以我们就需要comp函数了
;; comp会返回一个新的函数，这个函数的参数就是最右边的函数的参数
;; comp on the functions f1, f2, ... fn, creates a new function g such that g(x1, x2, ... xn)
;; (comp)(comp f)(comp f g)(comp f g & fs)
;; 组合多个函数，然后首先调用最右边的函数，返回值作为参数传给倒数第二个，依次这样进行调用
;; 函数组合
(defn negated-sum-str
  [& numbers]
  (str (- (apply + numbers))))

(negated-sum-str 1 2 3 4 5)

;; 但是用comp来将上面的函数重写会更加简洁
(def negated-sum-str (comp str - +))
(negated-sum-str 1 2 3 4)

((comp + - * /) 1 2 3 4 5)


(def character
  {:name "Smooches McCutes"
   :attributes {:intelligence 10
                :strength 4
                :dexterity 5}})
(def c-int (comp :intelligence :attributes))
(def c-str (comp :strength :attributes))
(def c-dex (comp :dexterity :attributes))

(c-int character)
;; => 10

(c-str character)
;; => 4
(c-dex character)
;; => 5
