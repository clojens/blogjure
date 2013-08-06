;; html_util
(ns blogjure.mvc.mvc_util
  (:use compojure.encodings)
  )

; ---------------- Functions to set flash messages ----------------

(defn- init-and-return-flash-messages
  "Initialize flash messages as a map (set as servlet attribute)"
  [servlet-request]
  (let [flash-messages {:error [] :notice [] :success []}]
    (do (.setAttribute servlet-request "flash-messages" flash-messages))
    flash-messages
    )
  )

(defn- get-flash-messages
  "Obtain (initialized if required) flash messages"
  [servlet-request]
  (let [flash-messages (.getAttribute servlet-request "flash-messages")]
    (if (= flash-messages nil)
      (init-and-return-flash-messages servlet-request)
      flash-messages
      )
    )
  )

; [TODO] The entire flash-messages chunk is being re-assembled on the fly.
; Consider making a ref'able item.
(defn- add-flash-message
  "Adds flash message to specified type"
  [flash-type message-str servlet-request]
  (let [flash-messages (get-flash-messages servlet-request)]
    (let [messages-by-type (flash-type flash-messages)]
      (do
        (.setAttribute servlet-request "flash-messages"
          (assoc (dissoc flash-messages flash-type)
            flash-type (cons message-str messages-by-type)
            )
          )
        )
      )
    )
  )

(defn get-error-flash-messages
  ""
  [servlet-request]
  (:error (get-flash-messages servlet-request))
  )

(defn add-error-flash-message
  "Adds error flash message"
  [message-str servlet-request]
  (add-flash-message
    :error message-str servlet-request
    ) 
  )

(defn get-notice-flash-messages
  ""
  [servlet-request]
  (:notice (get-flash-messages servlet-request))
  )

(defn add-notice-flash-message
  "Adds error flash message"
  [message-str servlet-request]
  (add-flash-message
    :notice message-str servlet-request
    ) 
  )

(defn get-success-flash-messages
  ""
  [servlet-request]
  (:success (get-flash-messages servlet-request))
  )

(defn add-success-flash-message
  "Adds error flash message"
  [message-str servlet-request]
  (add-flash-message
    :success message-str servlet-request
    ) 
  )

; ---------------- Functions to create URI params for flash messages ----------------

(defn- urlencode-messages-by-type
  "Accept a type of flash messages and encode them as URL"
  [flash-type-str messages-by-type]
  (if (= messages-by-type nil)
    []
    (seq
      (for [each messages-by-type]
        (str flash-type-str "=" (urlencode each))
        )
      )
    )
  )

(defn urlencode-flash-messages
  "URL-encode the flash messages, e.g. error=Validation+error&notice=Update+over"
  [servlet-request]
  (let [flash-messages (get-flash-messages servlet-request)]
    (apply str (interpose "&"
      (into (into
        ; error
        (urlencode-messages-by-type "error"   (get-error-flash-messages servlet-request))
        ; notice
        (urlencode-messages-by-type "notice"  (get-notice-flash-messages servlet-request))
        )
        ; success
        (urlencode-messages-by-type "success" (get-success-flash-messages servlet-request))
        )
      ))
    )
  )

; ---------------- Functions to render flash messages as HTML ----------------

(defn- flash-message-coll
  ""
  [msgtype-str message-coll]
  (apply str (for [each message-coll :when (not= each nil)]
    (str "<div class='" msgtype-str "'>" each "</div>"))
    )
  )

(defn flash-messages
  "Flash messages for a given type"
  [msgtype-str message-or-coll]
  (if (vector? message-or-coll)
    (flash-message-coll msgtype-str message-or-coll)
    (flash-message-coll msgtype-str [message-or-coll])
    )
  )

(defn flash-all-messages
  "Flash messages automatically based on supplied params map"
  [params]
  (str
    (flash-messages "error"   (:error   params))
    (flash-messages "notice"  (:notice  params))
    (flash-messages "success" (:success params))
    )
  )

(defn css-includes
  "Include CSS files in HTML"
  [context-path]
  (str
    "
  <!-- Framework CSS -->
  <link rel='stylesheet' href='" context-path "/blueprint/screen.css' type='text/css' media='screen, projection'> 
  <link rel='stylesheet' href='" context-path "/blueprint/print.css' type='text/css' media='print'> 
  <!--[if lt IE 8]><link rel='stylesheet' href='" context-path "/blueprint/ie.css' type='text/css' media='screen, projection'><![endif]--> 
  
  <!-- Import fancy-type plugin for the sample page. --> 
  <link rel='stylesheet' href='" context-path "/blueprint/plugins/fancy-type/screen.css' type='text/css' media='screen, projection'>
  
  <!-- CSS for Blogjure -->
  <link rel='stylesheet' href='" context-path "/css/blogjure.css' type='text/css' media='screen, projection'>
    "
    )
  )

(defn is-logged-in
  "Returns true if logged in, false otherwise"
  [http-session]
  (if (= (.getAttribute http-session "login") nil) false true)
  ;(if (= (:login http-session) nil) false true)
  )

(defn set-logged-in
  "Set logged-in status"
  [http-session]
  (.setAttribute http-session "login" "true")
  ;(alter http-session assoc :login "true")
  )

(defn unset-logged-in
  "Unset logged-in status"
  [http-session]
  (.removeAttribute http-session "login")
  ;(alter http-session dissoc :login)
  )

(defn blog-title-includes
  "Include blog title / subtitle in HTML"
  [http-session request-params]
  (str
    "
        <h1><a class='title' href='/blogjure'>Blogjure</a><h1>
        <h2 class='alt'>The personal blog</h2>
        " (flash-all-messages request-params)
        (if (not (is-logged-in http-session))
          (str "
            <div class='notice'>Authentication is not implemented yet -
              just click on Login button to login. Modifying username/password
              will cause the login to fail.
            </div>"
            )
          )
        "
        <div class='loginbar' align='right'>
        "
        (if (not (is-logged-in http-session))
          ; then
          (str
            "
            <form name='loginform' method='POST' action='/blogjure/blog/login' autocomplete='off'>
              <input type='text' name='username' value='Username' size='16'
                onFocus=\"if(this.value=='Username') this.value='';\"
                onBlur=\"if(this.value=='') this.value='Username';\" />
                
              <input type='password' name='password' value='Password' size='14'
                onFocus=\"if(this.value=='Password') this.value='';\"
                onBlur=\"if(this.value=='') this.value='Password';\" />
              
              <input type='submit' value='Login' />
            </form>
            "
            )
          ; else
          (str
            "
            <a href='#' class='loginbar' onClick=\"
              var ele = document.getElementById('newentry');
              if(ele.className == 'show') {
                ele.className='hide';
              }
              else if(ele.className == 'hide') {
                ele.className='show';
              }
              \">Post new entry</a>
            &nbsp;|&nbsp;&nbsp;
            <a href='/blogjure/blog/logout' class='loginbar' onClick=\"
              var ele = document.getElementById('newentry');
              ele.className='hide';
              \">Logout</a>
            &nbsp;&nbsp;
            "
            )
          )
        "
        </div>
    "
    (if (is-logged-in http-session)
      (str
        "
        <div id='newentry' class='hide' align='center'>
          <form name='newentry' method='POST' action='/blogjure/blog/newentry'>
            Enter the new blog entry
            <br/>
            <input type='text' name='title' value='Title' onClick=''
              onFocus=\"if(this.value=='Title') this.value='';\"
              onBlur=\"if(this.value=='') this.value='Title';\"
              />
            <br/>
            <textarea name='content' rows='40' cols='80' ></textarea>
            <br/>
            <div align='center'>
            <input type='submit' value='Post new entry' />
            <input type='button' value='Cancel' onClick=\"
              var ele = document.getElementById('newentry');
              ele.className='hide';
              \" />
            </div>
          </form>
        </div>
        "
        )
      ""
      )
    )
  )
