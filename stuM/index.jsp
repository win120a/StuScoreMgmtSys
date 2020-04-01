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
<%@ include file="../WEB-INF/dbConn.jsp" %>
<%@ include file="../WEB-INF/types.jsp" %>

<%
    ResultSet rs = stmt.executeQuery("select * from xs;");   // Put here to query data after the delete process.
%>

<!DOCTYPE html>
<html>
<head>
    <title>学生管理</title>
    <%@ include file="../WEB-INF/mgmtCommon.jsp" %>
    <script>
        function openDialog(url) {
            window.open(url, "info", "width=690 height=520 left=300 top=50");
        }
    </script>
</head>
<body>
<h1>学生管理</h1>
<form action="listProc" method="post">
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
            <td>性别</td>
            <%
                rs.beforeFirst();
                while (rs.next()) {
            %>
            <td><%= rs.getInt("gender") == 1 ? "男" : "女" %>
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
            <td>专业</td>
            <%
                rs.beforeFirst();
                while (rs.next()) {
            %>
            <td><%= rs.getString("major") %>
            </td>
            <%
                }
            %>
        </tr>

        <tr>
            <td>生日</td>
            <%
                rs.beforeFirst();
                while (rs.next()) {
            %>
            <td><%= rs.getString("birthdate") %>
            </td>
            <%
                }
            %>
        </tr>

        <tr>
            <td>总学分</td>
            <%
                PreparedStatement creditsPS = conn.prepareStatement("select sum(credit) as c from xs_kc where stuid=? and score >= 60;");

                rs.beforeFirst();
                while (rs.next()) {
                    creditsPS.setString(1, rs.getString("stuid"));
                    ResultSet creditsRS = creditsPS.executeQuery();
                    creditsRS.next();
                    String credits = creditsRS.getString("c");
            %>
            <td><%= credits == null ? "0" : credits %>
            </td>
            <%
                }
            %>
        </tr>

        <tr>
            <td>备注</td>
            <%
                rs.beforeFirst();
                while (rs.next()) {
                    String remark = rs.getString("remark");
            %>
            <td><%= remark == null ? "" : remark %>
            </td>
            <%
                }
            %>
        </tr>

        <tr>
            <td>操作</td>
            <%
                String reqS = "info?type=" + TYPE_MODIFY + "&id=";
                rs.beforeFirst();
                while (rs.next()) {
            %>
            <td>
                <label for='score_<%= rs.getString("stuid") %>'>删除? </label>
                <input name='<%= rs.getString("stuid") %>' type="checkbox" class="del"
                       id='score_<%= rs.getString("stuid") %>'><br/>
                <a href='javascript:void(0);'
                   onclick='openDialog("<%= reqS %><%= rs.getString("stuid")%>");'>编辑</a>
            </td>
            <%
                }
            %>
        </tr>
    </table>
    <br/>
    <input type="button" id="addStudent" onclick='openDialog("info?type=add");' value="添加">
    <input type="submit" name="del" onclick="return checkSelection();" value="删除">
    <input type="button" name="returnButt" onclick='location.href="../"' value="返回">
</form>
</body>
</html>