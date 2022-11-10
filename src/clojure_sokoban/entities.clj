(ns clojure-sokoban.entities
  (:require [lanterna.screen :as s]))

(defn to-entity [ch location]
  (let [t (case ch \@ :hero \# :wall \0 :boulder \^ :pit \X :exit nil)]
    (if t {:ch ch :type t :location location :id (.toString (java.util.UUID/randomUUID))} nil)))

(defn to-entities [line y]
  (filter some? (map-indexed #(to-entity %2 [%1 y]) line)))

(defn from-file [file-name]
  (let [lines (map butlast (re-seq #"[^\n]+\n" (slurp file-name)))]
    (flatten (map-indexed #(to-entities %2 %1) lines))))

(defn display [{:keys [scr level]}]
  (doseq [entity level]
    (let [[x y] (:location entity) ch (:ch entity)]
      (s/put-string scr x y (str ch)))))

(defn eq-entity [entity1 entity2]
  (= (:id entity1) (:id entity2)))

(defn push-entity [entity to level]
  ; Returns a new level, moving entities at one location
  ; to another location.

  ; we really want to specify entities by an id or memory address
  (map
    (fn [current_entity] (
                  if (eq-entity  entity current_entity)
                  (assoc current_entity :location to)
                  current_entity
                  )
      )
    level))
; (let [ent (first (filter #(= (from) (:location %)) level))
;       new-ent (assoc ent :location to)]
;   (conj level new-ent)))

(defn hero-in-level [level]
  ; Returns an entity
  (first (filter #(= (:type %) :hero) level)))

;;; testing
; (def level (from-file "resources/1.lvl"))

; (defn location-from [[x y] key-press]
;   (case key-press
;     :left  [(- x 1) y]
;     :right [(+ x 1) y]
;     :up [x (- y 1)]
;     :down [x (+ y 1)]))

; (def hero-loc (hero-location level))
; (def new-location (location-from hero-loc :right))
; (push-entity hero-loc new-location level)
