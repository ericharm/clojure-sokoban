(ns clojure-sokoban.level
  (:require [clojure.string :as str])
  (:require [clojure-sokoban.entity :as entity])
  (:require [clojure-sokoban.entities :as entities]))

(defn entities-from-line [line y]
  (remove nil? (map-indexed (fn [x char] (entity/from-char x y char)) line)))

(defn level-from-file [file]
  (let [lines (str/split-lines (slurp file))]
    (vec (mapcat entities-from-line lines (range)))))

(defn push-entity [level entity direction]
    (assoc level :entities (entities/except (:entities level) entity)))

(defn move-player [level direction]
  (let [player (:player level)
        new-player (entity/move player direction)
        new-location (:location new-player)
        entity-at-new-location (entities/entity-at level new-location)
        entities (entities/except (:entities level) entity-at-new-location)]
    (if (nil? entity-at-new-location)
      (assoc level :player new-player)
      (assoc level :player new-player :entities entities))))

(defn create []
  (let [all-entities (level-from-file "resources/1.lvl")
        player (entities/find-by-type all-entities :player)
        entities (vec (entities/except all-entities player))]
    {:player player
     :entities entities}))
