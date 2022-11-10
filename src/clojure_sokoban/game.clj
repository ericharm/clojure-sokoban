(ns clojure-sokoban.game
  (:require [lanterna.screen :as s])
  (:require [clojure-sokoban.entities :as entities]))

(defn kill [scr]
  (and (s/clear scr) (s/stop scr)))

(defn draw [{:keys [scr level]}]
  (s/clear scr)
  (entities/display {:scr scr :level level})
  ; (s/move-cursor scr (:x hero) (:y hero))
  (s/redraw scr))

(defn location-from [[x y] key-press]
  (case key-press
    :left  [(- x 1) y]
    :right [(+ x 1) y]
    :up    [x (- y 1)]
    :down  [x (+ y 1)]))

(defn update-game [{:keys [scr level]} key-press]
  (let [loc (entities/hero-location level)]
  {:scr scr
    ; :hero {:x 3 :y 1}
    :level (entities/push-entity loc (location-from loc key-press) level)}))

(defn run [state]
  (draw state)
  (let [scr (:scr state)
        key-press (s/get-key-blocking scr)]
    (case key-press
      \q (kill scr)
      (run (update-game state key-press)))))
