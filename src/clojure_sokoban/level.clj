(ns clojure-sokoban.level
  (:require [clojure.string :as str])
  (:require [clojure-sokoban.entity :as entity])
  (:require [clojure-sokoban.entities :as entities]))

(defn entities-from-line [line y]
  (remove nil? (map-indexed (fn [x char] (entity/from-char x y char)) line)))

(defn level-from-file [file]
  (let [lines (str/split-lines (slurp file))]
    (vec (mapcat entities-from-line lines (range)))))

; return a new list of entities
; in which the pushed entity's location is associated with
; the location that is one step in the given direction
(defn push-entity [entities pushed direction]
  (let [pushed-location (:location pushed)
        pushed-new-location (entity/move pushed-location direction)
        pushed-new (assoc pushed :location pushed-new-location)
        entity-at-new-location (entities/entity-at entities pushed-new-location)
        new-entities (entities/except entities entity-at-new-location)]
    (if (nil? entity-at-new-location)
      (conj new-entities pushed-new)
      (conj new-entities (assoc entity-at-new-location :location (entity/move pushed-new-location direction))))))

(defn move-player [level direction]
  (let [player (:player level)
        entities (:entities level)
        new-player (entity/move player direction)
        entity-at-new-location (entities/entity-at entities (:location new-player))]
    (if (nil? entity-at-new-location)
      (assoc level :player new-player)
      (assoc level :player new-player :entities (push-entity entities entity-at-new-location direction)))))

(defn create []
  (let [all-entities (level-from-file "resources/1.lvl")
        player (entities/find-by-type all-entities :player)
        entities (vec (entities/except all-entities player))]
    {:player player
     :entities entities}))
