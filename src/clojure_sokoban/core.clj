(ns clojure-sokoban.core
  (:require [lanterna.screen :as s])
  (:require [clojure-sokoban.level :as level])
  (:require [clojure-sokoban.game :as game]))

(def state
  { :term  (s/get-screen :unix)
    :hero  {:x 3 :y 1}
    :level (level/from-file "resources/1.lvl") })

(defn -main []
  (s/start (:term state))
  (game/run state))
