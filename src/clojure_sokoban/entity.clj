(ns clojure-sokoban.entity
  (:require [lanterna.screen :as s]))

(defn draw [entity screen]
  (let [[x y] (:location entity)
        char (:char entity)]
    (s/put-string screen x y (str char) {:fg (:color entity)})))

(defn create [x y char type color]
  {:id (java.util.UUID/randomUUID)
   :location [x y]
   :char char
   :draw draw
   :type type
   :color color})

(defn move [entity direction]
  (let [[x y] (:location entity)]
    (case direction
      :left (assoc entity :location [(dec x) y])
      :right (assoc entity :location [(inc x) y])
      :up (assoc entity :location [x (dec y)])
      :down (assoc entity :location [x (inc y)]))))

(defn from-char [x y char]
  (case char
    \@ (create x y \@ :player :magenta)
    \# (create x y \# :wall :white)
    \0 (create x y \0 :boulder :cyan)
    \^ (create x y \^ :pit :green)
    \X (create x y \X :exit :yellow)
    nil))

