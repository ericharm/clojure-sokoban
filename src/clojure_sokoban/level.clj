(ns clojure-sokoban.level
  (:require [lanterna.screen :as s]))

(defn from-file [file-name]
  (mapv vec (map butlast (re-seq #"[^\n]+\n" (slurp file-name)))))

(defn row [line y]
  (map-indexed (fn [x ch] {:ch ch :x x :y y}) line))

(defn display [{:keys [term lines hero]}]
  (doseq [line (map-indexed (fn [y line] (row line y)) lines)]
    (doseq [tile line]
      (s/put-string term (:x tile) (:y tile) (str (:ch tile)))))
  (s/put-string term (:x hero) (:y hero) "@"))

(defn char-at [{:keys [x y]} level]
  (nth (nth level y) x))

(defn move [by from level]
  (let [new-location {:x (+ (:x from) (:x by)) :y (+ (:y from) (:y by))}
        ch (char-at new-location level)]
    (if (= ch \#) from new-location)))

(defn location-from [{:keys [location level]} key-press]
  (case key-press
    :left  (move {:x -1 :y 0} location level)
    :right (move {:x 1 :y 0} location level)
    :up    (move {:x 0 :y -1} location level)
    :down  (move {:x 0 :y 1} location level)))

