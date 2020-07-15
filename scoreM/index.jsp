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
    <title>成绩管理</title>
    <%@ include file="../WEB-INF/commonScripts.jsp" %>
    <link rel="stylesheet" href="../stylesheet/tableCSS.css"/>
    <script>
        function openDialog(url) {
            window.open(url, "info", "width=670 height=270 left=300 top=50");
        }

        $(() => {
            $.getJSON("../api/courseList", (e) => {
                let array = e.info;

                for (let i = 0; i < e.info.length; i++) {
                    let ele = document.createElement("tr");

                    updChild(ele, array[i].id);
                    updChild(ele, array[i].name);
                    updChild(ele, array[i].term);
                    updChild(ele, array[i].courseHours);
                    updChild(ele, array[i].credits);

                    let tr = document.createElement("td");
                    let html = `<a href='javascript:void(0);'
                                                onclick='openDialog("info?id=%%");'>编辑</a>`;

                    while (html.indexOf("%%") !== -1) {
                        html = html.replace("%%", array[i].id);
                    }

                    tr.innerHTML = html;

                    ele.appendChild(tr);

                    $("#information")[0].appendChild(ele);
                }
            })
        });
    </script>
</head>
<body class="container">
<h1>课程成绩管理</h1>
<form action="" method="post">
    <br />
    <div class="table-responsive-lg">
        <table class="table table-collapse table-striped table-hover">
            <tbody id="information">
                <tr>
                    <th>课程号</th>
                    <th>课程名</th>
                    <th>开课学期</th>
                    <th>学时</th>
                    <th>学分</th>
                    <th>成绩编辑</th>
                </tr>
            </tbody>
        </table>
    </div>
    <br/>
    <input type="button" id="returnButt" onclick='location.href="../"' value="返回">
</form>
</body>
</html>