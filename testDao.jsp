<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="ac.adproj.scms.dao.*, java.sql.*, ac.adproj.scms.servlet.*, java.io.*" %>
<%@ include file="WEB-INF/dbConfig.jsp" %>

<!DOCTYPE html>
<html>
<head>
    <title>Test DAO</title>
</head>
<body>
    <%
        // DBDao daoO = new DBDao(driver, serverAddr, userName, password, serverTimeZone, db);

        DBDao daoO = InitServlet.daoO;

        ResultSet rs = daoO.query("select * from xs;");

        while (rs.next())
        {
            out.write("<p>" + rs.getString("stuid") + "</p>");
        }

        /*
        PreparedStatement ps = daoO.getConnection().prepareStatement("update xs set photo=? where stuid=123456;");

        FileInputStream fis = new FileInputStream("D:\\1.png");

        ps.setBinaryStream(1, fis);

        ps.executeUpdate();
        */

        ServletContext ctx = getServletContext();

        out.write(ctx.getContextPath());
        out.write(ctx.getRealPath("/images/none.png"));

    %>
</body>
</html>