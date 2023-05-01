(ns clojure-sokoban.game
  (:require [clojure-sokoban.level :as level]))

(defn draw [game screen]
  (let [level (:level game)
        player (:player level)
        entities (:entities level)]
    (doseq [entity entities]
      ((:draw entity) entity screen))
    ((:draw player) player screen)))

(defn key-pressed [game key]
  (let [level (:level game)]
    (condp = key
      \q (System/exit 0)
      :left (assoc game :level (level/move-player level :left))
      :right (assoc game :level (level/move-player level :right))
      :up (assoc game :level (level/move-player level :up))
      :down (assoc game :level (level/move-player level :down))
      game)))

(defn create []
  {:level (level/create)
   :draw draw
   :key-pressed key-pressed})
