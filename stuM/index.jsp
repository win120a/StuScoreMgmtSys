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
<%@ include file="../WEB-INF/types.jsp" %>
<%@ page import="ac.adproj.scms.dao.StudentDAO" %>
<%@ page import="ac.adproj.scms.entity.Student" %>
<%@ page import="java.util.Set" %>
<%@ taglib prefix="scms" uri="http://tags.scms.projs.ac.net" %>

<%
    Set<Student> entitySet = StudentDAO.getStudentObjectSet();
    pageContext.setAttribute("entitySet", entitySet);
    pageContext.setAttribute("classname", getClass().getName(), PageContext.PAGE_SCOPE);
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

<form action="list" method="post">
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

        <tr>
            <td>操作</td>

            <scms:dataRows content="id" type="student">
                <label for='${pageScope.current}'>删除? </label>
                <input name='${pageScope.current}' type="checkbox" class="del"
                       id='${pageScope.current}'><br/>
                <a href='javascript:void(0);'
                   onclick='openDialog("info?type=modify&id=${pageScope.current}");'>编辑</a>
            </scms:dataRows>
        </tr>
    </table>
    <br/>
    <input type="button" id="addStudent" onclick='openDialog("info?type=add");' value="添加">
    <input type="submit" name="del" onclick="return checkSelection();" value="删除">
    <input type="button" name="returnButt" onclick='location.href="../"' value="返回">
</form>
</body>
</html>