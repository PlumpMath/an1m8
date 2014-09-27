(ns frontend.core
  "I do stuff"
	(:require [frontend.dom :as d]
            [frontend.anim :as a]
            [frontend.svg :as s]))

;
;
; Junta Power!
;
;


;;;;;;;;;;
;
; tests

;(a/test-core-async)

(defn ^:export test_svg [id]
  (let [el (d/by-id id)
        svg (.getSVGDocument el)]
    (if svg
      (s/fix-viewBox! svg)
      (js/alert "Error while loading svg"))))


(defn ^:export test_scaling [id]
	(let [el (d/by-id id)]
		(d/scale-el! el 0.5)))


;;;;;;;;;;;
;
; main

(d/on-load (fn[]
; does not work in ie :)
;               (enable-console-print!)
;               (println "Junta Power!")
             ))


