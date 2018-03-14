(ns hello-clojure.db.jdbc-example.conman
  (:require [mount.core :refer [defstate]]
            [conman.core :as conman]))


;; 使用conman的封装来进行数据库的调用
(def pool-spec {;; :auto-commit        true
                ;; :read-only          false
                ;; :connection-timeout 30000
                ;; :validation-timeout 5000
                ;; :idle-timeout       600000
                ;; :max-lifetime       1800000
                :minimum-idle       10
                :maximum-pool-size  2000
                :pool-name          "db-pool"
                :adapter            "mysql"
                :username           "root"
                :password           "123456"
                :database-name      "reporting"
                :server-name        "localhost"
                :port-number        3306
                ;; :register-mbeans    false
                })

;; conman是更进一步的封装了

;; 将内存池跟*db*绑定
(defstate ^:dynamic *db*
  :start (conman/connect! pool-spec)
  :stop (conman/disconnect! *db*))

;; 这个sql文件里面的所有的函数都会默认调用*db*来进行操作
(conman/bind-connection *db* "sql/queries.sql")

;; 这里要手动执行一下这个start，要不然还是没有创建*db*，参考下面的回答
;; https://github.com/luminus-framework/conman/issues/18
(mount.core/start)


(create-user! {:id "foo" :first_name "Bob" :last_name "Bobberton" :email nil :pass nil})

(get-all-users)
