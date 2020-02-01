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
	driver = (String) application.getAttribute("driver");
	driver = driver != null ? (String) driver : "";

	serverAddr = (String) application.getAttribute("serverAddr");
	serverAddr = driver != null ? (String) serverAddr : "";

	userName = (String) application.getAttribute("userName");
	
	Object confO = application.getAttribute("configured");
	configured = confO == null ? false : (Boolean) confO;
	
	userName = userName != null ? (String) userName : "";

	password = (String) application.getAttribute("password");
	password = password != null ? (String) password : "";

	serverTimeZone = (String) application.getAttribute("serverTimeZone");
	serverTimeZone = serverTimeZone != null ? (String) serverTimeZone : "";

	db = (String) application.getAttribute("db");
	db = db != null ? (String) db : "";
%>

<%!
	private void saveConfigurations(ServletContext application, boolean configured)
	{
		application.setAttribute("driver", driver);
		application.setAttribute("userName", userName);
		application.setAttribute("password", password);
		application.setAttribute("serverTimeZone", serverTimeZone);
		application.setAttribute("serverAddr", serverAddr);
		application.setAttribute("db", db);

		userName = (String) application.getAttribute("userName");
		this.configured = configured;
		application.setAttribute("configured", configured);
	}

%>

<%!
	private void getParameters(HttpServletRequest request)
	{
		driver = (String) request.getParameter("driver");
		driver = driver != null ? (String) driver : "";

		serverAddr = (String) request.getParameter("serverAddr");
		serverAddr = driver != null ? (String) serverAddr : "";

		userName = (String) request.getParameter("userName");
		configured = Boolean.parseBoolean((String) request.getParameter("configured"));
		userName = userName != null ? (String) userName : "";

		password = (String) request.getParameter("password");
		password = password != null ? (String) password : "";

		serverTimeZone = (String) request.getParameter("serverTimeZone");
		serverTimeZone = serverTimeZone != null ? (String) serverTimeZone : "";

		db = (String) request.getParameter("db");
		db = db != null ? (String) db : "";
	}
%>