(ns hello-clojure.datatypes.strings)

;; 字符
\a

;; 字符串
"abc"

;; 查看一个数据类型
(class \a)

;; 这个就是非法的
;; \aaa


;; 多行字符串
"hello
you are welcome"

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


;; unicode的表示方法
\u00ff
;; 八进制表示
\o41
\o12
;; 貌似不支持16进制表示，不过一般情况下也用不到


;; 特殊字符
\space
\newline
\return
\space
\formfeed
\backspace
\tab
