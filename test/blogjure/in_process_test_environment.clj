;; in_process_test_environment

;; This file serves as an entry point for running the project within the IDE or
;; from an ANT target.
;;
;; [Eclipse users]
;; Assuming you have clojure-dev plugin (http://code.google.com/p/clojure-dev/)
;; simply right-click on this file in the Package Explorer or Navigator and
;; select (Run As --> Clojure REPL).
;; When the clojure-dev plugin starts supporting debugging you might be able to
;; do in-process debugging of Clojure source code using this file.
;;
;; [NetBeans users]
;; I was not able to figure out how to run a Clojure file in isolation
;; on NetBeans using Enclojure plugin. You can invoke the Ant RUN task, which
;; in turn executes this file.

(ns blogjure.in_process_test_environment
  (:use compojure)
  (:use compojure.server.jetty)
  (:use blogjure.mvc.blog_servlet))


(run-server {:port 8088}
  "/*" (servlet all))