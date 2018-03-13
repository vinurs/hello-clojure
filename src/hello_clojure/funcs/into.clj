(ns hello-clojure.funcs.into)

(map identity {:sunlight-reaction "Glitter!"})
;; => ([:sunlight-reaction "Glitter!"])

(into {} (map identity {:sunlight-reaction "Glitter!"}))
;; => {:sunlight-reaction "Glitter!"}

(map identity [:garlic :sesame-oil :fried-eggs])
;; => (:garlic :sesame-oil :fried-eggs)

(into [] (map identity [:garlic :sesame-oil :fried-eggs]))
;; => [:garlic :sesame-oil :fried-eggs]
