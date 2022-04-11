(ns clojure-sokoban.level
  (:require [lanterna.screen :as s]))

(defn from-file [file-name]
  (mapv vec (map butlast (re-seq #"[^\n]+\n" (slurp file-name)))))

(defn row [line y]
  (map-indexed (fn [x ch] {:ch ch :x x :y y}) line))

(defn display [{:keys [scr lines]}]
  (doseq [line (map-indexed (fn [y line] (row line y)) lines)]
    (doseq [tile line]
      (s/put-string scr (:x tile) (:y tile) (str (:ch tile))))))
; (s/put-string scr (:x hero) (:y hero) "@"))

(defn char-at [{:keys [x y]} level]
  (nth (nth level y) x))

(defn next-location [by from]
  {:x (+ (:x from) (:x by)) :y (+ (:y from) (:y by))})

(defn move-boulder [by from level]
  (let [new-location (next-location by from)
        ch (char-at new-location level)]
    (case ch
      \# false
      \0 false
      true)))
; maybe we return a new state
; and compare it to the old state
; OR we actually keep every state in a list
; and we can compare the current state to the last one

(defn move [by from level]
  (let [new-location (next-location by from)]
    (case (char-at new-location level)
      \# from
      \0 (if (move-boulder by new-location level) new-location from)
      new-location)))

(defn location-from [{:keys [location level]} key-press]
  (case key-press
    :left  (move {:x -1 :y 0} location level)
    :right (move {:x 1 :y 0} location level)
    :up    (move {:x 0 :y -1} location level)
    :down  (move {:x 0 :y 1} location level)
    location))


(defn replace-char-in-line [with-char at line]
  (map-indexed (fn [x orig-ch] (if (= x at) with-char orig-ch)) line))

(defn replace-char-in-level [at with level]
  (map-indexed
    (fn [y line] (if (= y (:y at))
                   (replace-char-in-line with (:x at) line)
                   line)) level))
