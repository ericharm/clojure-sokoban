(ns clojure-sokoban.game
  (:require [lanterna.terminal :as t])
  (:require [clojure-sokoban.level :as level])
  (:require [clojure-sokoban.point :as point]))

(defn location-from [location key-press]
  (case key-press
    :left  (point/at-location (- (:x location) 1) (:y location))
    :right (point/at-location (+ (:x location) 1) (:y location))
    :up    (point/at-location (:x location) (- (:y location) 1))
    :down  (point/at-location (:x location) (+ (:y location) 1))))
    ; (point/at-location (:x location) (:y location))))

(defn run [term location]
  (level/display term "resources/1.lvl")
  (t/move-cursor term (:x location) (:y location))
  (run term (location-from location (t/get-key-blocking term))))

