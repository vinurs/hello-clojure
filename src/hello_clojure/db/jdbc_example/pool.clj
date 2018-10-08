(ns hello-clojure.db.jdbc-example.pool
  (:require [hikari-cp.core :refer :all]
            ;; [mount.core :refer [defstate]]
            [clojure.java.jdbc :as jdbc]
            ))

(def pool-spec
  {;; :server-name "localhost"
   :jdbc-url "jdbc:mysql://localhost/reporting"
   :username "root"
   :password "123456"
   :database-name "reporting"})

(def datasource-options {
                         ;; :auto-commit        true
                         ;; :read-only          false
                         ;; :connection-timeout 30000
                         ;; :validation-timeout 5000
                         ;; :idle-timeout       600000
                         ;; :max-lifetime       1800000
                         :minimum-idle       10
                         :maximum-pool-size  10
                         :pool-name          "db-pool"
                         :adapter            "mysql"
                         :username           "root"
                         :password           "123456"
                         :database-name      "reporting"
                         :server-name        "localhost"
                         :port-number        3306
                         ;; :register-mbeans    false
                         })

;; 这里构造了一个内存池对象
;; (def datasource
;;   (make-datasource datasource-options))


;; 对于用db-spec的地方传参用内存池对象来，这样就可以使用内存池了
;; (jdbc/with-db-connection [conn {:datasource datasource}]
;;   (let [rows (jdbc/query conn ["select * from fruit where appearance = ?" "rosy"])]
;;     (println rows)))

;; (close-datasource datasource)


;; (defstate ^:dynamic *db*
;;   :start (conman/connect! pool-spec)
;;   :stop (conman/disconnect! *db*))
