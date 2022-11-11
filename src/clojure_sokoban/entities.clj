(ns clojure-sokoban.entities
  (:require [lanterna.screen :as s])
  (:require [clojure-sokoban.util :as util])
  (:require [clojure-sokoban.hero :as hero]))

(declare push-entity)

(defn default-params-from-char [char]
  ; Returns a map? of default params for an entity based on `char`
  (case char
    \@ {:type :hero :color :magenta}
    \# {:type :wall :color :white}
    \0 {:type :boulder :color :cyan}
    \^ {:type :pit :color :green}
    \X {:type :exit :color :yellow}
    nil))

(defn to-entity [ch location]
  ; Returns an Entity? {:ch :type :location :id}
  (let [params (default-params-from-char ch)]
    (if params (assoc params :ch ch :location location :id (util/random-id))
      nil)))

(defn to-entities [line y]
  ; Returns a list of Entities from a line of text and its vertical position
  (filter some? (map-indexed #(to-entity %2 [%1 y]) line)))

(defn from-file [file-name]
  ; Reads a file and returns a list of Entities
  (let [lines (map butlast (re-seq #"[^\n]+\n" (slurp file-name)))]
    (flatten (map-indexed #(to-entities %2 %1) lines))))

(defn display [scr state]
  ; Draws an entity to the screen
  (doseq [entity state]
    (let [
          level-size [15 11]
          ch (:ch entity)
          color (:color entity)
          [x y] (util/centerize scr (:location entity) level-size)
          ]
      (s/put-string scr x y (str ch) {:fg color}))))

(defn eq-entity [entity1 entity2]
  ; Returns true if entity1 is entity2
  (= (:id entity1) (:id entity2)))

(defn sort-entities-by-hero [entities]
  ; sorts entities so that hero goes first, then boulders, then anything else
  (let [sorted-by-boulder (sort-by #(not= (:type %) :boulder) entities)]
    (sort-by #(not= (:type %) :hero) sorted-by-boulder)))

(defn state-without-entities [state entities]
  ; Returns a new Entity[] with `entities` filtered out
  (let [entity-is-in-list (fn [entity list] (some #(= % entity) list))]
    (filter #(not (entity-is-in-list % entities)) state)))

(defn handle-boulder-collision [[boulder other] history state]
  ; Returns a new Entity[] after considering boulder collisions
  (case (:type other)
    :pit (state-without-entities state [boulder other])
    (last (butlast history))))

(defn handle-hero-collision [[hero other] history state from]
  ; Returns a new Entity[] after considering hero collisions
  (let [location (:location hero)
        x (first location)
        y (second location)
        hero-direction [(- x (first from)) (- y (second from))]]
    (hero/handle-collision history state other hero-direction push-entity)))

(defn handle-collisions [colliding-entities history state from]
  ; Returns a new Entity[] after accounting for colliding-entities
  (let [sorted-entities (sort-entities-by-hero colliding-entities)]
    (case (:type (first sorted-entities))
      :hero (handle-hero-collision sorted-entities history state from)
      :boulder (handle-boulder-collision sorted-entities history state)
      state
      )))

(defn detect-collisions [history entities location from]
  ; Returns a new Entity[] after checking for colliding-entities
  (let [colliding-entities (filter #(= (:location %) location) entities)]
    (case (count colliding-entities)
      1  entities
      2  (handle-collisions colliding-entities history entities from)
      (last history)
      )))

(defn next-state-after-push [state entity target]
  ; Returns a new Entity[] with `entity` at :location `to`
  (map
    (fn [current_entity]
      (if (eq-entity  entity current_entity)
        (assoc current_entity :location target)
        current_entity))
    state))

(defn push-entity [entity to history]
  ; Returns an Entity[] with collisions handled
  (let [
        from (:location entity)
        state (last history)
        next-state (next-state-after-push state entity to)]
    (detect-collisions history next-state to from)))

