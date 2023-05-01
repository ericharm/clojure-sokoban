(ns clojure-sokoban.util)

(defn create-menu-option [name, action, x, y]
  {:name name
   :action action
   :location [x y]})

(defn navigate-list [list, current-index, mod]
  (let [new-index (+ current-index mod)]
    (if (>= new-index (count list))
      0
      (if (< new-index 0)
        (- (count list) 1)
        new-index))))
