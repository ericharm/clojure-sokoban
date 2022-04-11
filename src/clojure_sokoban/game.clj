(ns clojure-sokoban.game
  (:require [lanterna.screen :as s])
  (:require [clojure-sokoban.level :as level]))

(defn run [{:keys [term hero level]}]
  (s/clear term)
  (level/display {:term term :lines level :hero hero})
  (s/move-cursor term (:x hero) (:y hero))
  (s/redraw term)
  (run {
        :term term
        :hero (level/location-from
                {:location hero :level level}
                (s/get-key-blocking term))
        :level level }))

