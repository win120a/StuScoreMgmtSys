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

<%@ page contentType="text/html; charset=utf-8" errorPage="../WEB-INF/errorPage.jsp" %>
<%@ page import="java.util.Map, java.util.HashSet" %>
<%@ include file="../WEB-INF/dbConn.jsp" %>
<%@ include file="../WEB-INF/types.jsp" %>

<%
	ResultSet rs = stmt.executeQuery("select * from kc;");   // Put here to query data after the delete process.
%>

<!DOCTYPE html>
<html>
<head>
	<title>课程管理</title>
	<%@ include file="../WEB-INF/mgmtCommon.jsp" %>
	
	<script>
		function openDialog(url)
		{
			window.open(url, "info", "width=670 height=270 left=300 top=50");
		}
	</script>
</head>
<body>
	<h1>课程管理</h1>
	<form action="listProc" method="post">
	<table class="T">  <%-- JSP Scriptlet that uses SQL Commands --%>
		<tr>
			<td>课程号</td>
			<%
				while (rs.next())
				{
					%><td><%= rs.getString("courseID") %></td><%
				}
			%>
		</tr>

		<tr>
			<td>课程名</td>
			<%
				rs.beforeFirst();
				while (rs.next())
				{
					%><td><%= rs.getString("courseName") %></td><%
				}
			%>
		</tr>

		<tr>
			<td>开课学期</td>
			<%
				rs.beforeFirst();
				while (rs.next())
				{
					%><td><%= rs.getString("term") %></td><%
				}
			%>
		</tr>

		<tr>
			<td>学时</td>
			<%
				rs.beforeFirst();
				while (rs.next())
				{
					%><td><%= rs.getString("courseHours") %></td><%
				}
			%>
		</tr>

		<tr>
			<td>学分</td>
			<%
				rs.beforeFirst();
				while (rs.next())
				{
					%><td><%= rs.getString("credits") %></td><%
				}
			%>
		</tr>

		<tr>
			<td>操作</td>
			<%
				String reqS = "subInfo.jsp?type=" + TYPE_MODIFY + "&id=";
				rs.beforeFirst();
				while (rs.next())
				{
					%><td>
						删除? 
						<input name='<%= rs.getString("courseID") %>' type="checkbox" class="del"><br />
						<a href='javascript:void(0);'
							onclick='openDialog("<%= reqS %><%= rs.getString("courseID")%>");'>编辑</a>
					</td><%
				}
			%>
		</tr>
	</table><br />
	<input type="button" id="addStudent" onclick='openDialog("subInfo.jsp?type=add");' value="添加">
	<input type="submit" name="del" onclick="return checkSelection();" value="删除">
	<input type="button" name="returnButt" onclick='location.href="../"' value="返回">
	</form>
</body>
</html>