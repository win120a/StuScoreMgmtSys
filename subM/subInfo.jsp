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

<script>
    function loadVars() {}
</script>

<%
    String type = request.getParameter("type");

    request.setCharacterEncoding("utf-8");

    if (TYPE_MODIFY.equals(type))
    {
        ResultSet rs = stmt.executeQuery("select * from kc where courseID=" + request.getParameter("id") + ";");
        rs.next();

        %>
        <script>
            // M - 1   F - 0
            function loadVars()
            {
                document.getElementById("courseName").value = '<%= rs.getString("courseName") %>';
                document.getElementById("courseID").value = '<%= rs.getString("courseID") %>';
                document.getElementById("courseID").disabled = true;
                document.getElementById("term").value = '<%= rs.getString("term") %>';
                document.getElementById("credits").value = '<%= rs.getString("credits") %>';
                document.getElementById("courseHours").value = '<%= rs.getString("courseHours") %>';
            }
        </script><%
    }
%>

<!DOCTYPE html>
<html>
<head>
    <title>课程信息</title>
    <style type="text/css">
        td
        {
            padding-right : 50px;
        }
    </style>
    <meta charset="utf-8">
</head>
<body style="text-align : center;" onload="loadVars();">
    <form action="infoProc" method="post">
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
                        <input type="text" name="courseID" id="courseID" required><br />
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

            <br />
                        

            <label for="credits">学分：</label><br />
            <input type="text" name="credits" id="credits" required>&nbsp;

            <%
                if (type.equals(TYPE_ADD))
                { %>
                    <input type="hidden" name="add" value="1"><br /><br /><%
                }
                else if(type.equals(TYPE_MODIFY))
                { %>
                    <input type="hidden" name="modify" value="1"><br /><br /><%
                }
            %>

            <input type="hidden" name="id" value='<%= request.getParameter("id") %>'>
            <input type="submit" id="submit" value="提交" />
            <input type="reset" id="reset" value="重置" />
                <%
                    if(type.equals(TYPE_MODIFY))
                    {
                        %>
                        <script>
                            document.getElementById("reset").onclick = function()
                            {
                                loadVars();
                                return false;
                            }
                        </script><%
                    }
                %>

        </fieldset>
    </form>
</body>
</html>