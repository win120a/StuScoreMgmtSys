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

<%@ page contentType="text/html; charset=utf-8" errorPage="WEB-INF/errorPage.jsp" %>

<!DOCTYPE html>
<html>
<head>
    <title>Welcome</title>

    <script src="scripts/ua.js"></script>

    <%@ include file="WEB-INF/commonScripts.jsp" %>

    <style>
        /*  Undo Bootstrap style. (According to Firefox 79)  */
        h6 {
            text-align: center;
            margin: 0;
            border-top: 1px #424242 solid;
            display: block;
            font-size: 0.67em;
            font-weight: bold;
            margin-block-start: 2.33em;
            margin-block-end: 2.33em;
            font-family: unset;
        }

        h1 {
            display: block;
            font-size: 2em;
            font-weight: bold;
            margin-block-start: .67em;
            margin-block-end: .67em;
            text-align: center;
            font-family: unset;
        }

        h2 {
            display: block;
            font-size: 1.5em;
            font-weight: bold;
            margin-block-start: .83em;
            margin-block-end: .83em;
            text-align: center;
            font-family: unset;
        }

        p {
            font-family: unset;
        }
    </style>
</head>
<body style="text-align: center;">
    <h2>欢迎使用学生成绩管理系统！</h2>
    <p>请在左边菜单栏中选择一个项目继续……</p>
</body>
</html>