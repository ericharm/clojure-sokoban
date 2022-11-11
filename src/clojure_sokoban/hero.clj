(ns clojure-sokoban.hero)

(defn handle-boulder-collision [boulder [x y] history state on-collide]
  ; Returns Entity[] after a hero has collided into a boulder
  (let [
        old-x (first (:location boulder))
        old-y (second (:location boulder))
        new-loc [(+ x old-x) (+ y old-y)]
        new-history (conj history state)
        ]
    (on-collide boulder new-loc new-history)))

(defn handle-collision [history state other-entity [x y] on-collide] 
  ; Returns Entity[] after a hero has collided into something
  (case (:type other-entity)
    :boulder (handle-boulder-collision other-entity [x y] history state on-collide)
    :exit []
    (last history)
    ))

(defn first-of-entities [entities]
  ; Returns first entity in an Entity[] with :type `:hero`
  (first (filter #(= (:type %) :hero) entities)))
