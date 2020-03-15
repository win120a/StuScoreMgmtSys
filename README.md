# Introduction
A really simple Java EE project (Model 1 webApp, currently moving to Model 2) that provides a way to manage the students' score.<br />
In fact, I use this project to practise some basic Java EE skills, especially JSP (Java Web) & JDBC. :)

# License
GPLv3

# Third-party Code Credits
1. This program uses MySQL Connector/J 8.0 with no code altered (Licensed under GPLv2). <br />
2. `.gitignore` is based on `Java.gitignore` in https://github.com/github/gitignore, which is licensed under Creative Commons Zero v1.0 Universal License (CC0-1.0).
3. `WEB-INF/lib/commons-fileupload-1.4.jar` and `WEB-INF/lib/commons-io-2.6.jar` are files of the Apache Commons Project (http://commons.apache.org/), which is licensed under Apache License.

# Structure
1. The Servelet Part of this project (a.k.a. `WEB-INF/src` and `WEB-INF/classes`) is an Ant project (the build.xml file is in root).
2. The whole folder is a web-app folder, copy it into Tomcat's `webapps` folder to deploy.
3. The DB Server Configuration is located in `WEB-INF/web.xml`.

# Building & Deploy
0. Copy The entire folder to the `webapps` directory.
1. Edit values in `WEB-INF/build.xml` (JAVA_HOME) & `WEB-INF/web.xml` (Server Configurations).
2. Compile the ant project.
3. Forward to http://[Address to WebApp]/installDB.jsp to create databases.
4. Delete the installDB.jsp file.