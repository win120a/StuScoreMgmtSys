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

<%@ page contentType="text/html; charset=utf-8" %>
<%@ include file="dbConn.jsp" %>

<%
	getParameters(request);

	String install = request.getParameter("install");

	if (install != null && install.equals("1"))
	{
		saveConfigurations(application, true);

		stmt.execute("create database if not exists " + request.getParameter("db") + ";");

		stmt.execute("use " + request.getParameter("db"));

		stmt.execute("CREATE TABLE `kc` (`courseID` int(11) NOT NULL COMMENT '课程号',`courseName` varchar(50) CHARACTER SET utf8 NOT NULL COMMENT '课程名',`term` varchar(15) NOT NULL COMMENT '开课学期',`courseHours` int(11) NOT NULL COMMENT '学时',`credits` int(11) NOT NULL COMMENT '学分', PRIMARY KEY (`courseID`)) ENGINE=InnoDB DEFAULT CHARSET=utf8 ;");

		stmt.execute("CREATE TABLE `xs` (`stuid` varchar(10) CHARACTER SET utf8 NOT NULL COMMENT '学生编号',`name` varchar(7) CHARACTER SET utf8 NOT NULL COMMENT '姓名',`major` varchar(10) CHARACTER SET utf8 NOT NULL COMMENT '专业名',`gender` tinyint(1) NOT NULL COMMENT '性别',`birthdate` date NOT NULL COMMENT '出生日期',`totalCredits` int(11) DEFAULT '0' COMMENT '总学分',`photo` mediumblob COMMENT '照片',`remark` text CHARACTER SET utf8 COMMENT '备注',PRIMARY KEY (`stuid`),CONSTRAINT `xs_chk_1` CHECK ((`gender` in (0,1)))) ENGINE=InnoDB DEFAULT CHARSET=utf8 ;");

		stmt.execute("CREATE TABLE `xs_kc` (`stuid` varchar(10) CHARACTER SET utf8 NOT NULL COMMENT '学号',`courseid` int(11) NOT NULL COMMENT '课程编号',`score` int(11) NOT NULL COMMENT '成绩',`credit` int(11) NOT NULL COMMENT '学分',PRIMARY KEY (`stuid`,`courseid`),KEY `courseid` (`courseid`),CONSTRAINT `xs_kc_ibfk_1` FOREIGN KEY (`stuid`) REFERENCES `xs` (`stuid`) ON DELETE RESTRICT ON UPDATE RESTRICT,CONSTRAINT `xs_kc_ibfk_2` FOREIGN KEY (`courseid`) REFERENCES `kc` (`courseID`) ON DELETE RESTRICT ON UPDATE RESTRICT) ENGINE=InnoDB DEFAULT CHARSET=utf8 ;");

		out.write("<script>alert(\"安装成功\");");
		out.write("location.href = \"index.jsp\"; </script>");
	}
%>

<!DOCTYPE html>
<html>
<head>
	<title>系统安装</title>
</head>
<body style="text-align: center;">
	<% if (true | !configured) { %>

	<h1>系统安装</h1><br />
	<form action="" method="post">
		数据库驱动：<input type="text" name="driver" value="<%= driver %>"><br />
		数据库服务器：<input type="text" name="serverAddr" value="<%= serverAddr %>"><br />
		数据库：<input type="text" name="db" value="<%= db %>"><br />
		用户名：<input type="text" name="userName" value="<%= userName %>"><br />
		密  码：<input type="password" name="password" value="<%= password %>"><br />
		服务器时区：<input type="text" name="serverTimeZone" value="<%= serverTimeZone %>"><br />
		<input type="hidden" name="install" value="1"><br />
		<input type="submit" value="安装">
	</form>

	<%
	} 
	else {
		%>
			<h1>系统已安装！</h1>
		<%
	}
	%>
</body>
</html>