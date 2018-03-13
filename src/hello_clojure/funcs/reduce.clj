(ns hello-clojure.funcs.reduce)

;; reduce对后面的函数，依次调用，并且前一次的返回值作为后一次调用的参数
;; 如果没有给函数初始参数，那么后面的函数会用collection里面的前两个作为参数
;; 如果有给了初始参数，那么就只会用collection的第一个元素作为参数

;;
;; (reduce f coll)(reduce f val coll)
;; 如上，recude函数调用方法：
;; reduece的函数只能是那种接受两个参数的,如果没有给一个初始值val，那么f会调用coll里面的头两个值作为f的参数，然后再将结果应用到f函数，再从coll里面取一个作为f的第二个参数，依次
;; 如果coll里面没有内容，那么同样的f需要能接受没有参数的调用，然后返回值
;; 如果coll只有一个元素，并且还没有给初始val值，那么就直接返回这个值，f是不会被调用的
;; 如果coll里面没有内容，但是提供了val的值，那么就直接返回这个val，f不会被调用

;; f不会被调用的情况
;; 也就是说，如果最开始只有一个参数的时候，就直接返回这个参数，f不会被调用，其它情况f都会被调用


(reduce max [1 6 3 4 5])


(reduce + 1 '(1 23 4 5))

(reduce * '(1 2 3))


(reduce + [1 2 3 4 5])
;;=> 15

(reduce + [])
;;=> 0

(reduce + [1])
;;=> 1

(reduce + [1 2])
;;=> 3

;; 给reduce的函数提供一个初始值
(reduce / 8 [])
;;=> 8

(reduce + 1 [2 3])
;;=> 6

(reduce (fn [new-map [key val]]
          (assoc new-map key (inc val)))
        {}
        {:max 30 :min 10})
;; => {:max 31, :min 11}


(reduce (fn [new-map [key val]]
          (if (> val 4)
            (assoc new-map key val)
            new-map))
        {}
        {:human 4.1 :critter 3.9})
;; => {:human 4.1}
