(ns hello-clojure.macros.begin-macro)


(defmacro backwards
  [form]
  (reverse form))

(backwards (" backwards" " am" "I" str))
