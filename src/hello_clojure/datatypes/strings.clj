(ns hello-clojure.datatypes.strings)

;; 字符
\a

;; 字符串
"abc"

"Lord Voldemort"
"\"He who must not be named\""
"\"Great cow of Moscow!\" - Hermes Conrad"


(def my-name "Chewbacca")
(str "\"Uggllglglglglglglglll\" - " my-name)
;; => "Uggllglglglglglglglll" - Chewbacca


;; 字符串
"jam"

;; 字符
\j
\[

"a string"

(str "It was the panda " "in the library " "with a dust buster")

;; 字符串是以双引号引用表示
;; 字符串连接操作
(def my-name "Chewbacca")
(str "\"Uggllglglglglglglglll\" - " name)
