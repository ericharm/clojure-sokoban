(ns clojure-sokoban.level
  (:require [clojure.string :as str])
  (:require [clojure-sokoban.entity :as entity]))

(defn entities-from-line [line y]
  (map-indexed (fn [x char] (entity/create x y char :wall :white)) line))

(defn entities-from-file [file]
  (let [lines (str/split-lines (slurp file))]
    (vec (mapcat entities-from-line lines (range)))))


