(ns app.find ^{:author "Federico Tomassetti"
         :doc "This namespace contains methods to find elements in a collection of compilation units"}
  (:use [app.model.protocols])
  (:use [app.model.javaparser])
  (:use [app.javaparser.navigation])
  (:use [app.operations])
  (:use [app.itemsOnLifecycle])
  (:use [app.utils])
  (:require [instaparse.core :as insta])
  (:import [app.operations Operation]))

(defn type-exact-match? [type-ref1 type-ref2]
  (and 
    (= (nil? type-ref1) (nil? type-ref2))
    (= (array? type-ref1) (array? type-ref2))
    (= (primitive? type-ref1) (primitive? type-ref2))
    (= (typeName type-ref1) (typeName type-ref2))
    (type-exact-match? (baseType type-ref1) (baseType type-ref2))))

(defn param-match-type? [pair]
  (let [[param expected-type] pair
        paramType (.getType param)]
    (type-exact-match? paramType expected-type)))

(defn method-match-exactly-parameters? [param-expected-types method]
  (let [params (.getParameters method)
        pairs (map vector params param-expected-types)]
    (and
      (= (count params) (count param-expected-types))
      (every? param-match-type? pairs))))

(defn find-methods-by-signature
  "Find all the methods in the given CUs which has the given names and have parameter types exactly equals to the one given"
  [cus name param-types]
  (let [ all-types (flatten (map allTypes cus))
         all-methods (flatten (map getMethods all-types))
         methods (filter (fn [m] (= name (.getName m))) all-methods)
         methods' (filter (partial method-match-exactly-parameters? param-types) methods)]
    methods'))
