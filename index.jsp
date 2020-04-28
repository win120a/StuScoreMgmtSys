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
    <title>学生成绩管理系统</title>

    <script src="scripts/ua.js"></script>
    <script>
        if (isMobileDevice() && getTerminalType() != TERMINAL_IPAD) {
            location.href = "index.mobi.jsp";
        }
    </script>

    <%@ include file="WEB-INF/commonScripts.jsp" %>
    <style>
        /* Originally .container */
        body {
            display: flex;
            flex-direction: column;
            width: 99.7%;
            height: 100%;
            margin: 0;
            padding: 0;
        }

        .header {
            border-bottom : 1px #424242 solid;
        }

        .panel {
            display: flex;
            flex-direction: row;
            justify-content: space-around;
            width: 100%;
        }

        .left-panel {
            margin-left : 10px;
            margin-right : 10px;
        }

        .right-panel {
            width: 91%;
            border-left : 1px #424242 solid;
            padding-left: 10px;
        }

        .right-frame {
            width: 100%;
            height : 100%;
            border : none;
        }

        .left-panel a {
            color : black;
            text-decoration: none;
        }

        .left-panel a:hover {
            color : orange;
            text-decoration: underline;
        }

        h6 {
            margin: 0;
            border-top : 1px #424242 solid;
        }
    </style>

    <script>
        $().ready(() => $(".panel").css("height", window.screen.availHeight / 2 + 50 + "px"));
    </script>
</head>
<body style="text-align: center;">
    <div class="header">
        <h1>系统主页</h1>
    </div>

    <div class="panel">
        <div class="left-panel">
            <form action="dispatcher" method="post">
                <a href="javascript:void(0);" onclick="$('#right-frame')[0].src = 'stuM/index.jsp';">学生管理</a><br />
                <a href="javascript:void(0);" onclick="$('#right-frame')[0].src = 'subM/index.jsp';">学科管理</a><br />
                <a href="javascript:void(0);" onclick="$('#right-frame')[0].src = 'scoreM/index.jsp';">成绩管理</a><br />
                <br />
                <input type="submit" name="test" value="测试">
            </form>
        </div>

        <div class="right-panel">
            <iframe id="right-frame" class="right-frame" src="welcome.jsp">
            </iframe>
        </div>
    </div>
</body>
</html>