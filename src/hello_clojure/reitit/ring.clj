(ns hello-clojure.reitit.ring
  (:require [reitit.core :as r]
            [reitit.ring :as ring]))


;; 配合ring进行路由

(defn handler [_]
  {:status 200, :body "ok"})

(defn wrap [handler id]
  (fn [request]
    (update (handler request) :wrap (fnil conj '()) id)))


;; 构造ring handler
(def app
  (ring/ring-handler
   (ring/router
    ["/api" {:middleware [[wrap :api]]}
     ["/ping" {:get handler
               :name ::ping}]
     ["/admin" {:middleware [[wrap :admin]]}
      ["/users" {:get handler
                 :post handler}]]])))

(app {:request-method :get, :uri "/api/admin/users"})
;; {:status 200, :body "ok", :wrap (:api :admin}

(app {:request-method :put, :uri "/api/admin/users"})
;; nil

(-> app (ring/get-router) (r/match-by-name ::ping))
;; => #reitit.core.Match{:template "/api/ping", :data {:middleware [[#function[hello-clojure.reitit.ring/wrap] :api]], :get {:handler #function[hello-clojure.reitit.ring/handler]}, :name :hello-clojure.reitit.ring/ping}, :result #reitit.ring.Methods{:get #reitit.ring.Endpoint{:data {:middleware [[#function[hello-clojure.reitit.ring/wrap] :api]], :name :hello-clojure.reitit.ring/ping, :handler #function[hello-clojure.reitit.ring/handler]}, :handler #function[hello-clojure.reitit.ring/wrap/fn--14646], :path "/api/ping", :method :get, :middleware [#reitit.middleware.Middleware{:name nil, :wrap #function[reitit.middleware/eval14184/fn--14186/fn--14191], :spec nil}]}, :head nil, :post nil, :put nil, :delete nil, :connect nil, :options #reitit.ring.Endpoint{:data {:middleware [[#function[hello-clojure.reitit.ring/wrap] :api]], :get {:handler #function[hello-clojure.reitit.ring/handler]}, :name :hello-clojure.reitit.ring/ping, :handler #function[reitit.ring/default-options-handler], :no-doc true}, :handler #function[hello-clojure.reitit.ring/wrap/fn--14646], :path "/api/ping", :method :options, :middleware [#reitit.middleware.Middleware{:name nil, :wrap #function[reitit.middleware/eval14184/fn--14186/fn--14191], :spec nil}]}, :trace nil, :patch nil}, :path-params nil, :path "/api/ping"}
