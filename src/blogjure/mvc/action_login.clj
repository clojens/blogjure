;; action_login

(ns blogjure.mvc.action_login
  (:use blogjure.mvc.mvc_util)
  (:use blogjure.mvc.compojure_util)
  )

(defn attempt-to-authenticate
  "Attempts to authenticate user"
  [compojure-request]
  ;(str compojure-request)
  (let [params (get-html-form-params compojure-request)]
    (if (and (= (:username params) "Username") (= (:password params) "Password"))
      ; then
      (do
        (add-success-flash-message "Login successful" (get-http-request compojure-request))
        (add-notice-flash-message  "You can post new entries" (get-http-request compojure-request))
        (set-logged-in (get-http-session compojure-request))
        )
      ; else
      (do
        (add-error-flash-message "Login failed" (get-http-request compojure-request))
        (unset-logged-in (get-http-session compojure-request))
        )
      ;(
        ;(unset-logged-in (get-http-session compojure-request))
        ;(if true false false)
        ;)
      )
    )
  )

