(ns clojure-sokoban.level
  (:require [clojure.string :as str])
  (:require [clojure-sokoban.entity :as entity])
  (:require [clojure-sokoban.entities :as entities]))

(defn entities-from-line [line y]
  (remove nil? (map-indexed (fn [x char] (entity/from-char x y char)) line)))

(defn level-from-file [file]
  (let [lines (str/split-lines (slurp file))]
    (vec (mapcat entities-from-line lines (range)))))

(defn move-player [level direction]
  (let [player (:player level)
        old-entities (:entities level)
        new-player (entity/move player direction)
        entity-at-new-location (entities/entity-at old-entities (:location new-player))]
    (if (nil? entity-at-new-location)
      (assoc level :player new-player)
      (let [new-entities (entity/push entity-at-new-location level direction)
            pushed-player (if (= old-entities new-entities) player new-player)]
        (assoc level :player pushed-player :entities new-entities)))))

(defn create []
  (let [all-entities (level-from-file "resources/1.lvl")
        player (entities/find-by-type all-entities :player)
        entities (vec (entities/except all-entities player))]
    {:player player
     :entities entities}))
