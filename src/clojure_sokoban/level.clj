(ns clojure-sokoban.level
  (:require [clojure.string :as str])
  (:require [clojure-sokoban.entity :as entity]))

(defn entities-from-line [line y]
  (remove nil? (map-indexed (fn [x char] (entity/from-char x y char)) line)))

(defn level-from-file [file]
  (let [lines (str/split-lines (slurp file))]
    (vec (mapcat entities-from-line lines (range)))))

(defn player-from-entities [entities]
  (first (filter #(= (:type %) :player) entities)))

(defn entities-without-player [entities]
  (filter #(not= (:type %) :player) entities))

(defn move-player [level direction]
  (let [player (:player level)]
   (assoc level :player (entity/move player direction))))

(defn create []
  (let [all-entities (level-from-file "resources/1.lvl")]
    {:player (player-from-entities all-entities)
     :entities (vec (entities-without-player all-entities))}))
