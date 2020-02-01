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

<%@ include file="../WEB-INF/dbConn.jsp" %>
<%@ include file="../WEB-INF/types.jsp" %>

<%@ page import="java.net.URLDecoder, java.nio.charset.Charset" %>

<%--
	stuid name major gender birthdate totalCredits photo remark
--%>

<script>
	function loadVars() {}
</script>

<%
	request.setCharacterEncoding("utf-8");
	String addP = request.getParameter("add");
	String modP = request.getParameter("modify");
	String type = request.getParameter("type");

	if (addP != null && addP.equals("1"))
	{
		StringBuilder builder = new StringBuilder();

		builder.append("insert into xs values (\"");
		builder.append(request.getParameter("id"));

		builder.append("\", \"");

		String name = request.getParameter("name");

		builder.append(name);

		builder.append("\", \"");
		builder.append(request.getParameter("major"));
		builder.append("\", \"");
		builder.append(request.getParameter("gender"));
		builder.append("\", \"");
		builder.append(request.getParameter("dob"));
		builder.append("\", ");
		builder.append("0");
		builder.append(", ");
		builder.append("NULL");
		builder.append(", \"");
		builder.append(request.getParameter("remark"));
		builder.append("\"");
		builder.append(");");

		// out.write("<script>alert('SQLS: " + builder.toString() + "');</script>");
		stmt.execute(builder.toString());
		%>
		<script>
			window.alert("添加成功！");
			opener.location.reload();
			window.opener = null;
			window.close();
		</script><%
	}

	if (modP != null && modP.equals("1"))
	{
		PreparedStatement ps = conn.prepareStatement("update xs set name=?, major=?, gender=?, birthdate=?, " + 
														"remark=? where stuid=?;");
														
		ps.setString(1, request.getParameter("name"));
		ps.setString(2, request.getParameter("major"));
		ps.setString(3, request.getParameter("gender"));
		ps.setString(4, request.getParameter("dob"));
		ps.setString(5, request.getParameter("remark"));
		ps.setString(6, request.getParameter("id"));

		ps.execute();
		%>
		<script>
			window.alert("修改成功！");
			opener.location.reload();
			window.opener = null;
			window.close();
		</script><%
	}

	if (TYPE_MODIFY.equals(type))
	{
		ResultSet rs = stmt.executeQuery("select * from xs where stuid=" + request.getParameter("id") + ";");
		rs.next();

		int gender = rs.getInt("gender");

		%>
		<script>
			// M - 1   F - 0
			function loadVars()
			{
				document.getElementById("name").value = '<%= rs.getString("name") %>';
				document.getElementById("id").value = '<%= rs.getString("stuid") %>';
				document.getElementById("id").disabled = true;
				document.getElementById("dob").value = '<%= rs.getString("birthdate") %>';
				document.getElementById("major").value = '<%= rs.getString("major") %>';
				<%
					String remark = rs.getString("remark");
					remark = remark == null ? "" : remark;
				%>

				document.getElementById("remark").value = '<%= remark %>';

				if(<%= gender %> == 1)
					document.getElementById("genderM").checked = true;
				else
					document.getElementById("genderF").checked = true;
			}
		</script><%
	}
%>

<!DOCTYPE html>
<html>
<head>
	<title>学生信息</title>
	<style type="text/css">
		td
		{
			padding-right : 50px;
		}
	</style>
	<meta charset="utf-8">
</head>
<body style="text-align : center;" onload="loadVars();">
	<form action="" method="post">
		<fieldset>
			<legend>学生信息</legend>
			
			<table>
				<tr>
					<td>
						<label for="name">姓名：</label>
						<input type="text" name="name" id="name" required>&nbsp;
					</td>
					<td>
						<label for="id">学号：</label>
						<input type="text" name="id" id="id" required><br />
					</td>
				</tr>

				<tr>
					<td>
						<label>性别：</label>
						<input type="radio" name="gender" id="genderM" value="1" required>
						<label for="genderM">男</label>
						<input type="radio" name="gender" id="genderF" value="0" required>
						<label for="genderF">女</label>
					</td>

					<td>
						<label for="dob">生日：</label>
						<input type="text" name="dob" id="dob" required>
					</td>
				</tr>

				<tr>
					<td>
						<label for="major">专业：</label>
						<input type="text" name="major" id="major" required>&nbsp;
					</td>
				</tr>
			</table>
						

			<label for="remark">备注：</label><br />
			<textarea name="remark" id="remark" cols="80" rows="20"></textarea>

			<%
				if (type.equals(TYPE_ADD))
				{ %>
					<input type="hidden" name="add" value="1"><br /><br /><%
				}
				else if(type.equals(TYPE_MODIFY))
				{ %>
					<input type="hidden" name="modify" value="1"><br /><br /><%
				}
			%>


			<input type="submit" id="submit" value="提交" />
			<input type="reset" id="reset" value="重置" />
			<%
				if(type.equals(TYPE_MODIFY))
				{
					%>
					<script>
						document.getElementById("reset").onclick = function()
						{
							loadVars();
							return false;
						}
					</script><%
				}
			%>
		</fieldset>
	</form>
</body>
</html>