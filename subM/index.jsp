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
<%@ page import="java.util.Set" %>
<%@ page import="ac.adproj.scms.entity.Course" %>
<%@ page import="ac.adproj.scms.dao.CourseDAO" %>

<%@ taglib prefix="scms" uri="http://tags.scms.projs.ac.net" %>

<%@ include file="../WEB-INF/types.jsp" %>

<%
    Set<Course> entitySet = CourseDAO.getCourseObjectSet();
    pageContext.setAttribute("entitySet", entitySet);
    pageContext.setAttribute("classname", getClass().getName(), PageContext.PAGE_SCOPE);
%>

<!DOCTYPE html>
<html>
<head>
    <title>课程管理</title>
    <%@ include file="../WEB-INF/mgmtCommon.jsp" %>

    <script>
        function openDialog(url) {
            window.open(url, "info", "width=670 height=270 left=300 top=50");
        }
    </script>
</head>
<body>
<h1>课程管理</h1>
<form action="list" method="post">
    <table class="T">  <%-- JSP Scriptlet that uses SQL Commands --%>
        <tr>
            <td>课程号</td>
            <scms:dataRows content="id" type="course">
                ${pageScope.current}
            </scms:dataRows>
        </tr>

        <tr>
            <td>课程名</td>
            <scms:dataRows content="name" type="course">
                ${pageScope.current}
            </scms:dataRows>
        </tr>

        <tr>
            <td>开课学期</td>
            <scms:dataRows content="term" type="course">
                ${pageScope.current}
            </scms:dataRows>
        </tr>

        <tr>
            <td>学时</td>
            <scms:dataRows content="courseHours" type="course">
                ${pageScope.current}
            </scms:dataRows>
        </tr>

        <tr>
            <td>学分</td>
            <scms:dataRows content="credits" type="course">
                ${pageScope.current}
            </scms:dataRows>
        </tr>

        <tr>
            <td>操作</td>
            <scms:dataRows content="id" type="course">
                删除?
                <input name='${pageScope.current}' type="checkbox" class="del"><br/>
                <a href='javascript:void(0);'
                   onclick='openDialog("info?type=modify&id=${pageScope.current}");'>编辑</a>
            </scms:dataRows>
        </tr>
    </table>
    <br/>
    <input type="button" id="addStudent" onclick='openDialog("info?type=add");' value="添加">
    <input type="submit" name="del" onclick="return checkSelection();" value="删除">
    <input type="button" id="returnButt" onclick='location.href="../"' value="返回">
</form>
</body>
</html>