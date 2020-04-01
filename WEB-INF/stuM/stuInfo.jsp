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

<%@ include file="../dbConn.jsp" %>
<%@ include file="../types.jsp" %>

<%@ page import="ac.adproj.scms.entity.Student" %>
<%@ page import="ac.adproj.scms.entity.GenderEnum" %>

<%--
    stuid name major gender birthdate totalCredits photo remark
--%>

<script>
    function loadVars() {}
</script>

<%
    Object stuObject = request.getAttribute("studentObject");
    Student student = null;

    if (stuObject instanceof Student) {
        student = (Student) stuObject;
    }

    String type = request.getParameter("type");

    if (TYPE_MODIFY.equals(type))
    {
        int gender = student.getGender() == GenderEnum.MALE ? 1 : 0;
        %>
        <script>
            // M - 1   F - 0
            function loadVars()
            {
                document.getElementById("name").value = '<%= student.getName() %>';
                document.getElementById("id").value = '<%= student.getId() %>';
                document.getElementById("id").disabled = true;
                document.getElementById("dob").value = '<%= student.getDob() %>';
                document.getElementById("major").value = '<%= student.getMajor() %>';
                <%
                    String remark = student.getRemark();
                    remark = remark == null ? "" : remark;
                %>

                document.getElementById("remark").value = '<%= remark %>';

                if(<%= gender %> == 1) {
                    document.getElementById("genderM").checked = true;
                } else {
                    document.getElementById("genderF").checked = true;
                }
            }
        </script><%
    }
%>

<!DOCTYPE html>
<html>
<head>
    <title>学生信息</title>
    <style type="text/css">
        td
        {
            padding-right : 30px;
        }

        table.paneLeft {
            width : 80%;
        }

        .paneLeft, .paneRight {
            float : left;
        }

        .photo
        {
            width: 70px;
            height : 95px;
        }

        #remarkLabel::before
        {
            content : "";
            height : 0;
            line-height : 0;
            visibility: hidden;
            clear : both;
            display: block;
        }

        #remark {
            width : 100%;
            height : 350px;
        }

        #remarkLabel {
            display: block;
            text-align: center;
        }
    </style>
    <meta charset="utf-8">
</head>
<body style="text-align : center;" onload="loadVars();">
    <form action="info" method="post" enctype="multipart/form-data">
        <fieldset>
            <legend>学生信息</legend>
            
            <table class="paneLeft">
                <tr>
                    <td>
                        <label for="name">姓名：</label>
                        <input type="text" name="name" id="name" required>&nbsp;
                    </td>
                    <td>
                        <label for="id">学号：</label>
                        <input type="text" name="id" id="id" required><br />
                    </td>
                </tr>

                <tr>
                    <td>
                        <label>性别：</label>
                        <input type="radio" name="gender" id="genderM" value="1" required>
                        <label for="genderM">男</label>
                        <input type="radio" name="gender" id="genderF" value="0" required>
                        <label for="genderF">女</label>
                    </td>

                    <td>
                        <label for="dob">生日：</label>
                        <input type="date" name="dob" id="dob" required>
                    </td>
                </tr>

                <tr>
                    <td>
                        <label for="major">专业：</label>
                        <input type="text" name="major" id="major" required>&nbsp;
                    </td>

                    <td>
                        <label for="major">头像：</label>
                        <input type="file" name="headSet" id="headSet" style="width : 150px" accept="image/*">&nbsp;
                    </td>
                </tr>
            </table>

            <div class="paneRight">
                <img src='../stuPhoto.png?id=${param.id}' class="photo" title="当前头像" />
            </div>

            <label for="remark" id="remarkLabel">备注：</label><br />
            <textarea name="remark" id="remark"></textarea>

            <%
                if (type.equals(TYPE_ADD))
                { %>
                    <input type="hidden" name="add" value="1"><br /><br /><%
                }
                else if(type.equals(TYPE_MODIFY))
                { %>
                    <input type="hidden" name="id" value='${param.id}'>
                    <input type="hidden" name="modify" value="1"><br /><br /><%
                }
            %>

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