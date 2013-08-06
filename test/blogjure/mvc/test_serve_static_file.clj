;; serve_static_file

(ns blogjure.mvc.test_serve_static_file
  (:use blogjure.mvc.serve_static_file)
  (:use clojure.contrib.test-is))

(deftest test-get-filename-extension
  (is (= (get-filename-extension "index.html") "html"))
  (is (= (get-filename-extension ".htaccess" ) "htaccess"))
  (is (= (get-filename-extension "abc."      ) ""))
  (is (= (get-filename-extension "."         ) ""))
  (is (= (get-filename-extension ""          ) "")))

;(deftest test-serve-static-file
  ; TODO (need HTTP-Client test harness for tetsing MVC)
;  )
