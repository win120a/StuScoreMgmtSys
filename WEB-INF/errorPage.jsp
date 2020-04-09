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