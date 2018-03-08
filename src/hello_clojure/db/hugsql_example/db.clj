(ns hello-clojure.db.hugsql-example.db
  (:require [hugsql.core :as hugsql]))

(def db {:dbtype "mysql"
         :dbname "reporting"
         :user "root"
         :password "123456"})

;; (def db
;;   {:subprotocol "mysql"
;;    :subname (str (System/getProperty "java.io.tmpdir")
;;                  "/princess_bride.h2")})
