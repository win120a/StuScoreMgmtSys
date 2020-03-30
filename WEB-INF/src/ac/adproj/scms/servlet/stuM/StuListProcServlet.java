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

import ac.adproj.scms.dao.DBDao;
import ac.adproj.scms.servlet.InitServlet;
import ac.adproj.scms.servlet.ServletProcessingException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Map;

public class StuListProcServlet extends HttpServlet {   //    /scms/stuM/listProc
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");

        try (
                DBDao daoO = InitServlet.daoO;
                Connection conn = daoO.getConnection();
                PreparedStatement ps = conn.prepareStatement("delete from xs where xs.stuid = ?;");
                Writer out = response.getWriter()) {

            String delF = request.getParameter("del");
            StringBuilder undoneMessageBuilder = new StringBuilder();
            undoneMessageBuilder.append("如下学生由于此前输入了成绩，不能删除。\\n\\n学号：\\n");
            boolean notFullyDel = false;

            if (delF != null) {
                Map<String, String[]> paramMap = request.getParameterMap();

                HashSet<String> delKeys = new HashSet<>();

                for (Map.Entry<String, String[]> s : paramMap.entrySet()) {
                    if (s.getKey().equals("del"))
                        continue;
                    if (s.getValue()[0].toLowerCase().equals("on"))
                        delKeys.add(s.getKey());
                }

                for (String s : delKeys) {
                    ps.setString(1, s);
                    try {
                        ps.execute();
                    } catch (java.sql.SQLIntegrityConstraintViolationException sicve) {
                        notFullyDel = true;
                        undoneMessageBuilder.append(s);
                        undoneMessageBuilder.append("\\n");
                    }
                }

                if (notFullyDel) {
                    out.write("<script>alert(\"" + new String(undoneMessageBuilder.toString().getBytes(), "utf-8") + "\"); location.href=\"index.jsp\";</script>");
                } else {
                    out.write(new String(("<script>alert(\"删除成功! \"); location.href=\"index.jsp\";</script>").getBytes(), "utf-8"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ServletProcessingException(e);
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
}