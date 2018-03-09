(ns hello-clojure.funcs.comp)

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
