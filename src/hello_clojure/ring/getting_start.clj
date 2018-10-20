(ns hello-clojure.ring.getting-start
  (:require [aleph.http :as http]
            [ring.middleware.keyword-params :refer :all]
            [ring.middleware.params :refer :all])
  (:import (java.io Closeable)))


;; ring其实就是把http的请求解析成了map，然后经过我们自己的处理以后返回一个ring需要的map就可以了，ring就是这么简单。


;; 对于所有的请求都是直接返回了hello
(defn handler [req]
  (prn req)
  {:status 200
   :headers {"content-type" "text/plain"}
   :body "hello vinurs!"})

;; 基于上面的简单的逻辑，我们可以构造出更多复杂的东西


;; ring主要有四个概念
;; handler [request] 可以是同步，可以是异步
;; requests
;; response
;; middleware

;; 同时定义同步跟异步的handler
(defn what-is-my-ip
  ([request]
   (prn request)
   {:status 200
    :headers {"Content-Type" "text/plain"}
    :body (:remote-addr request)})
  ([request respond raise]
   (respond (what-is-my-ip request))))


(defn content-type-response [response content-type]
  (assoc-in response [:headers "Content-Type"] content-type))

(defn wrap-content-type [handler content-type]
  (fn
    ([request]
     (-> (handler request) (content-type-response content-type)))
    ([request respond raise]
     (handler request #(respond (content-type-response % content-type)) raise))))


;; 最外面的最先执行，最里面的最先返回
(def app
  (-> handler
      (wrap-content-type "text/html")
      (wrap-keyword-params)
      ;; 首先解析ring里面的参数
      (wrap-params)))

;; 启动http服务器
;; (def server (http/start-server app {:port 8888}))
;; (def server (http/start-server handler {:port 8888}))
;; 关闭http服务器
;; (.close server)
