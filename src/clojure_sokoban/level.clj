(ns clojure-sokoban.level
  (:require [lanterna.terminal :as t]))

; sequence<string>
(defn read-lines [file-name]
  (re-seq #"[^\n]+\n"
  (slurp file-name)))

; sequence<sequence<character>>
(defn from-file [file-name]
  (map butlast (read-lines file-name)))

; sequence<tile>
(defn to-row [row line]
  (map-indexed
    (fn [column character] {:x column :y row :ch character}) line))

(defn to-level [file-name]
  (flatten
    (map-indexed (fn [row line] (to-row row line)) (from-file file-name))))

(defn print [term file-name]
  (doseq [tile (to-level file-name)]
    (t/put-character term (:ch tile) (:x tile) (:y tile)))
  (t/get-key-blocking term))
