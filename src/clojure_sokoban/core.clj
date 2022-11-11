(ns clojure-sokoban.core
  (:require [lanterna.screen :as s])
  (:require [clojure-sokoban.entities :as entities])
  (:require [clojure-sokoban.game :as game]))

(def screen (s/get-screen :unix))
(def initial-state (entities/from-file "resources/1.lvl"))

(defn -main []
  (s/start screen)
  (game/run screen (conj [] initial-state)))
