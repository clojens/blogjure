;; run_tests

(ns blogjure.run_tests
  (:use clojure.contrib.test-is)
  (:require blogjure.mvc.test_serve_static_file)
  )

(run-tests
  'blogjure.mvc.test_serve_static_file
  )
