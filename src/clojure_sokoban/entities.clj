(ns clojure-sokoban.entities)

(defn find-by-type [entities type]
  (first (filter #(= (:type %) type) entities)))

(defn except [entities entity]
  (filter #(not= (:id %) (:id entity)) entities))

(defn entity-at [entities location]
  (first (filter #(= (:location %) location) entities)))

; given a list of entities, an entity to remove and an entity to add,
; return the list of entities with the entity removed and the entity added
(defn replace-entity [entities entity-to-remove entity-to-add]
  (conj (except entities entity-to-remove) entity-to-add))
