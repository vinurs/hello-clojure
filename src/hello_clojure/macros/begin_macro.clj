(ns hello-clojure.macros.begin-macro)


(defmacro backwards
  [form]
  (reverse form))

(backwards (" backwards" " am" "I" str))


;; 对宏进行展开
(macroexpand '(when boolean-expression
                expression-1
                expression-2
                expression-3))

(defmacro infix
  "Use this macro when you pine for the notation of your childhood"
  [infixed]
  (list (second infixed) (first infixed) (last infixed)))

;; 宏会先展开，然后在当做代码进行运输
(infix (1 + 1))
(macroexpand '(infix (1 + 1)))

;; 对宏参数像函数参数那样解构
(defmacro infix-2
  [[operand1 op operand2]]
  (list op operand1 operand2))


(defmacro and
  "Evaluates exprs one at a time, from left to right. If a form
   returns logical false (nil or false), and returns that value and
   doesn't evaluate any of the other expressions, otherwise it returns
   the value of the last expr. (and) returns true."
  {:added "1.0"}
  ([] true)
  ([x] x)
  ([x & next]
   `(let [and# ~x]
      (if and# (and ~@next) and#))))

(+ 1 2)

(quote (+ 1 2))

+

'dr-jekyll-and-richard-simmons

(macroexpand '(when (the-cows-come :home)
                (call me :pappy)
                (slap me :silly)))

`(+ 1 2)


(defmacro code-critic
  "Phrases are courtesy Hermes Conrad from Futurama"
  [bad good]
  (list 'do
        (list 'println
              "Great squid of Madrid, this is bad code:"
              (list 'quote bad))
        (list 'println
              "Sweet gorilla of Manila, this is good code:"
              (list 'quote good))))
(code-critic (1 + 1) (+ 1 1))

(def message "Good job!")
