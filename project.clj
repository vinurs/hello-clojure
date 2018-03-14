(defproject hello-clojure "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :dependencies [[org.clojure/clojure "1.8.0"]

                 ;; 数据库
                 [org.clojure/java.jdbc "0.7.5"]
                 [mysql/mysql-connector-java "5.1.45"]

                 ;; 连接池
                 ;; [hikari-cp "2.2.0"]
                 [hikari-cp "1.8.3"]
                 [conman "0.7.6"]

                 [com.layerware/hugsql "0.4.8"]
                 [mount "0.1.12"]
                 ]

  ;; 主函数入口
  :main ^:skip-aot hello-clojure.main

  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
