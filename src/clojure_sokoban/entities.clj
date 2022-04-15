(ns clojure-sokoban.entities
  (:require [lanterna.screen :as s]))

(defn to-entity [ch location]
  (let [t (case ch \@ :hero \# :wall \0 :boulder \^ :pit \X :exit nil)]
    (if t {:ch ch :type t :location location} nil)))

(defn to-entities [line y]
  (filter some? (map-indexed #(to-entity %2 [%1 y]) line)))

(defn from-file [file-name]
  (let [lines (map butlast (re-seq #"[^\n]+\n" (slurp file-name)))]
    (flatten (map-indexed #(to-entities %2 %1) lines))))

(defn display [{:keys [scr level]}]
  (doseq [entity level]
    (let [[x y] (:location entity) ch (:ch entity)]
      (s/put-string scr x y (str ch)))))
