Here-There-Be-Dragons
=====================

CSE385 Creative Project

Building
--------
There's an ant build script (`build.xml`) and and Ivy script (`Ivy.xml`). The ant script includes these tasks:
    
  - `ant buildall` Generates both the client and the server jar
  - `ant clean-buildall` rebuilds the entire thing from scratch
  - `ant client` Generates only the client jar
  - `ant server` Generates only the server jar
  - `ant "run client"` Generates the client jar and runs it
  - `ant "run server"` Generates the server jar and runs it
  - `ant resolve` Download the dependencies with Ivy
