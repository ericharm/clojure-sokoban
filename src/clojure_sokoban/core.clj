(ns clojure-sokoban.core
  (:require [lanterna.screen :as s])
  (:require [clojure-sokoban.app :as app]))

(def screen (s/get-screen :unix))
(def app (app/create-app))

(defn -main []
  (let [state (:current-state app)]
    (s/start screen)
    (while true
      (app/run state screen))))
