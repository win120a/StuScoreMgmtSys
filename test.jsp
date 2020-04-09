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

<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="WEB-INF/errorPage.jsp" %>
<%@ page import="ac.adproj.scms.dao.StudentDao" %>
<%@ page import="java.util.Date" %>

<%--
    Class.forName("c");  // To Test error page.
--%>

<html>
<head>
    <title>First JSP App</title>
    <!-- Thin table border, in pure CSS code. -->
    <style>
        table.T {
            border-collapse: collapse;
        }

        .T tr td {
            border: black 0.5px solid;
            text-align: center;
            width: 80px;
            font-size: 0.8em;
        }
    </style>
</head>
<body>
<h1>Test.</h1>

<%@ include file="WEB-INF/dbConn.jsp" %>
<%
    ResultSet rs = stmt.executeQuery("select * from xs;");
%>

<p><%= new Date().toString() %>
</p> <%-- NO ';' in the expression block --%>

<p>
    <%
        out.println("Another block");
    %>
</p>

<%-- JSP Comment, which the users can't see it in the HTML source code. --%>

<!-- HTML Comment -->

<table class="T">  <%-- JSP Scriptlet. --%>
    <%
        for (int i = 0; i < 5; i++) {
    %>
    <tr><%
        for (int j = 0; j < 5; j++) {
    %>
        <td><%=j %>, <%=i %>
        </td>
        <%
            }%>
    </tr>
    <%
        }
    %>
</table>
<br/>

<table class="T">  <%-- JSP Scriptlet that uses SQL Commands (not yet) --%>
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
            rs.beforeFirst();
            while (rs.next()) {
        %>
        <td><%= rs.getString("totalcredits") %>
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
</table>

<p><%= StudentDao.getStudentObjectThroughDB("123456") %>
</p>
</body>
</html>