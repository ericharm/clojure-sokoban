(ns clojure-sokoban.level
  (:require [lanterna.terminal :as t])
  (:require [clojure-sokoban.point :as point]))

(defn read-lines [file-name]
  (map butlast (re-seq #"[^\n]+\n" (slurp file-name))))

(defn row [line y]
  (map-indexed
    (fn [x ch] {:point (point/at-location x y) :ch ch}) line))

(defn read-level [file-name]
  (flatten
    (map-indexed (fn [y line] (row line y))
                 (read-lines file-name))))

(defn display [term file-name]
  (doseq [tile (read-level file-name)]
    (t/put-character term (:ch tile) (:x (:point tile)) (:y (:point tile)))))


;; movement
;; a level is an array or rows, each which is made of up a tile
;; a tile can be empty or contain an entity
;; some entities can move to another tile
