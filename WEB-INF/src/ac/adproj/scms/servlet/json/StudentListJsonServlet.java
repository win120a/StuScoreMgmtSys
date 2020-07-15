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

import ac.adproj.scms.dao.StudentDAO;
import ac.adproj.scms.entity.Entity;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

public class StudentListJsonServlet extends AbstractListJsonServlet {
    @Override
    protected Set<? extends Entity> getEntitySet() throws SQLException {
        return StudentDAO.getStudentObjectSet();
    }

    @Override
    protected String handleDeleteRequest(JsonObject requestContent) {

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

        return notificationJson.toString();
    }
}
