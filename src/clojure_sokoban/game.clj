(ns clojure-sokoban.game
  (:require [lanterna.terminal :as t])
  (:require [clojure-sokoban.level :as level]))

(defn location-from [location key-press]
  (case key-press
    :left  {:x (- (:x location) 1) :y (:y location)}
    :right {:x (+ (:x location) 1) :y (:y location)}
    :up    {:x (:x location) :y (- (:y location) 1)}
    :down  {:x (:x location) :y (+ (:y location) 1)}))

(defn run [{:keys [term hero level]}]
  (level/display {:term term :lines level :hero hero})
  (t/move-cursor term (:x hero) (:y hero))
  (run {
        :term term
        :hero (location-from hero (t/get-key-blocking term))
        :level [] }
       ))

