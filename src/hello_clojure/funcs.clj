(ns hello-clojure.funcs
  (:gen-class))


;; 函数定义相关


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


;; 匿名函数
;; 匿名函数对于那些只在一个地方使用的函数比较有用
(def years [1940 1944 1961 1985 1987])
(filter (fn [year] (even? year)) years) ; long way w/ named arguments -> (1940 1944)
(filter #(even? %) years) ; short way where % refers to the argument
