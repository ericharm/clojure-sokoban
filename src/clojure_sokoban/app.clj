(ns clojure-sokoban.app
  (:require [lanterna.screen :as s]))

; just print the name of the menu's options
; to the screen
; (defn draw-menu [menu, screen]
;   (doseq [menu-options (menu :options)]
;     (s/put-string screen 0 0 menu-options)))


      ; (s/put-string scr x y (str ch) {:fg color}))))

; for each of a menu's menu-units, print the name to the screen
; (defn draw-menu-units [menu, screen]
(defn draw-menu [menu, screen]
  (doseq [menu-unit (menu :menu-units)]
    (s/put-string screen 0 0 (menu-unit :name))))

(defn update-menu [menu]
  menu)

(defn key-pressed-menu [menu, key]
  (case key
    :left (assoc menu :selected (dec (:selected menu)))
    :up (assoc menu :selected (dec (:selected menu)))
    :right (assoc menu :selected (inc (:selected menu)))
    :down (assoc menu :selected (inc (:selected menu)))
    menu))

(defn create-menu-option [name, action]
  {:name name
   :action action})

(def play (create-menu-option "Play" :play))
(def quit (create-menu-option "Quit" :quit))

; a menu has two options: play and quit
(defn create-menu []
  {:options [play quit]
   :selected 0
   :draw draw-menu
   :update update-menu
   :key-pressed key-pressed-menu})

; stage-select screen
; returns an object with that has three functions:
;  - draw, which draws the stage-select screen
;  - update, which updates the stage-select screen
;  - key-pressed, which handles key presses

(defn draw-stage-select [stage-select, screen]
  screen)
; (with-screen screen
    ;   (clear)
    ;   (draw-string "Stage Select" 0 0)
    ;   (draw-string "Press any key to start" 0 1)))

(defn update-stage-select [stage-select]
  nil)

(defn key-pressed-stage-select [stage-select, key]
  key)

(defn create-stage-select []
  {:draw draw-stage-select
   :update update-stage-select
   :key-pressed key-pressed-stage-select})

; game screen
; returns an object with that has three functions:
;  - draw, which draws the game
;  - update, which updates the game
;  - key-pressed, which handles key presses

(defn draw-game [game, screen]
  screen)
; (with-screen screen
    ;   (clear)
    ;   (draw-string "Game" 0 0)
    ;   (draw-string "Press any key to start" 0 1)))

(defn update-game [game]
  nil)

(defn key-pressed-game [game, key]
  key)

(defn create-game []
  {:draw draw-game
   :update update-game
   :key-pressed key-pressed-game})

; victory screen
; returns an object with that has three functions:
;  - draw, which draws the victory screen
;  - update, which updates the victory screen
;  - key-pressed, which handles key presses

(defn draw-victory [victory, screen]
  screen)
; (with-screen screen
    ;   (clear)
    ;   (draw-string "Victory" 0 0)
    ;   (draw-string "Press any key to start" 0 1)))

(defn update-victory [victory]
  nil)

(defn key-pressed-victory [victory, key]
  key)

(defn create-victory []
  {:draw draw-victory
   :update update-victory
   :key-pressed key-pressed-victory})



(def states {:menu :menu
             :stage-select :stage-select
             :game :game
             :victory :victory})


(defn create-app []
  {:current-state (states :menu)
   :menu (create-menu)
   :stage-select (create-stage-select)
   :game (create-game)
   :victory (create-victory)})

(defn draw-app [app screen]
  (case (:current-state app)
    :menu (draw-menu (:menu app) screen)
    :stage-select (draw-stage-select (:stage-select app) screen)
    :game (draw-game (:game app) screen)
    :victory (draw-victory (:victory app) screen)))

(defn update-app [app]
  (case (:current-state app)
    :menu (update-menu (:menu app))
    :stage-select (update-stage-select (:stage-select app))
    :game (update-game (:game app))
    :victory (update-victory (:victory app))))

(defn key-pressed-app [app key]
  (case (:current-state app)
    :menu (key-pressed-menu (:menu app) key)
    :stage-select (key-pressed-stage-select (:stage-select app) key)
    :game (key-pressed-game (:game app) key)
    :victory (key-pressed-victory (:victory app) key)))

