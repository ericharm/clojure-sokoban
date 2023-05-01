(ns clojure-sokoban.game
  (:require [clojure-sokoban.level :as level])
  (:require [clojure-sokoban.player :as player]))

(defn draw [game screen]
  (let [player (:player game)
        entities (:entities game)]
    ((:draw player) player screen)
    (doseq [entity entities]
      ((:draw entity) entity screen))))

(defn key-pressed [menu key]
  (case key
    \q (System/exit 0)
    menu))

(defn create []
  {:player (player/create 3 3)
   :entities (level/entities-from-file "resources/1.lvl")
   :draw draw
   :key-pressed key-pressed})
