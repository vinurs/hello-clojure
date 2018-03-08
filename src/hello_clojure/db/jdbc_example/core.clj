(ns hello-clojure.db.jdbc-example.core
  (:require [clojure.java.jdbc :as j])
  )


;; 访问连接数据库的最基本的方法

(def mysql-db {:dbtype "mysql"
               :dbname "reporting"
               :user "root"
               :password "123456"})

;; 使用jdbc提供的方法操控数据库
;; 插入数据库
(j/insert-multi! mysql-db :fruit ;; 数据库名称
                 [{:name "Apple" :appearance "rosy" :cost 24}
                  {:name "Orange" :appearance "round" :cost 49}])
;; ({:generated_key 1} {:generated_key 2})

;; 查询数据库
(j/query mysql-db
         ["select * from fruit where appearance = ?" "rosy"]
         {:row-fn :cost})
;; (24)

;; 更新数据库
;; (j/query mysql-db
;;          ["update fruit set cost=100 where id = ?" "rosy"]
;;          )
