(ns clojure-sokoban.core
  (:require [lanterna.screen :as s])
  (:require [clojure-sokoban.game :as game])
  (:require [clojure-sokoban.title-screen :as title]))

(def states [:game])

(def run
  (case (last states)
    :title title/run
    :game game/run))

(def screen (s/get-screen :unix))

(defn -main []
  (s/start screen)
  (run {:screen screen :states states}))
