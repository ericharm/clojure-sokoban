(ns clojure-sokoban.core
  (:require [lanterna.terminal :as t])
  (:require [clojure-sokoban.level :as level])
  (:require [clojure-sokoban.point :as point])
  (:require [clojure-sokoban.game :as game]))

(def term (t/get-terminal :unix))

(defn -main []
  (t/start term)
  ; (game/run term (point/new 10 10)))
  (level/print term "resources/1.lvl"))
