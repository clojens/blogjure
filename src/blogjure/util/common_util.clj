;; common_util

(ns blogjure.util.common_util
  )

(defn to-vector
  "Returns non-vector as a vector"
  [elem]
  (if (vector? elem) elem [elem])
  )

(defn is-null-string
  "Returns true if supplied string is null/empty"
  [any-string]
  (if (or (= any-string nil) (= any-string ""))
    true
    false
    )
  )

(defn is-any-null-string-in-map
  "Returns true if there exists at least one null/non-existent string value
  in a given map"
  [any-map & keyseq]
  (if (> (count
    (for [each keyseq :when (is-null-string (each any-map))] :dummy)
    ) 0)
    true
    false
    )
  )

(def xml-encode-lookup-table {
  \" "&quot;"
  \' "&apos;"
  \& "&amp;"
  \< "&lt;"
  \> "&gt;"
  })

(def html-encode-lookup-table (assoc xml-encode-lookup-table \newline "<br/>"))

(defn encode-as-formatted-string
  "Encodes supplied string as per supplied character-lookup table"
  [raw-str lookup-table]
  (apply str
    (for [chr raw-str]
      (let [tchr (get lookup-table chr)]
        (if (nil? tchr) chr tchr)))))

(defn encode-as-xml-string
  "Encodes supplied string as XML"
  [raw-str]
  (encode-as-formatted-string raw-str xml-encode-lookup-table))

(defn encode-as-html-string
  "Encodes supplied string as HTML"
  [raw-str]
  (encode-as-formatted-string raw-str html-encode-lookup-table))
