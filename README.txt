README for Blogjure
===================

Blogjure is a simple personal blog written in Clojure. This file contains
a brief log of activities happening around Blogjure.

Blogjure is licensed under LGPL 2.1 - please see LGPL-2.1.txt for details.

You can contact me by writing to: kumar.shantanu@gmail.com

Development Status:
-------------------
2009 August 02 (Sunday)
  - Migrated "view" layer (in MVC) from Clojure code to StringTemplate
  - [FIXED] Added encoding functions for HTML and XML - multiline posts
    and comments with special characters (>, <, &, ', ", newline) appear
    correctly.
  - Old "spaghetti" pure-Clojure based view layer has been removed
  - [TODO] Tests for data access layer
  - [TODO] Tests for MVC layer
2009 July 26 (Sunday)
  - Migrated data layer from SpringJDBC to Hibernate + Annotated POJOs
  - Dev-environment Hibernate properties are in test/hibernate.properties
  - Prod-environment Hibernate properties are in conf/hibernate.properties
  - Dev and prod environments default to newly embedded H2 database
  - Now you need to keep prod DB environment setup before building WAR
  - To run from command-line, $ ant run
  - Old data layer code (and tests) have been removed
  - [TODO] Tests for data layer and MVC layer
2009 July 19 (Sunday)
  - Login, logout work with hardcoded credentials
  - Posting new blog entry works
  - Posting user comment works
  - Flash messages (error, notice, success) work fine
  - [TODO] Configuration should be flexible (for prod / dev):
    Remove hardcoded DB config in blogjure/src/blogjure/db/db_util.clj
  - [TODO] Tests for MVC (might use Apache Commons HTTP-Client or
    http://github.com/technomancy/clojure-http-client/tree/master )
2009 July 17 (Friday)
  - Front page and blog-entry page render well
  - [FIXED] Static files work fine (in dev mode and in WAR)
  - New CSS style works for pages
  - Default URL auto-redirects to front page
  - [TODO] Functionality -- 1. Login, 2. Logout, 3. Post new entry, 4. Post comment
  - [TODO] Configuration should be flexible (for prod / dev):
    Remove hardcoded DB config in blogjure/src/blogjure/db/db_util.clj
  - [TODO] Tests for MVC (might use Apache Commons HTTP-Client or
    http://github.com/technomancy/clojure-http-client/tree/master )
2009 July 10 (Friday)
  - First MVC cycle with DB access is done (front page with blog entries)
  - [TODO] Tests for MVC (might use Apache Commons HTTP-Client or
    http://github.com/technomancy/clojure-http-client/tree/master )
  - [FIXME] Regression -- Serving static files broke (in dev and WAR)
  - [TODO] Add CSS for the web pages
2009 July 08 (Wednesday)
  - User comments DB access is done
  - DB Unit tests working, but need to write more tests
  - [TODO] Configuration should be flexible (for prod / dev):
    Remove hardcoded DB config in blogjure/src/blogjure/db/db_util.clj
2009 July 07 (Tuesday)
  - Refactored DB testing utility stuff out into test_dao_util
  - DB testing now re/creates DB tables and populates them with test data
  - DB access functions for "User comments" added with unit tests
2009 July 05/06 (Sunday/Monday)
  - DB access layer started (blog entries only so far)
  - DB access unit tests and test harness work
  - Build script now has better Clojure sources compile support
2009 July 04 (Saturday)
  - Project skeleton is imported into bitbucket
  - The generated WAR file works when deployed in Tomcat
  - Serving static files is an issue (Compojure doesn't handle context path)
2009 July 03 (Friday)
  - Refactored sources files to align namespaces / file-naming
  - Build script is functional (Clojure-test compile targets a bit hack'ish)
  - Unit tests working, but testing serve-static-file needs harness (later)
    (http://github.com/technomancy/clojure-http-client)
2009 July 02 (Thursday)
  - Project structure setup in IDE
  - MVC Scaffolding is setup (with Blog URIs)
  - Created the Blogjure favicon at http://www.favicon.cc/
  - Explored RING (http://github.com/mmcgrana/ring), decided not to use it
2009 July 01 (Wednesday)
  - Explored Eclipse (Clojure-dev) and NetBeans (Enclojure)
  - Liked Enclojure for auto-complete but went back to Eclipse eventually
  - Looked at Compojure, did toy experiments
2009 June 29/30 (Monday, Tuesday)
  - Googled quite a bit on the Clojure eco-system
  - Observed the structure of various blogs
  - Wrote some toy code; loved Clojure for its succinctness


Libraries being used by Blogjure:
---------------------------------
- Hibernate 3.2 (plus Annotations) for data layer
- Spring 2.5 (dependency for v0.1 data layer)
- Compojure for MVC (the JAR is modified to omit hard dependency on Jetty)
- StringTemplate 3.2 for view (in MVC) layer
- clojure.contrib.test-is for unit testing
- Ant (at least version 1.6.3) for building sources.
- Jetty to run in-process test environment

How to run
----------
If you have Java and ANT installed and PATH set, you are ready. Open terminal.
To Run (assuming *NIX, but works fine on Windows too):
  $ ant run
  (Go to URL http://localhost:8088/)
To build a deployable WAR file:
  $ ant
  (WAR file: ./build/dist/blogjure.war, Go to URL http://localhost:<port>/blogjure/)

How to setup IDE (because IDE files are not checked in):
--------------------------------------------------------
[ For Eclipse ]
  - Get Clojure-dev (http://code.google.com/p/clojure-dev/)
  - Create a Java project and enable Clojure support for the project
  - Include all JAR files (bundled and not-bundled) in project CLASSPATH
[ For NetBeans ]
  - Get Enclojure (http://enclojure.org/)
  - Create a Java project (freeform) and specify build.xml as ANT script
  - Include all JAR files (bundled and not-bundled) in project CLASSPATH
