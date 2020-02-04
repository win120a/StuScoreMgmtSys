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

<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="javax.servlet.http.HttpServletRequest" %>
<%@ page import="java.sql.*" %>

<%@ include file="WEB-INF/dbConfig.jsp" %>

<%
	String testParm = request.getParameter("test");

	if (testParm != null)
	{
		%><jsp:forward page="test.jsp"></jsp:forward><%
	}

	String subMParm = request.getParameter("subM");

	if (subMParm != null)
	{
		%><script>location.href = "subM/index.jsp"</script><%
	}

	String stuMParm = request.getParameter("stuM");

	if (stuMParm != null)
	{
		%><script>location.href = "stuM/index.jsp"</script><%
	}

	String scoreMParm = request.getParameter("scoreM");

	if (scoreMParm != null)
	{
		%><script>location.href = "scoreM/index.jsp"</script><%
	}

	String installParm = request.getParameter("install");

	if (installParm != null)
	{
		getParameters(request);
		saveConfigurations(application, false);
		%><jsp:forward page="WEB-INF/installDB.jsp"></jsp:forward><%
	}

	String loginParm = request.getParameter("login");

	if (loginParm != null)
	{
		getParameters(request);
		saveConfigurations(application, true);
	}
%>

<!DOCTYPE html>
<html>
<head>
	<title>学生成绩管理系统</title>
</head>
<body style="text-align: center;">
	<%
		if(!configured) {
	%>
	<h1>系统登录</h1><br />
	<form action="" method="post">
		数据库驱动：
		<select name="driver">
			<%@ page import = "java.util.Iterator" %>
			<%
				Class.forName("com.mysql.cj.jdbc.Driver");

				Iterator i = DriverManager.getDrivers().asIterator();
				
				while(i.hasNext())
					out.write("<option>" + i.next().getClass().getName() + "</option>");
			%>
		</select>
		<br />
		数据库服务器：<input type="text" name="serverAddr" value=""><br />
		数据库：<input type="text" name="db" value=""><br />
		用户名：<input type="text" name="userName" value=""><br />
		密  码：<input type="password" name="password" value=""><br />
		服务器时区：<input type="text" name="serverTimeZone" value=""><br />
		<input type="hidden" name="login" value="1"><br />
		<input type="submit" value="登录">
		<input type="submit" name="install" value="数据库安装">
	</form><br />
	<%
		}
	else { %>
	<h1>系统主页</h1>
	<form action="" method="post">
		<input type="submit" name="stuM" value="学生管理">
		<input type="submit" name="subM" value="学科管理">
		<input type="submit" name="scoreM" value="成绩管理">
		<input type="submit" name="test" value="测试">
	</form>
	<% } %>
</body>
</html>