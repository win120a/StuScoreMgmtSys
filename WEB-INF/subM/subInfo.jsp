<%@ page import="ac.adproj.scms.entity.Course" %><%--
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

<%@ page contentType="text/html; charset=utf-8" errorPage="../errorPage.jsp" %>
<%@ include file="../types.jsp" %>

<script>
    function loadVars() {
    }
</script>

<%
    request.setCharacterEncoding("utf-8");
    Object entityObject = request.getAttribute("entityObject");
    Course course = null;

    if (entityObject instanceof Course) {
        course = (Course) entityObject;
    }

    String type = request.getParameter("type");

    if (TYPE_MODIFY.equals(type)) {
%>
<script>
    // M - 1   F - 0
    function loadVars() {
        document.getElementById("courseName").value = '<%= course.getName() %>';
        document.getElementById("courseID").value = '<%= course.getId() %>';
        document.getElementById("courseID").disabled = true;
        document.getElementById("term").value = '<%= course.getTerm() %>';
        document.getElementById("credits").value = '<%= course.getCredits() %>';
        document.getElementById("courseHours").value = '<%= course.getCourseHours() %>';
    }
</script>
<%
    }
%>

<!DOCTYPE html>
<html>
<head>
    <title>课程信息</title>
    <style type="text/css">
        td {
            padding-right: 50px;
        }
    </style>
    <meta charset="utf-8">
</head>
<body style="text-align : center;" onload="loadVars();">
<form action="info" method="post">
    <fieldset>
        <legend>课程信息</legend>

        <table>
            <tr>
                <td>
                    <label for="courseName">课程名：</label>
                    <input type="text" name="courseName" id="courseName" required>&nbsp;
                </td>
                <td>
                    <label for="courseID">课程号：</label>
                    <input type="text" name="courseID" id="courseID" required><br/>
                </td>
            </tr>

            <tr>
                <td>
                    <label for="term">开课学期：</label>
                    <input type="text" name="term" id="term" required>
                </td>

                <td>
                    <label for="courseHours">学时：</label>
                    <input type="text" name="courseHours" id="courseHours" required>&nbsp;
                </td>
            </tr>
        </table>

        <br/>


        <label for="credits">学分：</label><br/>
        <input type="text" name="credits" id="credits" required>&nbsp;

        <input type="hidden" name="type" value="<%= type %>"><br/><br/>
        <input type="submit" id="submit" value="提交"/>
        <input type="reset" id="reset" value="重置"/>
        <%
            if (type.equals(TYPE_MODIFY)) { %>
                <input type="hidden" name="id" value='${param.id}'><%
        %>
        <script>
            document.getElementById("reset").onclick = function () {
                loadVars();
                return false;
            }
        </script>
        <%
            }
        %>

    </fieldset>
</form>
</body>
</html>