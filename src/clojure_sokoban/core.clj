(ns clojure-sokoban.core
  (:require [lanterna.terminal :as t])
  (:require [clojure-sokoban.level :as level])
  (:require [clojure-sokoban.game :as game]))

(def state
  { :term  (t/get-terminal :unix)
    :hero  {:x 3 :y 1}
    :level (level/read-lines "resources/1.lvl") })

(defn -main []
  (t/start (:term state))
  (game/run state))
