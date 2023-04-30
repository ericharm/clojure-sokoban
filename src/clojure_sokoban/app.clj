(ns clojure-sokoban.app
  (:require [clojure-sokoban.menu :as menu])
  (:require [lanterna.screen :as s]))

(defn create-app []
  {:current-state (menu/create)})

(defn run [state screen]
  (s/clear screen)
  ((:draw state) state screen)
  (s/redraw screen)
  (let [key (s/get-key-blocking screen)]
    (run ((:key-pressed state) state key) screen)))

