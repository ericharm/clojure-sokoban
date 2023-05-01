(ns clojure-sokoban.entity
  (:require [lanterna.screen :as s]))

(defn draw [entity screen]
  (let [[x y] (:location entity)
        char (:char entity)]
    (s/put-string screen x y (str char))))

(defn create [x y char type color]
  {:location [x y]
   :char char
   :draw draw
   :type type
   :color color})

; (defn from-char [x y char]
;   (case char
;     \@ {:type :hero :color :magenta}
;     \# {:type :wall :color :white}
;     \0 {:type :boulder :color :cyan}
;     \^ {:type :pit :color :green}
;     \X {:type :exit :color :yellow}
;     nil))
