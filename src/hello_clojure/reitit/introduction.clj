(ns hello-clojure.reitit.introduction
  (:require [reitit.core :as r]
            [reitit.ring :as ring]))

;; 定义路由
(def router
  (r/router
   [["/api/ping" ::ping]
    ["/api/orders/:id" ::order-by-id]]))

;; 测试一下路由
(r/match-by-path router "/api/ipa")
;; nil

(r/match-by-path router "/api/ping")
;; => #reitit.core.Match{:template "/api/ping", :data {:name :hello-clojure.reitit.introduction/ping}, :result nil, :path-params {}, :path "/api/ping"}

(r/match-by-path router "/api/orders/1")
;; => #reitit.core.Match{:template "/api/orders/:id", :data {:name :hello-clojure.reitit.introduction/order-by-id}, :result nil, :path-params {:id "1"}, :path "/api/orders/1"}


;; 反向路由
(r/match-by-name router ::ipa)

(r/match-by-name router ::ping)
;; => #reitit.core.Match{:template "/api/ping", :data {:name :hello-clojure.reitit.introduction/ping}, :result nil, :path-params {}, :path "/api/ping"}

(r/match-by-name router ::order-by-id)
;; => #reitit.core.PartialMatch{:template "/api/orders/:id", :data {:name :hello-clojure.reitit.introduction/order-by-id}, :result nil, :path-params nil, :required #{:id}}

(r/partial-match? (r/match-by-name router ::order-by-id))
;; true

(r/match-by-name router ::order-by-id {:id 2})
;; => #reitit.core.Match{:template "/api/orders/:id", :data {:name :hello-clojure.reitit.introduction/order-by-id}, :result nil, :path-params {:id "2"}, :path "/api/orders/2"}
