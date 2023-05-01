(ns clojure-sokoban.menu
  (:require [clojure-sokoban.game :as game])
  (:require [clojure-sokoban.util :as util])
  (:require [lanterna.screen :as s]))

(defn draw-options [options screen]
  (doseq [option options]
    (let [name (:name option)
          location (:location option)
          [x y] location]
      (s/put-string screen (+ 2 x) y name))))

(defn draw-cursor [selected-option screen]
  (let [[x y] (:location selected-option)]
    (s/move-cursor screen x y)))

(defn draw [menu, screen]
  (let [options (:options menu)
        selected-option (nth (:options menu) (:selected menu))]
    (draw-options options screen)
    (draw-cursor selected-option screen)))

(defn key-pressed [menu, key]
  (let [options (:options menu)
        selected (:selected menu)]
    (case key
      :down (assoc menu :selected (util/navigate-list options selected 1))
      :up (assoc menu :selected (util/navigate-list options selected -1))
      :enter (eval (:action (nth options selected)))
      menu)))

(def menu-options
  (list (util/create-menu-option "Play" (game/create) 0 0)
        (util/create-menu-option "Quit" '(System/exit 0) 0 1)))

(defn create []
  {:options menu-options
   :selected 0
   :draw draw
   :key-pressed key-pressed})
