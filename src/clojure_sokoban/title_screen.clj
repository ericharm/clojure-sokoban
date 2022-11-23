(ns clojure-sokoban.title-screen
  (:require [lanterna.screen :as s])
  (:require [clojure-sokoban.util :as util]))

(defn draw [scr]
  (s/clear scr)
  (s/put-string scr 5 5 "Sokoban" {:fg :blue})
  (s/redraw scr))

(defn run [{:keys [screen]}]
  ; The input/update/draw loop
  (draw screen)
  ; (draw screen history)
  (let [key-press (s/get-key-blocking screen)]
    (case key-press
      \q (util/kill screen)
      :enter (util/kill screen)
      (run screen ))))
