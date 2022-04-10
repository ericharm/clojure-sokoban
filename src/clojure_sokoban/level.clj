(ns clojure-sokoban.level
  (:require [lanterna.terminal :as t]))

(defn read-lines [file-name]
  (map butlast (re-seq #"[^\n]+\n" (slurp file-name))))

(defn to-row [row line]
  (map-indexed
    (fn [column character] {:x column :y row :ch character}) line))

(defn from-file [file-name]
  (flatten
    (map-indexed (fn [row line] (to-row row line))
                 (read-lines file-name))))

(defn display [term file-name]
  (doseq [tile (from-file file-name)]
    (t/put-character term (:ch tile) (:x tile) (:y tile)))
  (t/get-key-blocking term))
