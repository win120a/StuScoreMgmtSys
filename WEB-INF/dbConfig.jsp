<%--
    Copyright (C) 2011-2020 Andy Cheung

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <https://www.gnu.org/licenses/>.
--%>

<%@ page import="javax.servlet.ServletContext" %>

<%!
    String driver = "";
    String serverAddr = "";
    String userName = "";
    String password = "";
    String serverTimeZone = "";
    String db = "";

    boolean configured = false;
%>

<%
    driver = (String) application.getInitParameter("driver");
    driver = driver != null ? (String) driver : "";

    serverAddr = (String) application.getInitParameter("serverAddr");
    serverAddr = driver != null ? (String) serverAddr : "";

    userName = (String) application.getInitParameter("userName");
    
    Object confO = application.getAttribute("configured");
    configured = confO == null ? false : (Boolean) confO;
    
    userName = userName != null ? (String) userName : "";

    password = (String) application.getInitParameter("password");
    password = password != null ? (String) password : "";

    serverTimeZone = (String) application.getInitParameter("serverTimeZone");
    serverTimeZone = serverTimeZone != null ? (String) serverTimeZone : "";

    db = (String) application.getInitParameter("db");
    db = db != null ? (String) db : "";
%>

<%!
    private void saveConfigurations(ServletContext application, boolean configured)
    {
        this.configured = configured;
        application.setAttribute("configured", configured);
    }

%>