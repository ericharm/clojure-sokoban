(ns clojure-sokoban.level
  (:require [lanterna.terminal :as t]))

(defn read-lines [file-name]
  (map butlast (re-seq #"[^\n]+\n" (slurp file-name))))

(defn row [line y]
  (map-indexed
    (fn [x character] {:x x :y y :ch character}) line))

(defn read-level [file-name]
  (flatten
    (map-indexed (fn [y line] (row line y))
                 (read-lines file-name))))

(defn display [term file-name]
  (doseq [tile (read-level file-name)]
    (t/put-character term (:ch tile) (:x tile) (:y tile)))
  (t/get-key-blocking term))
