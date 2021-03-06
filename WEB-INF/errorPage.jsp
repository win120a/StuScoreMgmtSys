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

<%@ page contentType="text/html; charset=utf-8" isErrorPage="true" %>

<!DOCTYPE html>
<html>
<head>
    <title>程序出错啦！</title>
    <%@ include file="commonScripts.jsp" %>
</head>
<body style="text-align : center;">
<%
    if (exception.getClass().getName().contains("CommunicationsException")) { %>
<h1>服务器连接失败!</h1>
<p>请稍后重试。</p><br/>
<% } %>

<h1>程序出现错误。</h1>
<p>错误类：<%= exception.getClass().getName() %>
</p>
<p>错误信息：<%= exception.getMessage() %>
</p>
<p>
    调用堆栈：
    <%
        for (StackTraceElement ste : exception.getStackTrace()) {
            out.write(ste.toString() + "<br />");
        }
    %>
</p>
</body>
</html>