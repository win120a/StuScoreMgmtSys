# Introduction
A really simple Java EE project (Model 1 webApp, currently moving to Model 2) that provides a way to manage the students' score.<br />
In fact, I use this project to practise some basic Java EE skills, especially JSP (Java Web) & JDBC. :)

# License
GPLv3

# Third-party Code Credits
1. This program uses MySQL Connector/J 8.0 with no code altered (Licensed under GPLv2). <br />
2. `.gitignore` is based on `Java.gitignore` in https://github.com/github/gitignore, which is licensed under Creative Commons Zero v1.0 Universal License (CC0-1.0).
3. `WEB-INF/lib/commons-fileupload-1.4.jar` and `WEB-INF/lib/commons-io-2.6.jar` are files of the Apache Commons Project (http://commons.apache.org/), which is licensed under Apache License.
4. Gson project (https://github.com/google/gson) is used (`WEB-INF/lib/gson-2.8.6.jar`). It is licensed under Apache License.
5. Open-source JavaScript library - jQuery (`scripts/jquery-3.4.1.js`, licensed under MIT License)
6. Open-source UI kit - Bootstrap (MIT licensed, https://github.com/twbs/bootstrap )
7. Database Connection Pool - C3P0 (https://github.com/swaldman/c3p0, dual licensed under EPL 1.0 / LGPL 2.1)

# Structure
1. The building system of the Servlet Part of this project (a.k.a. `WEB-INF/src` and `WEB-INF/classes`) is driven by Ant. The build file is located in the root of the project.
2. The whole project is a web application, copy it into Tomcat's `webapps` folder to deploy.
3. The DB Server Configuration is located in `WEB-INF/web.xml`.

# Building & Deploy
0. Copy The entire folder to the `webapps` directory.
1. Edit values in `props.xml` (JAVA_HOME) & `WEB-INF/web.xml` (Server Configurations).
2. Compile the ant project.
3. Forward to http://[Address to WebApp]/installDB.jsp to create database.
4. Delete the installDB.jsp file.