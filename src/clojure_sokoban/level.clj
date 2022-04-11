(ns clojure-sokoban.level
  (:require [lanterna.screen :as s]))

(defn read-lines [file-name]
  (vec (map vec (butlast (re-seq #"[^\n]+\n" (slurp file-name))))))

(defn row [line y]
  (map-indexed (fn [x ch] {:ch ch :x x :y y}) line))

(defn display [{:keys [term lines hero]}]
  (doseq [line (map-indexed (fn [y line] (row line y)) lines)]
    (doseq [tile line]
      (s/put-string term (:x tile) (:y tile) (str (:ch tile)))))
  (s/put-string term (:x hero) (:y hero) "@"))

