/*
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
*/

package ac.adproj.scms.servlet;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.Writer;
import java.sql.*;
import java.util.*;

import javax.sql.rowset.serial.*;

import ac.adproj.scms.dao.*;

public class StuInfoProcServlet extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");

		try (DBDao daoO = InitServlet.daoO) {
			Writer out = response.getWriter();
			Connection conn = daoO.getConnection();
			Statement stmt = conn.createStatement();

			request.setCharacterEncoding("utf-8");
			String addP = request.getParameter("add");
			String modP = request.getParameter("modify");
			String type = request.getParameter("type");

			if (addP != null && addP.equals("1")) {
				StringBuilder builder = new StringBuilder();

				builder.append("insert into xs values (\"");
				builder.append(request.getParameter("id"));

				builder.append("\", \"");

				String name = request.getParameter("name");

				builder.append(name);

				builder.append("\", \"");
				builder.append(request.getParameter("major"));
				builder.append("\", \"");
				builder.append(request.getParameter("gender"));
				builder.append("\", \"");
				builder.append(request.getParameter("dob"));
				builder.append("\", ");
				builder.append("0");
				builder.append(", ");
				builder.append("NULL");
				builder.append(", \"");
				builder.append(request.getParameter("remark"));
				builder.append("\"");
				builder.append(");");

				stmt.execute(builder.toString());

				out.write("<script>");

				byte[] b = ("window.alert('添加成功! ');").getBytes();
				out.write(new String(b, "utf-8"));

				out.write("opener.location.reload();");
				out.write("window.opener = null;");
				out.write("window.close();");
				out.write("</script>");
			}

			if (modP != null && modP.equals("1")) {
				PreparedStatement ps = conn.prepareStatement(
						"update xs set name=?, major=?, gender=?, birthdate=?, " + "remark=? where stuid=?;");

				PreparedStatement ps_p = conn.prepareStatement("update xs set photo=? where stuid=?;");

				ps.setString(1, request.getParameter("name"));
				ps.setString(2, request.getParameter("major"));
				ps.setString(3, request.getParameter("gender"));
				ps.setString(4, request.getParameter("dob"));
				ps.setString(5, request.getParameter("remark"));
				ps.setString(6, request.getParameter("id"));

				ps.execute();

				byte[] pictB = getUploadedFile(request, "headSet", daoO);

				if (pictB[0] != -1)
				{
					ps_p.setBlob(1, new SerialBlob(pictB));
					ps_p.setString(2, request.getParameter("id"));
					ps_p.execute();
				}

				out.write("<script>");

				byte[] b = ("window.alert('修改成功! ');").getBytes();
				out.write(new String(b, "utf-8"));

				out.write("opener.location.reload();");
				out.write("window.opener = null;");
				out.write("window.close();");
				out.write("</script>");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			// response.sendRedirect("/META-INF/errorPage.jsp");
		}
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setHeader("Allow", "GET, HEAD, POST, OPTIONS");
		request.setCharacterEncoding("utf-8");
		response.setBufferSize(8192);
		response.setContentType("text/html; charset=utf-8");

		byte[] b = ("<p>测试 Test:" + request.getParameter("test") + "</p>").getBytes();

		response.getWriter().print(new String(b, "utf-8"));
	}

	private byte[] getUploadedFile(HttpServletRequest request, String name, DBDao daoO) throws IOException, ServletException
	{
		Part p = request.getPart("name");

		byte[] buffer = (p == null) ? new byte[1] : p.getInputStream().readAllBytes();

		byte n = buffer[0];

		buffer[0] = (p != null) ? n : -1;

		return buffer;
	}
}