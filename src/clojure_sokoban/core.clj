(ns clojure-sokoban.core
  (:require [lanterna.screen :as s])
  (:require [clojure-sokoban.app :as app]))

(def app (app/create-app))

(def screen (s/get-screen :unix))

(defn -main []
  (let [state (:current-state app)]
    (s/start screen)
    (while true
      (app/run state screen))))
