;; action_newcomment

(ns blogjure.mvc.action_newcomment
  (:use blogjure.model.hibernate_util)
  (:use blogjure.mvc.mvc_util)
  (:use blogjure.mvc.compojure_util)
  (:use blogjure.util.common_util)
  )

(defn is-valid-user-comment
  "Returns true if the user comment is valid, false otherwise"
  [params]
  (if (is-any-null-string-in-map params :name :email :content) false true))

(defn- persist-new-user-comment
  "Persists the posted user comment into database"
  [params
   entry-id]
  (with-hibernate-transaction (fn[hibernate-session]
    (let [blog-entry (.findById (new blogjure.model.BlogEntry) hibernate-session (long entry-id))]
      (.add (.comments blog-entry)
        (doto (new blogjure.model.UserComment)
          (.setBlog_entry  blog-entry)
          (.setContent     (:content params))
          (.setWhen_posted (new java.sql.Timestamp (.getTime (new java.util.Date))))
          (.setIs_deleted  false)
          (.setName        (:name  params))
          (.setEmail       (:email params))
          (.setUrl         (:url   params))))
      (.save blog-entry hibernate-session)))))

(defn post-new-user-comment
  "Post new user comment"
  [compojure-request entry-id]
  (let [params (get-html-form-params compojure-request)
        http-session (get-http-session compojure-request)]
    (if (is-valid-user-comment params)
      ; then
      (do
        (persist-new-user-comment params entry-id)
        (add-success-flash-message  "New user comment posted succesfully." (get-http-request compojure-request))
        )
      ; else
      (add-error-flash-message  "Invalid / incomplete user comment." (get-http-request compojure-request)))))
