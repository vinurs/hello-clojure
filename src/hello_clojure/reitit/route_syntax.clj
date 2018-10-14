(ns hello-clojure.reitit.route-syntax
  (:require [reitit.core :as r]
            [reitit.ring :as ring]))

;; 路由定义的语法
;; route定义的时候是以一个vector的形式来定义的
;; 最简单的只有一个路由
["/ping"]

;; 两个路由，可以看出一个路由是一个vector
[["/ping"]
 ["/pong"]]


;; 带参数的路由
[["/ping" ::ping]
 ["/pong" {:name ::pong}]]

;; 路径中带参数的路由
[["/users/:user-id"]
 ["/api/:version/ping"]]

;; 所有的匹配的路由
["/public/*path"]

;; 路由可以有多级，嵌套路由
["/api"
 ["/admin" {:middleware [::admin]}
  ["" ::admin]
  ["/db" ::db]]
 ["/ping" ::ping]]

;; 上面的路由等同于下面的
[["/api/admin" {:middleware [::admin], :name ::admin}]
 ["/api/admin/db" {:middleware [::admin], :name ::db}]
 ["/api/ping" {:name ::ping}]]

;; reitit里面的路由仅仅是数据而已，因此我们可以通过程序来构造路由，而不是写好了
;; 上面的方法创造出来的路由仅仅是数据结构而已
;; 需要通过下面的函数来转换
(defn cqrs-routes [actions]
  ["/api" {:interceptors [::api ::db]}
   (for [[type interceptor] actions
         :let [path (str "/" (name interceptor))
               method (condp = type
                        :query :get
                        :command :post)]]
     [path {method {:interceptors [interceptor]}}])])
(cqrs-routes
 [[:query   'get-user]
  [:command 'add-user]
  [:command 'add-order]])
