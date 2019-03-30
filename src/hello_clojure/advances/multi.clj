(ns hello-clojure.advances.multi)

;; 多态方法
(defmulti complier :os)

(defmethod complier ::unix [m]
  (get m :complier)
  )

(defmethod complier ::osx [m]
  (get m :complier)
  )

(complier {:os ::unix :complier "cc"})
(complier {:os ::osx :complier "gcc"})
