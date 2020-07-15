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

<!DOCTYPE html>
<html>
<head>
    <title>学生管理</title>
    <%@ include file="../WEB-INF/mgmtCommon.jsp" %>
    <script>
        function openDialog(url) {
            if (!isMobileDevice()) {
                window.top.frameBox("学生信息", url);
            } else {
                window.open(url, "info", "width=690 height=520 left=300 top=50");
            }
        }

        let loadStudentList = () => {
            removeItemsInList();
            $.getJSON("../api/studentList", (e) => {
                let array = e.info;

                for (let i = 0; i < e.info.length; i++) {
                    let ele = document.createElement("tr");

                    updChild(ele, array[i].id);
                    updChild(ele, array[i].name);
                    updChild(ele, array[i].gender);
                    updChild(ele, array[i].major);
                    updChild(ele, array[i].dob);
                    updChild(ele, array[i].totalCredits);
                    updChild(ele, array[i].remark);

                    let tr = document.createElement("td");
                    let html = `<a href='javascript:void(0);'
                                                onclick='if (confirm("确定删除这名学生吗？（学号：%%）")) deleteStudent([%%])'>删除</a>
                                            &nbsp;&nbsp;<a href='javascript:void(0);'
                                                onclick='openDialog("<%= application.getContextPath() %>/stuM/info?type=modify&id=%%");'>编辑</a>`;

                    while (html.indexOf("%%") !== -1) {
                        html = html.replace("%%", array[i].id);
                    }

                    tr.innerHTML = html;

                    ele.appendChild(tr);

                    $("#information")[0].appendChild(ele);
                }
            })
        };

        let deleteStudent = (id) => {
            let xhr = new XMLHttpRequest();

            xhr.open("DELETE", "../api/studentList");
            xhr.setRequestHeader("Content-type", "application/json");

            let str = id.toString();

            xhr.onload = () => {
                let obj = JSON.parse(xhr.responseText);

                if (parseInt(obj.status) === -1) {
                    alertBar("因输入了成绩，删除失败！学生号：" + obj.failedID.toString(), true, 3000);
                } else {
                    alertBar("删除成功。", true, 3000);
                }

                loadStudentList();
            };

            xhr.send("{id : [" + str + "]}");
        };

        let removeItemsInList = () => {
            let infoO = $("#information");

            let children = infoO.children();

            for (let i = 0; i < children.length; i++) {

                let ele = $(children[i]);

                if (children[i].id.toString() === "name") {
                    continue;
                }

                ele.remove();
            }
        };

        $(() => loadStudentList());
    </script>
</head>
<body class="container" style="text-align: center;">
<h1>学生管理</h1>

<br/>
<div class="table-responsive-lg">
    <table class="table table-collapse table-striped table-hover">
        <tbody id="information">
        <tr id="name">
            <th>学号</th>
            <th>姓名</th>
            <th>性别</th>
            <th>专业</th>
            <th>生日</th>
            <th>总学分</th>
            <th>备注</th>
            <th>操作</th>
        </tr>
        </tbody>
    </table>
</div>
<br/>
<input type="button" class="btn btn-primary" id="addStudent"
       onclick='openDialog("<%= application.getContextPath() %>/stuM/info?type=add");' value="添加">
<input type="button" class="btn btn-secondary" id="returnButt" onclick='location.href="../"' value="返回">
</body>
</html>