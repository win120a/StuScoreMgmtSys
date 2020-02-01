<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="java.util.Map, java.util.HashSet" %>
<%@ include file="../WEB-INF/dbConn.jsp" %>
<%@ include file="../WEB-INF/types.jsp" %>

<%
	PreparedStatement ps = conn.prepareStatement("delete from kc where kc.courseID = ?;");
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

	ResultSet rs = stmt.executeQuery("select * from kc;");   // Put here to query data after the delete process.
%>

<!DOCTYPE html>
<html>
<head>
	<title>课程管理</title>
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
			window.open(url, "_blank", "width=670 height=270 left=300 top=50");
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
	<h1>课程管理</h1>
	<form action="" method="post">
	<table class="T">  <%-- JSP Scriptlet that uses SQL Commands --%>
		<%--
			stuidname major gender birthdate totalCredits photo remark
		--%>
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