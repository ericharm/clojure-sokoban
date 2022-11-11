(ns clojure-sokoban.util
  (:require [lanterna.screen :as s])
  )

; maybe this is more of a game method than a util?
(defn location-from [[x y] key-press]
  ; Returns a location from a location [x y] given a keypress
  (case key-press
    :left  [(- x 1) y]
    :right [(+ x 1) y]
    :up    [x (- y 1)]
    :down  [x (+ y 1)] [x y]))

(defn centerize [screen [point-x point-y] [content-width content-height]]
  ; Returns a point coordinate relative to the center of the screen
  (let [
        screen-size (s/get-size screen)
        half-content-width (/ content-width 2)
        half-content-height (/ content-height 2)
        top-left-x (- (/ (first screen-size) 2) half-content-width)
        top-left-y (- (/ (second screen-size) 2) half-content-height)
        ] [(+ top-left-x point-x) (+ top-left-y point-y)]))

(defn random-id [] (.toString (java.util.UUID/randomUUID)))

