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

package ac.adproj.scms.servlet.scoreM;

import ac.adproj.scms.dao.DBDao;
import ac.adproj.scms.servlet.InitServlet;
import ac.adproj.scms.servlet.ServletProcessingException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class ScoreInfoServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");

        try (DBDao daoO = InitServlet.daoO) {

            Connection conn = daoO.getConnection();

            Writer out = response.getWriter();

            Statement stmt = conn.createStatement();

            PreparedStatement ps = conn.prepareStatement("select score from xs_kc where stuid=? and courseID=?;");

            PreparedStatement ps_u = conn
                    .prepareStatement("update xs_kc set score=?, credit=? where stuid=?" + "and courseID=?;");

            // sID, cID, score, credit
            PreparedStatement ps_i = conn.prepareStatement("insert into xs_kc values (?, ?, ?, ?);");

            PreparedStatement ps_d = conn.prepareStatement("delete from xs_kc where stuid=? and courseID=?;");

            ps.setString(2, request.getParameter("id"));
            ps_i.setString(2, request.getParameter("id"));
            String updateP = request.getParameter("update");

            if (updateP != null && updateP.equals("1")) {
                Map<String, String[]> paramMap = request.getParameterMap();
                Map<String, String> scoreMap = new HashMap<>(paramMap.size());

                for (Map.Entry<String, String[]> s : paramMap.entrySet()) {
                    if (s.getKey().equals("update") || s.getKey().equals("id"))
                        continue;
                    if (s.getKey().contains("score_"))
                        scoreMap.put(s.getKey(), s.getValue()[0]);
                }

                for (Map.Entry<String, String> s : scoreMap.entrySet()) // "score_" + StuID, score
                {
                    ResultSet creditsS = stmt
                            .executeQuery("select credits from kc where courseID=" + request.getParameter("id"));

                    creditsS.next();
                    String credits = creditsS.getString("credits");
                    String score = s.getValue();

                    PreparedStatement selectPS = conn
                            .prepareStatement("select * from xs_kc where stuid=? and courseID=?");

                    selectPS.setString(1, s.getKey().replace("score_", ""));
                    selectPS.setString(2, request.getParameter("id"));

                    ResultSet selectRS = selectPS.executeQuery();

                    if (!selectRS.next()) {
                        if (s.getValue().isEmpty()) {
                            continue; // Do not do insert statement if the value is empty.
                        }

                        ps_i.setString(1, s.getKey().replace("score_", ""));
                        ps_i.setString(3, score);
                        ps_i.setString(4, credits);
                        ps_i.execute();

                        // out.print("<script>alert(\"I: " + ps_i.toString() + "\");</script>");
                    } else { // In case the data was inserted (i.e exists).
                        // s c i
                        if (s.getValue().isEmpty()) {
                            // "delete from xs_kc where stuid=? and courseID=?;"

                            ps_d.setString(1, s.getKey().replace("score_", ""));
                            ps_d.setString(2, request.getParameter("id"));
                            ps_d.execute();
                            // out.print("<script>alert(\"D: " + ps_d.toString() + "\");</script>");
                        } else {
                            ps_u.setString(1, score);
                            ps_u.setString(2, credits);
                            ps_u.setString(3, s.getKey().replace("score_", ""));
                            ps_u.setString(4, request.getParameter("id"));
                            ps_u.execute();
                            // out.print("<script>alert(\"U: " + ps_u.toString() + "\");</script>");
                        }
                    }

                    byte[] b = ("<script>alert(\"更新成绩成功!\"); opener = null; close();</script>").getBytes();
                    out.write(new String(b, "utf-8"));
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

        byte[] b = ("<p>测试1 Test:" + request.getParameter("test") + "</p>").getBytes();

        response.getWriter().print(new String(b, "utf-8"));
    }
}