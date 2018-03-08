(ns hello-clojure.funcs.partial)
(def only-strings
  (partial filter string? ))
;;= #'user/ only- strings
(only-strings ["a" 5 "b" 6])

;;= ("a" "b") 偏 函数 应用 在 很多 情况下
