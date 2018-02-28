(ns hello-clojure.java
  ;; 引用java类库
  (:import [java.util Calendar GregorianCalendar])
  (:gen-class))

;; 跟java之间相互调用


;; 访问java里面的常量
(. java.util.Calendar APRIL) ; -> 3
(. Calendar APRIL) ; works if the Calendar class was imported
java.util.Calendar/APRIL
Calendar/APRIL ; works if the Calendar class was imported

(. Math pow 2 4) ; -> 16.0
(Math/pow 2 4)


;; 创建新的java对象
(def calendar (new GregorianCalendar 2008 Calendar/APRIL 16)) ; April 16, 2008
calendar
(def calendar (GregorianCalendar. 2008 Calendar/APRIL 16))


(. calendar add Calendar/MONTH 2)
(. calendar get Calendar/MONTH) ; -> 5
(.add calendar Calendar/MONTH 2)
(.get calendar Calendar/MONTH) ; -> 7

(. (. calendar getTimeZone) getDisplayName) ; long way
(.. calendar getTimeZone getDisplayName) ; -> "Central Standard Time"


;; java线程相关
(defn delayed-print [ms text]
  (Thread/sleep ms)
  (println text))

;; Pass an anonymous function that invokes delayed-print
;; to the Thread constructor so the delayed-print function
;; executes inside the Thread instead of
;; while the Thread object is being created.
(.start (Thread. #(delayed-print 1000 ", World!"))) ; prints 2nd
(print "Hello") ; prints 1st
;; output is "Hello, World!"




;; 异常处理
