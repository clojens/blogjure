;; compojure_util

(ns blogjure.mvc.compojure_util)

(defn get-http-request
  "Extracts and returns HttpServletRequest object from Compojure request object"
  [compojure-request]
  (:servlet-request compojure-request)
  )

(defn get-http-session
  "Extracts and returns HttpSession object from a Compojure request object"
  [compojure-request]
  (let [request (get-http-request compojure-request)]
    (if (= request nil)
      nil
      (.getSession request)
      )
    )
  )

(defn get-context-path
  "Extracts and returns servlet context path from compojure-request"
  [compojure-request]
  (. (:servlet-request compojure-request) getContextPath)
  )

(defn get-query-params
  "Extracts and returns query params"
  [compojure-request]
  (:query-params compojure-request)
  )

(defn get-html-form-params
  "Extracts and returns the form (POST) params"
  [compojure-request]
  (:form-params compojure-request)
  )
