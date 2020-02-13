<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="ac.adproj.scms.dao.*, java.sql.*" %>
<%@ include file="WEB-INF/dbConfig.jsp" %>

<!DOCTYPE html>
<html>
<head>
	<title>Test DAO</title>
</head>
<body>
	<%
		DBDao daoO = new DBDao(driver, serverAddr, userName, password, serverTimeZone, db);

		ResultSet rs = daoO.query("select * from xs;");

		while (rs.next())
		{
			out.write("<p>" + rs.getString("stuid") + "</p>");
		}
	%>
</body>
</html>