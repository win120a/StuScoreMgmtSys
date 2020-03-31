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
import ac.adproj.scms.dao.StudentDao;
import ac.adproj.scms.entity.Student;
import ac.adproj.scms.servlet.InitServlet;
import ac.adproj.scms.servlet.ServletProcessingException;
import ac.adproj.scms.servlet.forms.MultipartFormHandler;
import ac.adproj.scms.servlet.forms.MultipartFormHandlerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.io.Writer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class StuInfoController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String stuid = req.getParameter("id");
        Student s = StudentDao.getStudentObjectThroughDB(stuid);
        req.setAttribute("studentObject", s);
        req.getRequestDispatcher("stuInfo.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        MultipartFormHandler mpf = MultipartFormHandlerFactory.getFormHandler(request);

        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");

        try (DBDao daoO = InitServlet.daoO) {
            Writer out = response.getWriter();
            Connection conn = daoO.getConnection();
            Statement stmt = conn.createStatement();

            request.setCharacterEncoding("utf-8");
            String addP = mpf.getStringParameter("add");
            String modP = mpf.getStringParameter("modify");
            String type = mpf.getStringParameter("type");

            if (addP != null && addP.equals("1")) {
                // id name major gender dob totalCredits(=0) photo remark
                PreparedStatement ps_a = conn.prepareStatement("insert into xs values (?, ?, ?, ?, ?, ?, NULL, ?);");

                ps_a.setString(1, mpf.getStringParameter("id"));
                ps_a.setString(2, mpf.getStringParameter("name"));
                ps_a.setString(3, mpf.getStringParameter("major"));
                ps_a.setString(4, mpf.getStringParameter("gender"));
                ps_a.setString(5, mpf.getStringParameter("dob"));
                ps_a.setString(6, "0");

                ps_a.setString(7, mpf.getStringParameter("remark"));

                // System.err.println(ps_a.toString());

                ps_a.execute();

                byte[] pictW = (byte[]) mpf.getNonFormFieldObject("headSet");

                if (pictW != null && pictW.length != 0) {
                    updatePhoto(conn, mpf.getStringParameter("id"), pictW);
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

                ps.setString(1, mpf.getStringParameter("name"));
                ps.setString(2, mpf.getStringParameter("major"));
                ps.setString(3, mpf.getStringParameter("gender"));
                ps.setString(4, mpf.getStringParameter("dob"));
                ps.setString(5, mpf.getStringParameter("remark"));
                ps.setString(6, mpf.getStringParameter("id"));

                ps.execute();

                // byte[] pictB = getUploadedFile(request, "headSet", daoO);
                byte[] pictW = (byte[]) mpf.getNonFormFieldObject("headSet");

                if (pictW != null && pictW.length != 0) {
                    updatePhoto(conn, mpf.getStringParameter("id"), pictW);
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

    private void updatePhoto(Connection conn, String id, byte[] pictW) throws SQLException {
        PreparedStatement ps_p = conn.prepareStatement("update xs set photo=? where stuid=?;");
        if (pictW != null && pictW.length != 0) {
            byte[] pictB = pictW;
            ps_p.setBlob(1, new SerialBlob(pictB));
            ps_p.setString(2, id);
            ps_p.execute();
        } else {
            throw new IllegalArgumentException();
        }
    }
}
