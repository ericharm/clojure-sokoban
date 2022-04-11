(ns clojure-sokoban.game
  (:require [lanterna.screen :as s])
  (:require [clojure-sokoban.level :as level]))

(defn kill [scr]
  (and (s/clear scr) (s/stop scr)))

(defn draw [{:keys [scr hero level]}]
  (s/clear scr)
  (level/display {:scr scr :lines level :hero hero})
  (s/move-cursor scr (:x hero) (:y hero))
  (s/redraw scr))

(defn update [{:keys [scr hero level]} key-press]
  (let [location (level/location-from {:location hero :level level} key-press)
        from-char (level/char-at (:x hero) (:y hero))]
  {:scr scr
    :hero location
    :level (level/update level from-char location)}))

(defn run [state]
  (draw state)
  (let [scr (:scr state)
        key-press (s/get-key-blocking scr)]
    (case key-press
      \q (kill scr)
      (run (update state key-press)))))
