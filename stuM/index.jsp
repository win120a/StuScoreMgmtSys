<%--
    Copyright (C) 2011-2019 Andy Cheung

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
	PreparedStatement ps = conn.prepareStatement("delete from xs where xs.stuid = ?;");
	String delF = request.getParameter("del");

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

			ps.execute();
		}
	}

	ResultSet rs = stmt.executeQuery("select * from xs;");   // Put here to query data after the delete process.
%>

<!DOCTYPE html>
<html>
<head>
	<title>学生管理</title>
	<style>
		table.T
		{
			border-collapse: collapse;
		}
		.T tr td
		{
			border : black 0.5px solid;
			text-align: center;
			width: 80px;
			font-size : 0.8em;
		}

		*
		{
			text-align: center;
		}
	</style>
	<script>
		function openDialog(url)
		{
			window.open(url, "_blank", "width=670 height=520 left=300 top=50");
		}

		function checkSelection()
		{
			let objects = document.getElementsByClassName("del");
			let flag = false;

			for (var i = objects.length - 1; i >= 0; i--) {
				if (objects[i].checked)
				{
					flag = true;
					break;
				}
			}

			if (!flag || objects.length == 0)
			{
				alert("没有选择的项目！");
				return false;
			}

			if (confirm("确认删除？"))
			{
				return true;
			}

			return false;
		}
	</script>
</head>
<body>
	<h1>学生管理</h1>
	<form action="" method="post">
	<table class="T">  <%-- JSP Scriptlet that uses SQL Commands --%>
		<%--
			stuidname major gender birthdate totalCredits photo remark
		--%>
		<tr>
			<td>姓名</td>
			<%
				while (rs.next())
				{
					%><td><%= rs.getString("name") %></td><%
				}
			%>
		</tr>

		<tr>
			<td>性别</td>
			<%
				rs.beforeFirst();
				while (rs.next())
				{
					%><td><%= rs.getInt("gender") == 1 ? "男" : "女" %></td><%
				}
			%>
		</tr>

		<tr>
			<td>学号</td>
			<%
				rs.beforeFirst();
				while (rs.next())
				{
					%><td><%= rs.getString("stuid") %></td><%
				}
			%>
		</tr>

		<tr>
			<td>专业</td>
			<%
				rs.beforeFirst();
				while (rs.next())
				{
					%><td><%= rs.getString("major") %></td><%
				}
			%>
		</tr>

		<tr>
			<td>生日</td>
			<%
				rs.beforeFirst();
				while (rs.next())
				{
					%><td><%= rs.getString("birthdate") %></td><%
				}
			%>
		</tr>

		<tr>
			<td>总学分</td>
			<%
				rs.beforeFirst();
				while (rs.next())
				{
					%><td><%= rs.getString("totalcredits") %></td><%
				}
			%>
		</tr>

		<tr>
			<td>备注</td>
			<%
				rs.beforeFirst();
				while (rs.next())
				{
					String remark = rs.getString("remark");
					%><td><%= remark == null ? "" : remark %></td><%
				}
			%>
		</tr>

		<tr>
			<td>操作</td>
			<%
				String reqS = "stuInfo.jsp?type=" + TYPE_MODIFY + "&id=";
				rs.beforeFirst();
				while (rs.next())
				{
					%><td>
						删除? 
						<input name='<%= rs.getString("stuid") %>' type="checkbox" class="del"><br />
						<a href='javascript:void(0);'
							onclick='openDialog("<%= reqS %><%= rs.getString("stuid")%>");'>编辑</a>
					</td><%
				}
			%>
		</tr>
	</table><br />
	<input type="button" id="addStudent" onclick='openDialog("stuInfo.jsp?type=add");' value="添加">
	<input type="submit" name="del" onclick="return checkSelection();" value="删除">
	<input type="button" name="returnButt" onclick='location.href="../"' value="返回">
	</form>
</body>
</html>