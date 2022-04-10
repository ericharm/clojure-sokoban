(ns clojure-sokoban.game
  (:require [lanterna.terminal :as t])
  (:require [clojure-sokoban.point :as point]))

(defn handle-input [term]
  (t/get-key-blocking term))

(defn location-from [location key-press]
  (case key-press
    :left  (point/at-location (- (:x location) 1) (:y location))
    :right (point/at-location (+ (:x location) 1) (:y location))
    :up    (point/at-location (:x location) (- (:y location) 1))
    :down  (point/at-location (:x location) (+ (:y location) 1))))

(defn run [term location]
  (t/move-cursor term (:x location) (:y location))
  (run term (location-from location (handle-input term))))
