(ns clojure-sokoban.entity
  (:require [lanterna.screen :as s]))

(defn draw [entity screen]
  (let [[x y] (:location entity)
        char (:char entity)]
    (s/put-string screen x y (str char) {:fg (:color entity)})))

(defn create [x y char type color]
  {:location [x y]
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

(defn create-player [x y]
  (create x y \@ :player :magenta))

(defn create-wall [x y]
  (create x y \# :wall :white))

(defn create-boulder [x y]
  (create x y \0 :boulder :cyan))

(defn create-pit [x y]
  (create x y \^ :pit :green))

(defn create-exit [x y]
  (create x y \X :exit :yellow))

(defn from-char [x y char]
  (case char
    \@ (create-player x y)
    \# (create-wall x y)
    \0 (create-boulder x y)
    \^ (create-pit x y)
    \X (create-exit x y)
    nil))
