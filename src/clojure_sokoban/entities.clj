(ns clojure-sokoban.entities)

(defn find-by-type [entities type]
  (first (filter #(= (:type %) type) entities)))

(defn except [entities entity]
  (filter #(not= (:id %) (:id entity)) entities))

(defn entity-at [entities location]
  (first (filter #(= (:location %) location) entities)))
