;; action_newentry

(ns blogjure.mvc.action_newentry
  (:use blogjure.model.hibernate_util)
  (:use blogjure.mvc.mvc_util)
  (:use blogjure.mvc.compojure_util)
  (:use blogjure.util.common_util)
  )

(defn is-valid-blog-entry
  "Returns true if the blog entry is valid, false otherwise"
  [params]
  (if (is-any-null-string-in-map params :title :content) false true)
  )

(defn- persist-new-blog-entry
  "Persists the posted blog entry into database"
  [params]
  (with-hibernate-transaction
    (fn[hibernate-session]
      (doto (new blogjure.model.BlogEntry)
        (.setTitle       (:title   params))
        (.setContent     (:content params))
        (.setWhen_posted (new java.sql.Timestamp (.getTime (new java.util.Date))))
        (.setIs_deleted  false)
        (.save hibernate-session)
        ))))

(defn post-new-blog-entry
  "Post new blog entry"
  [compojure-request]
  (let [params (get-html-form-params compojure-request)
        http-session (get-http-session compojure-request)]
    (if (not (is-logged-in http-session))
      ; then
      (add-error-flash-message  "You are not logged in. You must be logged in to post new entry." (get-http-request compojure-request))
      ; else
      )
    (if (is-valid-blog-entry params)
      ; then
      (do
        (persist-new-blog-entry params)
        (add-success-flash-message  "New blog entry posted succesfully." (get-http-request compojure-request))
        )
      ; else
      (add-error-flash-message  "Invalid / incomplete blog entry." (get-http-request compojure-request))
      )
    )
  )
