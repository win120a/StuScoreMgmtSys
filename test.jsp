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
<%@ page import="ac.adproj.scms.dao.StudentDAO" %>
<%@ page import="java.util.Date" %>
<%@ page import="ac.adproj.scms.entity.Student" %>
<%@ page import="java.util.Set" %>
<%@ taglib prefix="scms" uri="http://tags.scms.projs.ac.net" %>

<%
    Set<Student> entitySet = StudentDAO.getStudentObjectSet();
    pageContext.setAttribute("entitySet", entitySet);
    pageContext.setAttribute("classname", getClass().getName(), PageContext.PAGE_SCOPE);
%>

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

<p><%= new Date().toString() %></p>

<table class="T">
    <tr>
        <td>姓名</td>
        <scms:dataRows content="name" type="student">
            ${pageScope.current}
        </scms:dataRows>
    </tr>

    <tr>
        <td>性别</td>
        <scms:dataRows content="gender" type="student">
            ${pageScope.current}
        </scms:dataRows>
    </tr>

    <tr>
        <td>学号</td>
        <scms:dataRows content="id" type="student">
            ${pageScope.current}
        </scms:dataRows>
    </tr>

    <tr>
        <td>专业</td>
        <scms:dataRows content="major" type="student">
            ${pageScope.current}
        </scms:dataRows>
    </tr>

    <tr>
        <td>生日</td>
        <scms:dataRows content="dob" type="student">
            ${pageScope.current}
        </scms:dataRows>
    </tr>

    <tr>
        <td>总学分</td>
        <scms:dataRows content="totalCredits" type="student">
            ${pageScope.current}
        </scms:dataRows>
    </tr>

    <tr>
        <td>备注</td>
        <scms:dataRows content="remark" type="student">
            ${pageScope.current}
        </scms:dataRows>
    </tr>
</table>

<p><%= StudentDAO.getStudentObjectThroughDB("123456") %>
</p>
</body>
</html>