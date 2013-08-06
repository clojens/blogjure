;; serve-static-file
(ns blogjure.mvc.serve_static_file
  (:use clojure.contrib.def)
  (:use compojure))

;; (C) Copyright 2009 Mark McGranaghan
;; (C) Copyright 2009 Shantanu Kumar
;; Adapted from URL below
;; http://github.com/mmcgrana/ring/blob/24fe773f62026b95e275468f6875812f87989508/src/ring/file_info.clj

(defn get-filename-extension
  "Returns extension of the filename. If unavailable, returns empty string."
  [filename]
  (let [idx (.lastIndexOf filename ".")]
    (if (or (< idx 0) (= idx (- (.length filename) 1)))
      "" (.substring filename (+ idx 1)))))

(defvar- base-mime-types
  {"ai"    "application/postscript"
   "asc"   "text/plain"
   "avi"   "video/x-msvideo"
   "bin"   "application/octet-stream"
   "bmp"   "image/bmp"
   "class" "application/octet-stream"
   "cer"   "application/pkix-cert"
   "crl"   "application/pkix-crl"
   "crt"   "application/x-x509-ca-cert"
   "css"   "text/css"
   "dms"   "application/octet-stream"
   "doc"   "application/msword"
   "dvi"   "application/x-dvi"
   "eps"   "application/postscript"
   "etx"   "text/x-setext"
   "exe"   "application/octet-stream"
   "gif"   "image/gif"
   "htm"   "text/html"
   "html"  "text/html"
   "ico"   "image/x-icon"
   "jpe"   "image/jpeg"
   "jpeg"  "image/jpeg"
   "jpg"   "image/jpeg"
   "js"    "text/javascript"
   "lha"   "application/octet-stream"
   "lzh"   "application/octet-stream"
   "mov"   "video/quicktime"
   "mpe"   "video/mpeg"
   "mpeg"  "video/mpeg"
   "mpg"   "video/mpeg"
   "pbm"   "image/x-portable-bitmap"
   "pdf"   "application/pdf"
   "pgm"   "image/x-portable-graymap"
   "png"   "image/png"
   "pnm"   "image/x-portable-anymap"
   "ppm"   "image/x-portable-pixmap"
   "ppt"   "application/vnd.ms-powerpoint"
   "ps"    "application/postscript"
   "qt"    "video/quicktime"
   "ras"   "image/x-cmu-raster"
   "rb"    "text/plain"
   "rd"    "text/plain"
   "rtf"   "application/rtf"
   "sgm"   "text/sgml"
   "sgml"  "text/sgml"
   "swf"   "application/x-shockwave-flash"
   "tif"   "image/tiff"
   "tiff"  "image/tiff"
   "txt"   "text/plain"
   "xbm"   "image/x-xbitmap"
   "xls"   "application/vnd.ms-excel"
   "xml"   "text/xml"
   "xpm"   "image/x-xpixmap"
   "xwd"   "image/x-xwindowdump"
   "zip"   "application/zip"})

(defn- guess-mime-type
  "Returns a String corresponding to the guessed mime type for the given file,
  or application/octet-stream if a type cannot be guessed."
  [#^File filename mime-types]
  (get mime-types (get-filename-extension filename)
    ;"application/octet-stream"
    "text/html"))

(defn serve-static-file
  "Serves static file with guessed mime-type (content-type)"
  ([filename]
  (. System/err (println (str "Serving static file: " filename)))
  [(content-type (guess-mime-type filename base-mime-types))
    (serve-file filename)])
  ([base-dir filename]
  (. System/err (println (str "Serving static file from " base-dir ": " filename)))
  [(content-type (guess-mime-type filename base-mime-types))
    (serve-file base-dir filename)])
  )

