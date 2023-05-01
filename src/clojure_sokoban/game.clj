(ns clojure-sokoban.game
  (:require [clojure-sokoban.level :as level]))

(defn draw [game screen]
  (let [level (:level game)
        player (:player level)
        entities (:entities level)]
    (doseq [entity entities]
      ((:draw entity) entity screen))
    ((:draw player) player screen)))

(defn key-pressed [menu key]
  (case key
    \q (System/exit 0)
    menu))

(defn create []
  {:level (level/create)
   :draw draw
   :key-pressed key-pressed})
