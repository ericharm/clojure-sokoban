(ns clojure-sokoban.core
  (:require [lanterna.screen :as s])
  (:require [clojure-sokoban.entities :as entities])
  (:require [clojure-sokoban.game :as game]))

(def state
  { :scr  (s/get-screen :unix)
    :hero  {:x 3 :y 1}
    :level (entities/from-file "resources/1.lvl") })

(defn -main []
  (s/start (:scr state))
  (game/run state))
