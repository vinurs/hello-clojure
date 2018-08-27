(defproject hello-clojure "0.1.0"
  :description "学习clojure(script)"
  :url "https://github.com/vinurs/hello-clojure"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :dependencies [[org.clojure/clojure "1.8.0"]

                 ;; 日志
                 [com.taoensso/timbre "4.10.0"]

                 ;; logback框架
                 [ch.qos.logback/logback-classic "1.1.2"]

                 ;; SLF4J binding for Clojure's Timbre logging library
                 [com.fzakaria/slf4j-timbre "0.3.8"]
                 [org.slf4j/log4j-over-slf4j "1.7.14"]
                 [org.slf4j/jul-to-slf4j "1.7.14"]
                 [org.slf4j/jcl-over-slf4j "1.7.14"]

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
