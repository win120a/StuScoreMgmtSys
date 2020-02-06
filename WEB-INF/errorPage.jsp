<%@ page contentType="text/html; charset=utf-8" isErrorPage="true" %>

<!DOCTYPE html>
<html>
<head>
	<title>程序出错啦！</title>
</head>
<body style="text-align : center;">
	<h1>程序出现错误。</h1>
	<p>错误类：<%= exception.getClass().getName() %></h2>
	<p>错误信息：<%= exception.getMessage() %></p>
	<p>
		调用堆栈：
		<%
			for (StackTraceElement ste : exception.getStackTrace())
			{
				out.write(ste.toString() + "<br />");
			}
		%>
	</p>
</body>
</html>