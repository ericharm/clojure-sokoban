(ns clojure-sokoban.core
  (:require [lanterna.screen :as s])
  ; (:require [clojure-sokoban.game :as game])
  ; (:require [clojure-sokoban.title-screen :as title]))
  (:require [clojure-sokoban.app :as app]))

; (def states [:game])

; (def run
;   (case (last states)
;     :title title/run
;     :game game/run))

(def app (app/create-app))

(def screen (s/get-screen :unix))

; create an app
; in a loop, draw the app, and handle key presses, and update the app
(defn -main []
  ; (app/draw-app app screen)
  (s/put-string screen 0 0 "hello")
  (let [key (s/get-key-blocking screen)]
    (app/key-pressed-app app key))
  (app/update-app app))


; (defn run
;   ([screen history]
;    ; The input/update/draw loop
;    (draw screen (last history))
;    ; (draw screen history)
;    (let [key-press (s/get-key-blocking screen)]
;      (case key-press
;        \q (util/kill screen)
;        \u (run screen (undo history))
;        (run screen (conj history (update-game history key-press))))))
;   ([args]
;        (run (:screen args) (conj [] initial-state))))



