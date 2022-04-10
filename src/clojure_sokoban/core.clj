(ns clojure-sokoban.core
  (:require [lanterna.terminal :as t])
  (:require [clojure-sokoban.point :as point])
  (:require [clojure-sokoban.game :as game]))

(def term (t/get-terminal :unix))

; {
;   :running true
;   :hero {:x 2 :y 2}
;   :level ({:x 0 :y 0 \#} ...)
; }

; (game/run [term hero-position level])


; (defn run [term location]
;   (level/display term "resources/1.lvl")
;   (t/move-cursor term (:x location) (:y location))
;   (run term (location-from location (t/get-key-blocking term))))


(defn -main []
  (t/start term)
  (game/run term (point/at-location 10 10)))
  ; (level/display term "resources/1.lvl"))
