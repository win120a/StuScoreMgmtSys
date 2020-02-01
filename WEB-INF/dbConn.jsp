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