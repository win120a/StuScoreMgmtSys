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
<%@ page import="java.util.Map, java.util.HashSet" %>
<%@ include file="../WEB-INF/dbConn.jsp" %>
<%@ include file="../WEB-INF/types.jsp" %>

<%
	PreparedStatement ps = conn.prepareStatement("delete from kc where kc.courseID = ?;");
	String delF = request.getParameter("del");
	StringBuilder undoneMessageBuilder = new StringBuilder();
	undoneMessageBuilder.append("如下课程由于此前输入了成绩，不能删除。\\n\\n课程号：\\n");
	boolean notFullyDel = false;


	if (delF != null)
	{
		Map<String, String[]> paramMap = request.getParameterMap();
		HashSet<String> delKeys = new HashSet<>();

		for(Map.Entry<String, String[]> s : paramMap.entrySet())
		{
			if (s.getKey().equals("del")) 
				continue;
			if (s.getValue()[0].toLowerCase().equals("on"))
				delKeys.add(s.getKey());
			// out.print("K: " + s.getKey() + "  V: " + s.getValue()[0] + "\n");
		}

		// out.print("\n\n\n");

		// out.print("DK:  ");

		for(String s : delKeys)
		{
			// out.print(s + ",");

			ps.setString(1, s);

			try
			{
				ps.execute();
			}
			catch(java.sql.SQLIntegrityConstraintViolationException e)
			{
				notFullyDel = true;
				undoneMessageBuilder.append(s);
				undoneMessageBuilder.append("\\n");
			}
		}

		if(notFullyDel)
		{
			out.write("<script>alert(\"" + undoneMessageBuilder.toString() + "\");</script>");
		}
	}

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
	<form action="" method="post">
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