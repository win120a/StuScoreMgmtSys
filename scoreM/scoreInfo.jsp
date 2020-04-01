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
<%@ page import="ac.adproj.scms.dao.DBDao, ac.adproj.scms.servlet.InitServlet" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.PreparedStatement" %>
<%@ page import="java.sql.ResultSet" %>

<%@ include file="../WEB-INF/types.jsp" %>

<%
    DBDao daoO = InitServlet.daoO;
    Connection conn = daoO.getConnection();
    PreparedStatement ps = conn.prepareStatement("select score from xs_kc where stuid=? and courseID=?;");
    ps.setString(2, request.getParameter("id"));
    ResultSet rs = daoO.query("select * from xs;");   // Put here to query data in case the RS is closed.
%>

<!DOCTYPE html>
<html>
<head>
    <title>学生成绩管理</title>
    <link rel="stylesheet" href="../stylesheet/tableCSS.css"/>
    <script>
        function clearBlanks() {
            let eles = document.getElementsByClassName("scoreF");
            for (let i = eles.length - 1; i >= 0; i--) {
                eles[i].value = "";
            }
        }
    </script>
</head>
<body>
<h1>学生成绩</h1>
<form action="infoProc" method="post">
    <table class="T">  <%-- JSP Scriptlet that uses SQL Commands --%>
        <%--
            stuidname major gender birthdate totalCredits photo remark
        --%>
        <tr>
            <td>姓名</td>
            <%
                while (rs.next()) {
            %>
            <td><%= rs.getString("name") %>
            </td>
            <%
                }
            %>
        </tr>

        <tr>
            <td>学号</td>
            <%
                rs.beforeFirst();
                while (rs.next()) {
            %>
            <td><%= rs.getString("stuid") %>
            </td>
            <%
                }
            %>
        </tr>

        <tr>
            <td>成绩</td>
            <%
                rs.beforeFirst();
                while (rs.next()) {
                    String score = null;
                    ps.setString(1, rs.getString("stuid"));
                    ResultSet rs1 = ps.executeQuery();
                    boolean hasScore = rs1.next();
                    score = hasScore ? rs1.getString("score") : "";

            %>
            <td>
                <input type="text" name='score_<%= rs.getString("stuid") %>' id='score_<%= rs.getString("stuid") %>'
                       value="<%= score %>" style="width : 40px;" class="scoreF">
            </td>
            <%
                }
            %>
        </tr>
    </table>
    <br/>
    <input type="hidden" name="update" value="1"/>
    <input type="hidden" name="id" value='<%= request.getParameter("id") %>'/>
    <input type="submit" value="保存">
    <input type="reset" value="恢复">
    <input type="button" value="清空" onclick="clearBlanks();">
</form>
</body>
</html>