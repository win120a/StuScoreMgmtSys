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

<%@ page contentType="text/html; charset=utf-8" errorPage="../errorPage.jsp" %>
<%@ page import="ac.adproj.scms.dao.StudentScoreDAO, ac.adproj.scms.entity.Entity" %>
<%@ page import="ac.adproj.scms.entity.StudentScore" %>
<%@ page import="java.util.Set" %>
<%@ taglib prefix="scms" uri="http://tags.scms.projs.ac.net" %>
<%@ include file="../types.jsp" %>

<%
    @SuppressWarnings("unchecked")
    Set<Entity> scoreSet = (Set<Entity>) request.getAttribute("entitySet");
    pageContext.setAttribute("scoreSet", scoreSet);
    pageContext.setAttribute("classname", getClass().getName(), PageContext.PAGE_SCOPE);
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
<form action="info" method="post">
    <table class="T">
        <tr>
            <td>姓名</td>
            <scms:entityPropInScore content="name" type="student">
                ${pageScope.current}
            </scms:entityPropInScore>
        </tr>

        <tr>
            <td>学号</td>
            <scms:entityPropInScore content="id" type="student">
                ${pageScope.current}
            </scms:entityPropInScore>
        </tr>

        <tr>
            <td>成绩</td>
            <%
                for (Entity e : scoreSet) {
                    int scoreNumber = ((StudentScore) e).getScore();
                    String score = scoreNumber == StudentScoreDAO.EMPTY_SCORE_VALUE ? "" : Integer.toString(scoreNumber);
            %>
            <td>
                <input type="text" name='score_<%= ((StudentScore)e).getStudent().getId() %>'
                       id='score_<%= ((StudentScore)e).getStudent().getId() %>'
                       value="<%= score %>" style="width : 40px;" class="scoreF">
            </td>
            <%
                }
            %>
        </tr>
    </table>
    <br/>
    <input type="hidden" name="type" value="update"/>
    <input type="hidden" name="id" value='<%= request.getParameter("id") %>'/>
    <input type="submit" value="保存">
    <input type="reset" value="恢复">
    <input type="button" value="清空" onclick="clearBlanks();">
</form>
</body>
</html>