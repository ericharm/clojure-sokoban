(ns clojure-sokoban.level
  (:require [lanterna.terminal :as t]))

(defn read-lines [file-name]
  (vec (map vec (butlast (re-seq #"[^\n]+\n" (slurp file-name))))))

(defn row [line y]
  (map-indexed (fn [x ch] {:ch ch :x x :y y}) line))

(defn display [{:keys [term lines hero]}]
  (doseq [line (map-indexed (fn [y line] (row line y)) lines)]
    (doseq [tile line]
      (t/put-character term (:ch tile) (:x tile) (:y tile))))
  (t/put-character term \@ (:x hero) (:y hero)))

