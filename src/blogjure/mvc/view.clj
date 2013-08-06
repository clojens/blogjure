;; view
(ns blogjure.mvc.view
  (:use blogjure.mvc.mvc_util)
  (:use blogjure.mvc.compojure_util))

(defn with-view-template
  "Invokes func with the view template (StringTemplate object) of specified
  name as parameter."
  [view-name func]
  (let [template-group (new org.antlr.stringtemplate.StringTemplateGroup "default")
        view-template (.getInstanceOf template-group view-name)]
    (func view-template)))

(defn render-view-template
  "Render the view template."
  [view-template]
  (.toString view-template))

(defn set-common-values
  "Set common values (for the header)"
  [view-template context http-session flash-messages]
  (.setAttribute view-template "context_path"   context)
  (.setAttribute view-template "flash_messages" flash-messages)
  (let [logged-in (is-logged-in http-session)]
    (.setAttribute view-template "logged_in" logged-in)
    (.setAttribute view-template "not_logged_in" (not logged-in))))

; Shortcut (three-in-one) function for all of above functions
(defn render-with-view-template
  "Render the view template after passing it to specified function."
  ([view-name
    context
    session
    flash-messages
    func
    ]
    (let [template-group (new org.antlr.stringtemplate.StringTemplateGroup "default")
          view-template (.getInstanceOf template-group view-name)]
      (do
        (set-common-values view-template context session flash-messages)
        (func view-template))
      (.toString view-template)))
  ([view-name
    compojure-request
    func
    ]
    (let [request-params (get-query-params compojure-request)]
      (render-with-view-template view-name
        (get-context-path compojure-request)
        (get-http-session compojure-request)
        (flash-all-messages request-params)
        func))))
