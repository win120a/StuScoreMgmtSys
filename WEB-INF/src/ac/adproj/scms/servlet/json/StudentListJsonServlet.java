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

package ac.adproj.scms.servlet.json;

import ac.adproj.scms.dao.CourseDAO;
import ac.adproj.scms.dao.StudentDAO;
import ac.adproj.scms.entity.Student;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Set;

public class StudentListJsonServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Set<Student> s = null;

        try {
            s = StudentDAO.getStudentObjectSet();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        String json;
        if (s == null) {
            json = "{\"error\" : \"NO Student.\"}";
        } else {
            JsonObject jso = new JsonObject();
            JsonArray jsa = new JsonArray();
            jso.add("info", jsa);

            s.forEach(v -> jsa.add(v.getJSON()));
            json = jso.toString();
        }

        resp.setCharacterEncoding("utf-8");
        resp.setContentType("application/json");
        try (Writer w = resp.getWriter()) {
            w.write(json);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        StringBuilder jsonTextBuilder = new StringBuilder();

        try (Scanner s = new Scanner(req.getReader())) {
            while (s.hasNext()) {
                jsonTextBuilder.append(s.nextLine());
            }
        }

        JsonObject requestContent = (JsonObject) JsonParser.parseString(jsonTextBuilder.toString());

        JsonArray jsa = requestContent.getAsJsonArray("id");

        Iterator<JsonElement> iterator = jsa.iterator();

        boolean notFullyDel = false;
        LinkedList<String> undoneIDs = new LinkedList<>();

        while (iterator.hasNext()) {
            String id = iterator.next().getAsString();

            try {
                StudentDAO.deleteObject(id);
            } catch (java.sql.SQLIntegrityConstraintViolationException sicve) {
                notFullyDel = true;
                undoneIDs.add(id);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        JsonObject notificationJson = new JsonObject();

        if (notFullyDel) {
            JsonArray ids = new JsonArray(undoneIDs.size());

            undoneIDs.forEach(ids::add);

            notificationJson.addProperty("status", "-1");
            notificationJson.add("failedID", ids);
        } else {
            notificationJson.addProperty("status", "0");
        }

        notifyClient(resp, notificationJson.toString());
    }

    private void notifyClient(HttpServletResponse resp, String content) {
        try (PrintWriter w = resp.getWriter()) {
            w.print(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
