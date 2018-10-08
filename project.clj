(defproject hello-clojure "0.1.0"
  :description "学习clojure(script)"
  :url "https://github.com/vinurs/hello-clojure"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :dependencies [[org.clojure/clojure "1.9.0"]

                 ;; 日志
                 [com.taoensso/timbre "4.10.0"]

                 ;; logback框架
                 [ch.qos.logback/logback-classic "1.1.2"]

                 ;; SLF4J binding for Clojure's Timbre logging library
                 [com.fzakaria/slf4j-timbre "0.3.8"]
                 [org.slf4j/log4j-over-slf4j "1.7.14"]
                 [org.slf4j/jul-to-slf4j "1.7.14"]
                 [org.slf4j/jcl-over-slf4j "1.7.14"]

                 ;; 数据库连接
                 [org.clojure/java.jdbc "0.7.7"]
                 ;; mysql驱动
                 ;; [mysql/mysql-connector-java "5.1.45"]
                 [mysql/mysql-connector-java "8.0.12"]
                 ;; postgresql驱动
                 [org.postgresql/postgresql "42.2.4"]
                 ;; ms sqlserver驱动
                 [com.microsoft.sqlserver/mssql-jdbc "7.0.0.jre8"]

                 ;; 直接根据sql文件来生成函数，这个其实可以不用包含了，conman里面应该已经有了
                 [com.layerware/hugsql "0.4.9" :exclusions [org.clojure/java.jdbc]]

                 ;; 数据库连接池
                 ;; clojure封装的HikariCP JDBC连接池
                 [hikari-cp "2.6.0"]
                 ;; 在hikari-cp上面更进一步的封装，直接调用
                 [conman "0.7.6"]

                 ;; timer
                 [tools-timer "1.0.4"]


                 [mount "0.1.12"]]

  ;; 主函数入口
  :main ^:skip-aot hello-clojure.main

  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
