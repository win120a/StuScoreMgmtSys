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
import java.io.*;
import java.sql.*;
import java.util.*;

import ac.adproj.scms.dao.*;

public class StudentPhotoServlet extends HttpServlet
{
	private static final String QUERY_SQL = "select photo as p from xs where stuid=?;";

	@Override
	public void service(HttpServletRequest request, HttpServletResponse response)
	{
		String id = request.getParameter("id");

		try (DBDao daoO = InitServlet.daoO)
		{
			/*     Get the photo file    */
			Connection conn = daoO.getConnection();

			PreparedStatement ps = conn.prepareStatement(QUERY_SQL);

			ps.setString(1, id);

			System.out.println(ps.toString());

			ResultSet resSet = ps.executeQuery();

			resSet.next();

			Blob photoO = resSet.getBlob("p");

			// FileInputStream fis = new FileInputStream();

			/*    Send Response     */

			if (photoO == null)
			{
				ServletContext ctx = getServletContext();
				String rPath = ctx.getRealPath("/images/none.png");
				FileInputStream photoS = new FileInputStream(new File(rPath));
				byte[] buffer = photoS.readAllBytes();
				response.getOutputStream().write(buffer);
			}
			else
			{	
				InputStream photoS = photoO.getBinaryStream();
				byte[] buffer = photoS.readAllBytes();
				response.setContentType("image/png");
				response.getOutputStream().write(buffer);
			}
		}
		catch (SQLException | IOException e)
		{
			e.printStackTrace();
		}
	}
}