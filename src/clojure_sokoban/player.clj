(ns clojure-sokoban.player
  (:require [clojure-sokoban.entity :as entity]))

(defn create [x y]
  (entity/create x y \@ :player :magenta))
