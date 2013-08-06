;; action_entry

(ns blogjure.mvc.action_entry
  (:use blogjure.model.hibernate_util)
  (:use blogjure.mvc.mvc_util)
  (:use blogjure.mvc.view)
  (:use blogjure.mvc.compojure_util)
  (:use compojure)
  (:use blogjure.util.common_util)
  )

(defn- render-comments
  "Accept List<Comment> and return List<Map<Comment>>"
  [view-template comments]
  (doall
    (for [each comments]
      (.setAttribute view-template "comments" (assoc {
        "id"          (.id          each)
        "content"     (encode-as-html-string (.content     each))
        "when_posted" (.when_posted each)
        "is_deleted"  (.is_deleted  each)
        "name"        (encode-as-html-string (.name        each))
        "email"       (encode-as-html-string (.email       each))
        "url"         (encode-as-html-string (.url         each))
        }
        "url_is_null" (is-null-string (.url each)))))))

(defn- render-blog-entry
  "Render HTML for blog entry and comments"
  [compojure-request blog-entry]
  (render-with-view-template "view_entry" compojure-request (fn[view-template]
    (let [c (.comments blog-entry)]
      (.setAttribute   view-template "one_comment"   (= (count c) 1))
      (.setAttribute   view-template "many_comments" (> (count c) 1))
      (render-comments view-template c)
      (.setAttribute   view-template "blog_entry" {
        "id"            (.id          blog-entry)
        "title"         (encode-as-html-string (.title       blog-entry))
        "when_posted"   (.when_posted blog-entry)
        "content"       (encode-as-html-string (.content     blog-entry))
        }
        )))))

(defn render-entry-as-html
  "Display blog entry"
  [compojure-request entry-id context]
  ; gather data and render html
  (with-hibernate-session (fn[hibernate-session]
    (let [blog-entry (.findById (new blogjure.model.BlogEntry) hibernate-session (long entry-id))]
      (if (= blog-entry nil)
        (redirect-to (str context "/404"))
        ; display the page
        (render-blog-entry
          compojure-request blog-entry))))))
