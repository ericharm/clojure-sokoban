(ns clojure-sokoban.game
  (:require [lanterna.screen :as s])
  (:require [clojure-sokoban.level :as level]))

(defn location-from [location key-press]
  (case key-press
    :left  {:x (- (:x location) 1) :y (:y location)}
    :right {:x (+ (:x location) 1) :y (:y location)}
    :up    {:x (:x location) :y (- (:y location) 1)}
    :down  {:x (:x location) :y (+ (:y location) 1)}))

(defn run [{:keys [term hero level]}]
  (s/clear term)
  (level/display {:term term :lines level :hero hero})
  (s/move-cursor term (:x hero) (:y hero))
  (s/redraw term)
  (run {
        :term term
        :hero (location-from hero (s/get-key-blocking term))
        :level level }
       ))

