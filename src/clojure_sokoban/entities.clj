(ns clojure-sokoban.entities
  (:require [lanterna.screen :as s]))

(declare push-entity)

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

(defn handle-hero-collision [level_history level other-entity [x y]] 
  ; (case key-press
      ;   :left  [(- x 1) y]
      ;   :right [(+ x 1) y]
      ;   :up    [x (- y 1)]
      ;   :down  [x (+ y 1)]) 
  (if (= (:type other-entity) :boulder)
    (let [
          old-x (first (:location other-entity))
          old-y (second (:location other-entity))
          new-bould-loc [(+ x old-x) (+ y old-y)]
          new-history (conj level_history level)
          ]
      (push-entity other-entity new-bould-loc new-history))
    (last level_history)
    ))

(defn handle-collisions [colliding-entities level_history level from]
  (let [
        sorted-entities (sort-by #(not= (:type %) :hero) colliding-entities)
        ]
  (if
      (= (:type (first sorted-entities)) :hero)
      (let [
            hero (first sorted-entities)
            location (:location hero)
            hero-direction [
                            (- (first location) (first from))
                            (- (second location) (second from))]
            ]
  (handle-hero-collision level_history level (second sorted-entities) hero-direction))
  level
  )))
 

(defn detect-collisions [level_history level location from]
  (let [colliding-entities (filter #(= (:location %) location) level)]
  (case (count colliding-entities)
        1  level
        2  (handle-collisions colliding-entities level_history level from)
        3  (last level_history)
        )))

(defn push-entity [entity to level_history]
  ; Returns a new `level` with `entity` at :location `to`
  (let [
    from (:location entity) 
    level (last level_history) next-level (map
    (fn [current_entity]
      (if (eq-entity  entity current_entity)
        (assoc current_entity :location to)
        current_entity))
    level)]
  (detect-collisions level_history next-level to from
  )))

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
