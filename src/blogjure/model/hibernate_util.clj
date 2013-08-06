;; hibernate_util

(ns blogjure.model.hibernate_util)

(defn init-config
  "Intialize Hibernate configuration for application - TO BE CALLED ONLY ONCE"
  []
  (let [hibernate-cfg (new org.hibernate.cfg.AnnotationConfiguration)]
    (doto hibernate-cfg
      ; naming strategy (expand camel-case to underscore delimited col names)
      (.setNamingStrategy (new org.hibernate.cfg.ImprovedNamingStrategy))
      ; add domain model POJOs
      (.addAnnotatedClass blogjure.model.BlogEntry)
      (.addAnnotatedClass blogjure.model.UserComment)
      )
    )
  )

(def global-hibernate-cfg (init-config) )

(defn get-session-factory
  "Get Hibernate session factory - TO BE CALLED ONLY ONCE"
  [hibernate-cfg]
  (.buildSessionFactory hibernate-cfg)
  )

(def global-hibernate-session-factory (get-session-factory global-hibernate-cfg) )

(defn get-session
  "Get Hibernate session - call once for every transaction"
  ([]
    (get-session global-hibernate-session-factory))
  ([hibernate-session-factory]
    (.openSession hibernate-session-factory))
  )

(defn with-hibernate-session
  "
  Execute function while passing Hibernate session as a parameter to it.
  Example usage below (Recommended for read operations only):
  (with-hibernate-session
    (fn [hibernate-session]
      (let [e (.findById (new blogjure.model.BlogEntry) hibernate-session (long 1))]
        (println e)
        )
      )
    )
  "
  [func]
  (let [ss (get-session)]
    (try
      (func ss)
      (finally (.close ss) )
      )
    )
  )

(defn with-hibernate-transaction
  "
  Execute function in a transaction, while passing session as a parameter.
  Example usage (below):
  (with-hibernate-transaction
    (fn [hibernate-session]
      ; Saving blog entry with session
      (.save (get-new-blog-entry) hibernate-session)
      )
    )
  "
  [func]
  (let [ss (get-session)
        tx (.beginTransaction ss)]
    (try
      (func ss)
      (.commit tx)
      (catch Exception ex
        (.rollback tx)
        (throw ex)
        )
      (finally (.close ss) )
      )
    )
  )

;; ============ DDL operations (consider using hibernate.properties) ===========

(defn ddl-hibernate-cfg
  "Returns hibernate-cfg suitable for DDL (initializes Session-factory too)"
  []
  (let [hibernate-cfg global-hibernate-cfg
        hibernate-sf  global-hibernate-session-factory]
    hibernate-cfg
    )
  )

(defn drop-tables
  "Drop tables in the schema"
  ([]
    (drop-tables (ddl-hibernate-cfg)))
  ([hibernate-cfg]
    (let [schema-export (new org.hibernate.tool.hbm2ddl.SchemaExport hibernate-cfg)]
      (.drop schema-export true true)
      )
    )
  )

(defn create-tables
  "Create tables in the schema"
  ([]
    (create-tables (ddl-hibernate-cfg)))
  ([hibernate-cfg]
    (let [schema-export (new org.hibernate.tool.hbm2ddl.SchemaExport hibernate-cfg)]
      (.create schema-export true true)
      )
    )
  )

(defn drop-create-tables
  "Drop and create tables in the schema"
  ([]
    (drop-create-tables (ddl-hibernate-cfg)))
  ([hibernate-cfg]
    (println "Dropping + creating tables for config: " hibernate-cfg)
    (let [schema-export (new org.hibernate.tool.hbm2ddl.SchemaExport hibernate-cfg)]
      (println schema-export)
      (.drop   schema-export true true)
      (.create schema-export true true)
      )
    )
  )

