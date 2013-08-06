;; action_logout

(ns blogjure.mvc.action_logout
  (:use blogjure.mvc.mvc_util)
  (:use blogjure.mvc.compojure_util)
  )

(defn perform-logout
  "Logout"
  [compojure-request]
  (do
    (unset-logged-in (get-http-session compojure-request))
    (add-notice-flash-message  "You are now logged out." (get-http-request compojure-request))
    )
  )

