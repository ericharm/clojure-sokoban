(ns clojure-sokoban.menu
  (:require [lanterna.screen :as s]))

(defn create-menu-option [name, action, x, y]
  {:name name
   :action action
   :location [x y]})

(defn navigate-list [list, current-index, mod]
  (let [new-index (+ current-index mod)]
    (if (>= new-index (count list))
      0
      (if (< new-index 0)
        (- (count list) 1)
        new-index))))

(defn draw [menu, screen]
  (let [options (:options menu)]
    (doseq [option options]
      (let [name (:name option)
            location (:location option)
            [x y] location]
        (s/put-string screen x y name))))
  (let [selected (:selected menu)
        [x y] (:location (nth (:options menu) selected))]
    (s/move-cursor screen x y)))

(defn key-pressed [menu, key]
  (let [options (:options menu)
        selected (:selected menu)]
    (case key
      :down (assoc menu :selected (navigate-list options selected 1))
      :up (assoc menu :selected (navigate-list options selected -1))
      :enter ((:action (nth options selected)) menu)
      menu)))

(def play (create-menu-option "Play" :play 0 0))
(def quit (create-menu-option "Quit" :quit 0 1))

(defn create []
  {:options [play quit]
   :selected 0
   :draw draw
   :key-pressed key-pressed})


