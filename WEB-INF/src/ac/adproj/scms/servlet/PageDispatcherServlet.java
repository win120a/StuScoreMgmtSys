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
import java.io.*;

import ac.adproj.scms.servlet.ServletProcessingException;

public class PageDispatcherServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");

        try (Writer out = response.getWriter()) {
            String testParm = request.getParameter("test");

            if (testParm != null)
            {
                forward("/test.jsp", request, response);
                return;
            }

            String subMParm = request.getParameter("subM");

            if (subMParm != null)
            {
                out.write("<script>location.href = \"subM/index.jsp\"</script>");
            }

            String stuMParm = request.getParameter("stuM");

            if (stuMParm != null)
            {
                out.write("<script>location.href = \"stuM/index.jsp\"</script>");
            }

            String scoreMParm = request.getParameter("scoreM");

            if (scoreMParm != null)
            {
                out.write("<script>location.href = \"scoreM/index.jsp\"</script>");
            }
        }
    }

    private void forward(String url, HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        req.getRequestDispatcher(url).forward(req, res);
    }
}