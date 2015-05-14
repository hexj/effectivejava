(ns app.test.find_test
  (:use [app.core])
  (:use [app.test.helper])
  (:use [app.find])
  (:use [app.model.protocols])
  (:use [app.javaparser.parsing])
  (:use [app.javaparser.navigation])
  (:use [clojure.test])
  (:use [conjure.core])
  (:require [instaparse.core :as insta]))

; ============================================
; find-methods-by-signature
; ============================================

(def javaparser2 "test-resources/sample-codebases/javaparser")
(def javaparser-cus (cus javaparser2))

(deftest find-methods-by-signature-on-equals
  (let [res (find-methods-by-signature javaparser-cus "equals" [(make-declared-type-ref "java.lang.Object")])]
    (is (= 2 (count res)))))