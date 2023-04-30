(ns clojure-sokoban.game
  (:require [lanterna.screen :as s]))


; just print the word "game" to the screen
(defn draw [game, screen]
  (s/put-string screen 0 0 "game"))

; any key press just exits
(defn key-pressed [menu, key]
  (System/exit 0))

(defn create []
  {:draw draw
   :key-pressed key-pressed})


