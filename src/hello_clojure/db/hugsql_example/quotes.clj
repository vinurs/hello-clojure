(ns hello-clojure.db.hugsql-example.quotes
  (:require [hugsql.core :as hugsql]
            [hello-clojure.db.hugsql-example.db :refer [db]]))

;; 创建对应的函数
(hugsql/def-db-fns "sql/quotes.sql")

;; 创建对应的vector
(hugsql/def-sqlvec-fns "sql/quotes.sql")

;; 通过sql语句里面定义的函数来进行调用
;; (create-quotes-table db)
;; (create-quotes-table-sqlvec db)
