(ns clojure-sokoban.core
  (:require [lanterna.terminal :as t]))

(def term (t/get-terminal :swing))

(defrecord Point [x y])

(defn handle-input []
  (t/get-key-blocking term))

(defn next-location [location input]
  (case input
    :left (Point. (- (:x location) 1) (:y location))
    :right (Point. (+ (:x location) 1) (:y location))
    :up (Point. (:x location) (- (:y location) 1))
    :down (Point. (:x location) (+ (:y location) 1))))

(defn game-update [location]
  (t/move-cursor term (:x location) (:y location))
    (game-update (next-location location (handle-input))))

(defn -main []
  (let [current-location (Point. 10 10)]
  (t/start term)
  (game-update current-location)))
