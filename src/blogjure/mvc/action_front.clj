;; mvc_front

(ns blogjure.mvc.action_front
  (:use blogjure.mvc.view)
  (:use blogjure.model.hibernate_util)
  (:use blogjure.util.common_util)
  )

(defn- render-blog-entries
  "Render frontpage for blog entries"
  [compojure-request blog-entries]
  (render-with-view-template "view_front" compojure-request (fn[view-template]
    (let [entries-count (count blog-entries)]
      (if (> entries-count 1)
        (.setAttribute view-template "many_blog_entries" true))
      (if (= entries-count 1)
        (.setAttribute view-template "one_blog_entry"    true)))
    (apply str (for [each blog-entries] (do
      (.setAttribute view-template "blog_entries" {
        "id"          (.id          each)
        "title"       (encode-as-html-string (.title       each))
        "when_posted" (.when_posted each)
        "content"     (encode-as-html-string (.content     each))
        "comments_count" (let [comments-count (.size (.comments each))]
                           (if (= comments-count 0) "No" (str "" comments-count)))
        }
        )))))))

(defn render-front-as-html
  "Render the frontpage"
  [compojure-request]
  (with-hibernate-session (fn[hibernate-session]
    (let [blog-entries (.findAll (new blogjure.model.BlogEntry) hibernate-session)]
      (render-blog-entries compojure-request blog-entries)))))
