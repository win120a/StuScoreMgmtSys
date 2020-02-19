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

package ac.adproj.scms.servlet.stuM;

import javax.servlet.*;
import javax.servlet.http.*;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.sql.*;
import java.util.*;

import javax.sql.rowset.serial.*;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import ac.adproj.scms.dao.*;
import ac.adproj.scms.servlet.InitServlet;
import ac.adproj.scms.servlet.ServletProcessingException;

/**
	The student info's processing Servlet. (a.k.a /stuM/infoProc)

	@author Andy Cheung
*/
public class StuInfoProcServlet extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		Map<String, DataWrap> formContents = getFormContents(request);
		
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");

		try (DBDao daoO = InitServlet.daoO) {
			Writer out = response.getWriter();
			Connection conn = daoO.getConnection();
			Statement stmt = conn.createStatement();

			request.setCharacterEncoding("utf-8");
			String addP = getStringParameter("add", formContents);
			String modP = getStringParameter("modify", formContents);
			String type = getStringParameter("type", formContents);

			if (addP != null && addP.equals("1")) {
				// id name major gender dob totalCredits(=0) photo remark
				PreparedStatement ps_a = conn.prepareStatement("insert into xs values (?, ?, ?, ?, ?, ?, NULL, ?);");

				ps_a.setString(1, getStringParameter("id", formContents));
				ps_a.setString(2, getStringParameter("name", formContents));
				ps_a.setString(3, getStringParameter("major", formContents));
				ps_a.setString(4, getStringParameter("gender", formContents));
				ps_a.setString(5, getStringParameter("dob", formContents));
				ps_a.setString(6, "0");

				ps_a.setString(7, getStringParameter("remark", formContents));

				// System.err.println(ps_a.toString());

				ps_a.execute();

				DataWrap pictW = formContents.get("headSet");

				if (pictW != null && ((byte[]) pictW.object).length != 0)
				{
					updatePhoto(conn, getStringParameter("id", formContents), pictW);
				}

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

				ps.setString(1, getStringParameter("name", formContents));
				ps.setString(2, getStringParameter("major", formContents));
				ps.setString(3, getStringParameter("gender", formContents));
				ps.setString(4, getStringParameter("dob", formContents));
				ps.setString(5, getStringParameter("remark", formContents));
				ps.setString(6, getStringParameter("id", formContents));

				ps.execute();

				// byte[] pictB = getUploadedFile(request, "headSet", daoO);
				DataWrap pictW = formContents.get("headSet");
				
				if (pictW != null && ((byte[]) pictW.object).length != 0)
				{
					updatePhoto(conn, getStringParameter("id", formContents), pictW);
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
			throw new ServletProcessingException(e);
			// response.sendRedirect("/META-INF/errorPage.jsp");
		}
	}

	private void updatePhoto(Connection conn, String id, DataWrap pictW) throws SQLException
	{
		PreparedStatement ps_p = conn.prepareStatement("update xs set photo=? where stuid=?;");
		if (pictW != null && (!pictW.isFormField()) && ((byte[]) pictW.object).length != 0)
		{
			byte[] pictB = (byte[]) pictW.object;
			ps_p.setBlob(1, new SerialBlob(pictB));
			ps_p.setString(2, id);
			ps_p.execute();
		}
		else
		{
			throw new IllegalArgumentException();
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

	/**
		A simple Data Wrapper class to wrap a form entry.

		@author Andy Cheung
	*/
	private static class DataWrap {
		private Object object;
		private boolean formField;

		public Object getObject() {
			return object;
		}

		public boolean isFormField() {
			return formField;
		}

		public DataWrap(Object object, boolean formField) {
			super();
			this.object = object;
			this.formField = formField;
		}
	}
	
	/**
		Get a form field value in the pre-generated formContents from getFormContents(HttpServletRequest);

		@author Andy Cheung
	*/
	private String getStringParameter(String key, Map<String, DataWrap> formContents)
	{
		if (formContents.get(key) == null)
			return null;
		
		if (!formContents.get(key).formField)
			throw new IllegalArgumentException();
		
		return (String) formContents.get(key).getObject();
	}

	/**
		Core method to gather the multipart/form-data form's data.

		@author Andy Cheung
	*/
	private Map<String, DataWrap> getFormContents(HttpServletRequest request) throws UnsupportedEncodingException {
		HashMap<String, DataWrap> contents = new HashMap<String, DataWrap>();
		
		if(!ServletFileUpload.isMultipartContent(request))
			throw new IllegalArgumentException();

		// Create a factory for disk-based file items
		DiskFileItemFactory factory = new DiskFileItemFactory();

		// Configure a repository (to ensure a secure temp location is used)
		ServletContext servletContext = this.getServletConfig().getServletContext();
		File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
		factory.setRepository(repository);

		// Create a new file upload handler
		ServletFileUpload upload = new ServletFileUpload(factory);

		// Parse the request
		try {
			List<FileItem> items = upload.parseRequest(request);

			for (FileItem fi : items) {
				if (fi.isFormField()) {
					DataWrap dw = new DataWrap(fi.getString("utf-8"), true);
					contents.put(fi.getFieldName(), dw);
				}
				else
				{
					DataWrap dw = new DataWrap(fi.get(), false);
					contents.put(fi.getFieldName(), dw);
				}
			}
		} catch (FileUploadException e) {
			e.printStackTrace();
			throw new ServletProcessingException();
		}

		return contents;
	}
}