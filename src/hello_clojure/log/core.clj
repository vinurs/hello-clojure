(ns hello-clojure.log.core
  (:require [taoensso.timbre :as timbre
             :refer [log  trace  debug  info  warn  error  fatal  report
                     logf tracef debugf infof warnf errorf fatalf reportf
                     spy get-env]]
            [taoensso.timbre.appenders.core :as appenders]
            [clojure.java.io :as io])
  )



;; 输出到文件
(timbre/merge-config!
 {:appenders {:spit                     ;自带的appender，可以输出到文件
              (appenders/spit-appender {:fname "/Users/vinurs/my-file.log"})}})

;; ;; 打印日志到文件的时候去掉颜色
;; (timbre/merge-config!
;;  {:output-fn (partial timbre/default-output-fn {:stacktrace-fonts {}})})

;; 默认timbre是打印日志到终端，不会到文件
;; 默认的日志级别是debug，因此只有大于debug的才会被输出

;; timbre的配置示例
timbre/example-config

;; set-config!
;; 应该是对所有的东西完全覆盖
;; merge-config!
;; 对配置进行合并
;; 这两个函数的参数都是map

;; (log :info timbre/*config*)
;; timbre/*config*
;; ;; (timbre/set-config! {:level :info})
;; ;; 可以看到前后两次的输出对比，所以我们一般使用merge-config!
;; timbre/*config*

;; ;; 这是默认的配置
;; #_{:level :debug,
;;    :ns-whitelist [],
;;    :ns-blacklist [],
;;    :middleware [],
;;    :timestamp-opts {:pattern "yy-MM-dd HH:mm:ss", :locale :jvm-default, :timezone :utc},
;;    :output-fn #function[taoensso.timbre/default-output-fn],
;;    :appenders {:println {:enabled? true,
;;                          :async? false,
;;                          :min-level nil,
;;                          :rate-limit nil,
;;                          :output-fn :inherit,
;;                          :fn #function[taoensso.timbre.appenders.core/println-appender/fn--3113]}}}

;; (timbre/merge-config! {:level :info})
;; ;; merge以后的值
;; #_{:level :info,
;;    :ns-whitelist [],
;;    :ns-blacklist [],
;;    :middleware [],
;;    :timestamp-opts {:pattern "yy-MM-dd HH:mm:ss", :locale :jvm-default, :timezone :utc},
;;    :output-fn #function[taoensso.timbre/default-output-fn],
;;    :appenders {:println {:enabled? true,
;;                          :async? false,
;;                          :min-level nil,
;;                          :rate-limit nil,
;;                          :output-fn :inherit,
;;                          :fn #function[taoensso.timbre.appenders.core/println-appender/fn--3113]}}}
;; ;; 如果直接用log，那么要加上级别
;; (log :info "hello")

;; (log :info {:a 1 :b 2})

;; ;; 这个不会被输出
;; (trace "This won't print due to insufficient log level")

;; (info "This will print")
;; ;; => nil

;; (spy :info (* 5 4 3 2 1))
;; (defn my-mult [x y] (info "Lexical env:" (get-env)) (* x y))
;; ;; => #'my-mult
;; (my-mult 4 7)
;; ;; => 28


;; (info (Exception. "Oh noes") "arg1" "arg2")

;; ;; (def log-file-name "log.txt")
;; ;; (timbre/set-config! {:appenders {:spit (appenders/spit-appender {:fname log-file-name})}})


;; ;; config,控制timbre的行为的规则很简单，
;; ;; 就是在*config*里面，所以我们只需要弄懂这个配置用起来就得心应手了

;; (def example-config
;;   "An example Timbre v4 config map.

;;   APPENDERS
;;     An appender is a map with keys:
;;       :min-level       ; Level keyword, or nil (=> no minimum level)
;;       :enabled?        ;
;;       :async?          ; Dispatch using agent? Useful for slow appenders (clj only)
;;       :rate-limit      ; [[ncalls-limit window-ms] <...>], or nil
;;       :output-fn       ; Optional override for inherited (fn [data]) -> string
;;       :timestamp-opts  ; Optional override for inherited {:pattern _ :locale _ :timezone _} (clj only)
;;       :ns-whitelist    ; Optional, stacks with active config's whitelist
;;       :ns-blacklist    ; Optional, stacks with active config's blacklist
;;       :fn              ; (fn [data]) -> side effects, with keys described below

;;     An appender's fn takes a single data map with keys:
;;       :config          ; Entire config map (this map, etc.)
;;       :appender-id     ; Id of appender currently dispatching
;;       :appender        ; Entire map of appender currently dispatching
;;       :instant         ; Platform date (java.util.Date or js/Date)
;;       :level           ; Keyword
;;       :error-level?    ; Is level e/o #{:error :fatal}?
;;       :?ns-str         ; String,  or nil
;;       :?file           ; String,  or nil
;;       :?line           ; Integer, or nil ; Waiting on CLJ-865
;;       :?err            ; First-arg platform error, or nil
;;       :vargs           ; Vector of raw args
;;       :output_         ; Forceable - final formatted output string created
;;                        ; by calling (output-fn <this-data-map>)
;;       :msg_            ; Forceable - args as a string
;;       :timestamp_      ; Forceable - string (clj only)
;;       :hostname_       ; Forceable - string (clj only)
;;       :output-fn       ; (fn [data]) -> formatted output string
;;                        ; (see `default-output-fn` for details)
;;       :context         ; *context* value at log time (see `with-context`)

;;       **NB** - any keys not specifically documented here should be
;;       considered private / subject to change without notice.

;;   MIDDLEWARE
;;     Middleware are simple (fn [data]) -> ?data fns (applied left->right) that
;;     transform the data map dispatched to appender fns. If any middleware
;;     returns nil, NO dispatch will occur (i.e. the event will be filtered).

;;   The `example-config` source code contains further settings and details.
;;   See also `set-config!`, `merge-config!`, `set-level!`."

;;   {
;;    ;; 其实就是这里是默认的appender，下面的appenders是额外的appender
;;    ;; 当前的日志级别，低于这个级别的是不会被输出的，级别按照下面的顺序
;;    :level :debug  ; e/o #{:trace :debug :info :warn :error :fatal :report}

;;    ;; Control log filtering by namespaces/patterns. Useful for turning off
;;    ;; logging in noisy libraries, etc.:
;;    ;; 根据命名空间来进行日志过滤，貌似还支持正则表达式
;;    ;; 这些命名空间里面的日志会被输出来
;;    :ns-whitelist  [] #_["my-app.foo-ns"]
;;    ;; 这些命名空间里面的日志是不会被输出来的
;;    :ns-blacklist  [] #_["taoensso.*"]

;;    :middleware [] ; (fns [data]) -> ?data, applied left->right

;;    ;; Clj only:
;;    ;; 仅仅对clojure生效，时间戳选项, map格式
;;    ;; 默认值 {:pattern "yy-MM-dd HH:mm:ss", :locale :jvm-default, :timezone :utc}
;;    ;; 有三种key，
;;    ;; pattern: 显示时间
;;    ;; locale:
;;    ;; timezone: 时区
;;    ;; :timestamp-opts default-timestamp-opts ; {:pattern _ :locale _ :timezone _}

;;    ;; 输出函数，将数据转成字符串输出
;;    ;; :output-fn default-output-fn ; (fn [data]) -> string

;;    ;; 这里面包含了各种各样的appender，每个appender都是key-map
;;    :appenders
;;    {;; The standard println appender:
;;     ;; :println (println-appender {:stream :auto})

;;     ;; appenders里面的内容组成了一个链表，会依次被执行

;;     :an-example-custom-println-appender
;;     ;; Inline appender definition (just a map):
;;     {:enabled?   true
;;      :async?     false
;;      :min-level  nil
;;      :rate-limit [[1 250] [10 5000]] ; 1/250ms, 10/5s
;;      :output-fn  :inherit
;;      :fn ; Appender's (fn [data]) -> side effects
;;      (fn [data]
;;        (let [{:keys [output_]} data
;;              formatted-output-str (force output_)]
;;          (println formatted-output-str)))}}})


;; (log :info timbre/default-timestamp-opts)

;; (timbre/merge-config! {:ns-blacklist ["hello-clojure.log.slf4j"]})
;; (timbre/merge-config! {:ns-blacklist ["*slf4j*"]})

;; (timbre/merge-config! {:ns-blacklist []})

;; timbre/default-timestamp-opts
