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
<%@ page import="java.util.Map, java.util.HashMap" %>
<%@ include file="../WEB-INF/dbConn.jsp" %>
<%@ include file="../WEB-INF/types.jsp" %>

<%
	PreparedStatement ps = conn.prepareStatement("select score from xs_kc where stuid=? and courseID=?;");

	PreparedStatement ps_u = conn.prepareStatement("update xs_kc set score=?, credit=? where stuid=?" +
													"and courseID=?;");

	// sID, cID, score, credit
	PreparedStatement ps_i = conn.prepareStatement("insert into xs_kc values (?, ?, ?, ?);");

	ps.setString(2, request.getParameter("id"));
	ps_i.setString(2, request.getParameter("id"));
%>

<%
	String updateP = request.getParameter("update");

	if (updateP != null && updateP.equals("1"))
	{
		Map<String, String[]> paramMap = request.getParameterMap();
		Map<String, String> scoreMap = new HashMap<>(paramMap.size());

		for (Map.Entry<String, String[]> s : paramMap.entrySet())
		{
			if (s.getKey().equals("update") || s.getKey().equals("id")) 
				continue;
			if (s.getKey().contains("score_")) 
				scoreMap.put(s.getKey(), s.getValue()[0]);
		}

		for(Map.Entry<String, String> s : scoreMap.entrySet())
		{
			ResultSet creditsS = stmt.executeQuery("select credits from kc where courseID=" + 
													request.getParameter("id"));
			creditsS.next();
			String credits =  creditsS.getString("credits");
			String score = s.getValue();

			try
			{
				if (s.getValue().isEmpty())
				{
					score = "-1";
				}

				ps_i.setString(1, s.getKey().replace("score_", ""));
				ps_i.setString(3, score);
				ps_i.setString(4, credits);
				ps_i.execute();
			}
			catch (SQLException sqle)  // In case the data was inserted (i.e exists).
			{
				sqle.printStackTrace();
				// s c i
				if (s.getValue().isEmpty())
				{
					score = "-1";
				}

				ps_u.setString(1, score);
				ps_u.setString(2, credits);
				ps_u.setString(3, s.getKey().replace("score_", ""));
				ps_u.setString(4, request.getParameter("id"));
				ps_u.execute();
			}
		}

		out.print("<script>alert(\"更新成绩成功!\");window.opener = null; window.close();</script>");
	}

	ResultSet rs = stmt.executeQuery("select * from xs;");   // Put here to query data in case the RS is closed.
%>

<!DOCTYPE html>
<html>
<head>
	<title>学生成绩管理</title>
	<link rel="stylesheet" href="../stylesheet/tableCSS.css" />
	<script>
		function clearBlanks()
		{
			let eles = document.getElementsByClassName("scoreF");
			for (let i = eles.length - 1; i >= 0; i--) {
				eles[i].value = "";
			}
		}
	</script>
</head>
<body>
	<h1>学生成绩</h1>
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
			<td>成绩</td>
			<%
				rs.beforeFirst();
				while (rs.next())
				{
					String score = null;
					try
					{
						ps.setString(1, rs.getString("stuid"));
						ResultSet rs1 = ps.executeQuery();
						rs1.next();
						score = rs1.getString("score");
					}
					catch (SQLException sqle)
					{
						score = "";
					}

					if (score.equals("-1"))
					{
						score = "";
					}

					%><td>
						<input type="text" name='score_<%= rs.getString("stuid") %>' id='score_<%= rs.getString("stuid") %>' value="<%= score %>" style="width : 40px;" class="scoreF">
					</td><%
				}
			%>
		</tr>
	</table><br />
	<input type="hidden" name="update" value="1" />
	<input type="submit" value="保存">
	<input type="reset" value="恢复">
	<input type="button" value="清空" onclick="clearBlanks();">
	</form>
</body>
</html>