;; BlogServlet

(ns blogjure.mvc.blog_servlet
  (:use compojure)
  (:use blogjure.mvc.serve_static_file)
  (:use blogjure.mvc.mvc_util)
  (:use blogjure.mvc.compojure_util)
  (:use blogjure.mvc.action_front)
  (:use blogjure.mvc.action_entry)
  (:use blogjure.mvc.action_newentry)
  (:use blogjure.mvc.action_newcomment)
  (:use blogjure.mvc.action_login)
  (:use blogjure.mvc.action_logout)
  (:gen-class
    :extends javax.servlet.http.HttpServlet
    )
  )

;; <lifted-code>
;; This portion is lifted from the URL below:
;; http://groups.google.com/group/compojure/browse_thread/thread/d14b8b3f03894d52/1ba2c31bf7df1a09?lnk=gst&q=context#1ba2c31bf7df1a09

(declare *context*)

(defn with-context
  [ctx & route-seq]
  (let [handler (apply routes route-seq)
    pattern (re-pattern (str "^" ctx "(/.*)?"))]
    (fn [request]
      (if-let [[_ uri] (re-matches pattern (:uri request))]
        (binding [*context* ctx]
          (handler (assoc request :uri uri)))))))

(defn url [path]
  (str *context* path))

;; </lifted-code>

(defroutes all
  (GET "/"  ;; handy for development mode
    (redirect-to "/blogjure/blog/front"))
  (GET "/favicon.ico"  ;; handy for development mode
    (serve-static-file "/favicon.ico"))
  (with-context "/blogjure"
    (GET "/"
      (redirect-to [(url "/blog/front")]))
    (GET "/blog/"
      (redirect-to [(url "/blog/front")]))
    (GET "/blog/front"
      (render-front-as-html request))
    (POST "/blog/login"
      ;(html [:h1 "TODO: Login Action"])
      (attempt-to-authenticate request)
      (redirect-to
        ;[(url "/blog/front")]
        [(url (str "/blog/front?" (urlencode-flash-messages (get-http-request request))))]
        )
      )
    (GET "/blog/logout"
      (perform-logout request)
      (redirect-to
        [(url (str "/blog/front?" (urlencode-flash-messages (get-http-request request))))]
        )
      )
    (POST "/blog/newentry"
      ;(html [:h1 "TODO: Post New Blog Entry"])
      (post-new-blog-entry request)
      (redirect-to
        [(url (str "/blog/front?" (urlencode-flash-messages (get-http-request request))))]
        )
      )
    (POST "/blog/entry/:id/newcomment"
      (let [entry-id (new java.lang.Integer (params :id))]
        (post-new-user-comment request entry-id)
        (redirect-to
          [(url (str "/blog/entry/" entry-id "?" (urlencode-flash-messages (get-http-request request))))]
          )
        )
      )
    (GET "/blog/entry/:id"
      (render-entry-as-html
        request
        (new java.lang.Integer (params :id))
        *context*))
    (GET "/blog/atom"
      (html [:h1 "TODO: Atom Feeds :-)"]))
    (GET "/404"
      (html [:h1 "404 The page you requested does not exist"]))
    (GET "/blog/error"
      (html [:h1 "500 Aw Snap! Bless you"]))
    (GET "/blog/*"
      (. System/err (println "In-context matched none, serving static file"))
      (or (serve-static-file "." (str "" (params :*))) :next) ; served from "public" folder
      ;(or (serve-static-file "public" (route :*)))
    ))
  (ANY "*"
    ;(. System/err (println "Matched none"))
    ;(page-not-found)
    (or (serve-static-file (params :*)) :next)
    ;(redirect-to ["/404"])
    )
)

(defservice all)
