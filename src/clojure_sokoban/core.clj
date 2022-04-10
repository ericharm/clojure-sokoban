(ns clojure-sokoban.core
  (:require [lanterna.terminal :as t]))

(def term (t/get-terminal :swing))

(defn create-point [x y]
  {:x x :y y})

(defn handle-input []
  (t/get-key-blocking term))

(defn next-location [location input]
  (case input
    :left  (create-point (- (:x location) 1) (:y location))
    :right (create-point (+ (:x location) 1) (:y location))
    :up    (create-point (:x location) (- (:y location) 1))
    :down  (create-point (:x location) (+ (:y location) 1))))

(defn game-update [location]
  (t/move-cursor term (:x location) (:y location))
  (game-update (next-location location (handle-input))))

(defn -main []
  (let [current-location (create-point 10 10)]
    (t/start term)
    (game-update current-location)))
