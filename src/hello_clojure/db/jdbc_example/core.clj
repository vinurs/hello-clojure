(ns hello-clojure.db.jdbc-example.core
  (:require [clojure.java.jdbc :as j])
  )


;; 访问连接数据库的最基本的方法，使用jdbc库

;; 定义数据库的基本信息
(def mysql-db-spec {:dbtype "mysql"
                    ;; 数据库名字
                    :dbname "reporting"
                    ;; 用户名
                    :user "root"
                    ;; 密码
                    :password "123456"
                    ;; TODO: 还可以提供一些别的信息的，后面补全mysql相关信息
                    })



(def postgresql-db-spec {:dbtype "postgresql"
                         ;; 数据库名字
                         :dbname "xxx"
                         ;; 用户名
                         :user "xxx"
                         ;; 密码
                         :password "xxx"
                         :host "xxxx"
                         :port 3432
                         })


(def mssql-db-spec {:dbtype "mssql"
                    ;; 数据库名字
                    :dbname "xxx"
                    ;; 用户名
                    :user "xxx"
                    ;; 密码
                    :password "xxx"
                    :host "xxx"
                    :port 1433
                    })

;; (j/query postgresql-db-spec ["SELECT 3*5 AS result"])

;; (j/query mssql-db-spec ["SELECT 3*5 AS result"])


;; 一个简单的使用
(j/query mysql-db-spec ["SELECT 3*5 AS result"])
;; => {:result 15}


;; 创建表
(def create-fruit-table-ddl
  ;; 构造一个ddl(数据定义语言)
  (j/create-table-ddl :fruit
                      [[:id :int]
                       [:name "varchar(32)"]
                       [:appearance "varchar(32)"]
                       [:cost :int]
                       [:grade :real]]))

;; 使用命令来使用刚才ddl创建表，同时建立索引
;; (j/db-do-commands mysql-db-spec
;;                   [create-fruit-table-ddl
;;                    ])

;; 数据库的基本操作，创建、读取、更新、删除
;; Create，也就是插入
;; 插入数据库，这个会自动建立连接、销毁连接，为什么要提这个，为后面我们使用连接池作铺垫
;; (j/insert! mysql-db-spec
;;            :fruit {:name "hello"
;;                    :appearance "large"
;;                    :cost 32
;;                    :grade 10})
;; ;; Read
;; (j/query   mysql-db-spec
;;            ["SELECT * FROM fruit WHERE name = ?" "Apple"])

;; ;; Update
;; (j/update! mysql-db-spec :fruit
;;            ;; 字段更新
;;            {:name "nihao"
;;             :cost "100"}
;;            ;; where语句
;;            ["id = ?" 1])

;; ;; Delete
;; (j/delete! mysql-db-spec :fruit
;;            ;; where语句
;;            ["id = ?" 2])

;; ;; 删除数据库
;; ;; 定义删除的ddl
;; (def drop-fruit-table-ddl (j/drop-table-ddl :fruit))
;; ;; (j/db-do-commands mysql-db-spec
;; ;;                   [drop-fruit-table-ddl])

;; ;; 使用jdbc提供的方法操控数据库
;; ;; 插入数据库，这个会自动建立连接、销毁连接，为什么要提这个，为后面我们使用连接池作铺垫
;; ;; 同时插入多行数据
;; (j/insert-multi! mysql-db-spec :fruit ;; 数据库表名称
;;                  [{:name "Apple" :appearance "rosy" :cost 24}
;;                   {:name "Orange" :appearance "round" :cost 49}])
;; ;; ({:generated_key 1} {:generated_key 2})

;; ;; 查询数据库
;; (j/query mysql-db-spec
;;          ["select * from fruit where appearance = ?" "rosy"]
;;          ;; 对结果进行处理
;;          {:row-fn :cost})
;; ;; (24)


;; ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; TODO: 上面是最基本的使用方法，后面把sql详细的使用方法再一个个去试验一下
;; ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;





;; 但是每次执行sql语句都自动执行连接数据库、销毁连接这个操作，未免耗费资源太大，这时候我们引入连接池的概念



;; with-db-connection这个用来创建一个链接，然后他body里面的内容都可以用这个连接，出了这个函数以后连接才被销毁
;; 这个可以用来一个连接做多条sql语句
