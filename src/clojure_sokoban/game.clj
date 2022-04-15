(ns clojure-sokoban.game
  (:require [lanterna.screen :as s])
  (:require [clojure-sokoban.entities :as entities]))

(defn kill [scr]
  (and (s/clear scr) (s/stop scr)))

(defn draw [{:keys [scr hero level]}]
  (s/clear scr)
  (entities/display {:scr scr :level level})
  (s/move-cursor scr (:x hero) (:y hero))
  (s/redraw scr))

(defn update-game [{:keys [scr level]}]
  {:scr scr
    :hero {:x 3 :y 1}
    :level level})

(defn run [state]
  (draw state)
  (let [scr (:scr state)
        key-press (s/get-key-blocking scr)]
    (case key-press
      \q (kill scr)
      (run (update-game state)))))
