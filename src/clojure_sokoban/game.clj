(ns clojure-sokoban.game
  (:require [lanterna.screen :as s])
  (:require [clojure-sokoban.entities :as entities]))

(defn kill [scr]
  (and (s/clear scr) (s/stop scr)))

(defn draw [scr level]
  (s/clear scr)
  (entities/display {:scr scr :level level})
  (s/redraw scr))

(defn location-from [[x y] key-press]
  (case key-press
    :left  [(- x 1) y]
    :right [(+ x 1) y]
    :up    [x (- y 1)]
    :down  [x (+ y 1)]))

(defn update-game [scr level_history key-press]
  (let [ level (last level_history)
        hero (entities/hero-in-level level)
        new-loc (location-from (:location hero) key-press)
        next-state (entities/push-entity hero new-loc level_history)
        ] {
           :scr scr
           :level_history (conj level_history next-state)}))

(defn run [{:keys [scr level_history]}]
  (draw scr (last level_history))
  (let [key-press (s/get-key-blocking scr)]
    (case key-press
      \q (kill scr)
      (run (update-game scr level_history key-press)))))
