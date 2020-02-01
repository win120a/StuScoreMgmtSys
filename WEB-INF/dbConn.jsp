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

<%@ page import="java.util.Date, java.util.Properties" %>    <%-- Use commas to import multiple classes. --%>
<%@ page import="java.sql.*" %>     <%-- Equals to the import statements. --%>

<%@ include file="./dbConfig.jsp" %>

<%
	try
	{
		Class.forName(driver);
	}
	catch(ClassNotFoundException cnfe)
	{
		cnfe.printStackTrace();
	}
%>

<%
	Properties p = new Properties();
	p.put("user", userName);
	p.put("password", password);
	p.put("timezone", serverTimeZone);
	Connection conn = DriverManager.getConnection(configured ? "jdbc:mysql://" + serverAddr + "/" + db : 
													"jdbc:mysql://" + serverAddr + "/" , p);
	Statement stmt = conn.createStatement();
%>