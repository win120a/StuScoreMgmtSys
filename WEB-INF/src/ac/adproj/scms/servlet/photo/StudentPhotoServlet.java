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

package ac.adproj.scms.servlet.photo;

import ac.adproj.scms.servlet.ServletProcessingException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;

/**
 * Servlet that transfer the photo array to the client.
 */
public class StudentPhotoServlet extends HttpServlet {
    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");

        try (PhotoService ps = PhotoServiceFactory.getPhotoService(request);
             OutputStream out = response.getOutputStream()) {
            byte[] photoB = ps.getPhoto(id);
            response.setContentType("image/png");
            out.write(photoB);
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            throw new ServletProcessingException(e);
        } catch (Exception e) {
            throw new ServletProcessingException(e);
        }
    }
}