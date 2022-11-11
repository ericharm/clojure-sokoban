(ns clojure-sokoban.game
  (:require [lanterna.screen :as s])
  (:require [clojure-sokoban.util :as util])
  (:require [clojure-sokoban.entities :as entities])
  (:require [clojure-sokoban.hero :as hero]))

(defn kill [scr]
  (and (s/clear scr) (s/stop scr)))

(defn draw [scr entities]
  (s/clear scr)
  (entities/display scr entities)
  (s/redraw scr))

(defn update-game [history key-press]
  ; Returns an level state, the result of trying to push a hero
  ; in response to a key-press
  (let [entities (last history)
        hero (hero/first-of-entities entities)
        new-loc (util/location-from (:location hero) key-press)
        next-state (entities/push-entity hero new-loc history)
        ] next-state))

(defn undo [history]
  (if (> (count history) 1) (vec (butlast history)) history))

(defn run [screen history]
  ; The input/update/draw loop
  (draw screen (last history))
  ; (draw screen history)
  (let [key-press (s/get-key-blocking screen)]
    (case key-press
      \q (kill screen)
      \u (run screen (undo history))
      (run screen (conj history (update-game history key-press))))))
