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

package ac.adproj.scms.servlet.base.detailed;

import ac.adproj.scms.entity.Entity;
import ac.adproj.scms.servlet.ServletProcessingException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Set;

public abstract class MultipleStudentsTextOnlyDetailedControllerBase extends TextOnlyDetailedFormControllerBase {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (getViewUrl() == null) {
            throw new IllegalArgumentException();
        }

        String id = req.getParameter("id");
        Set<Entity> e;

        try {
            e = readEntitySet(id);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new ServletProcessingException(throwables);
        }

        req.setAttribute("entitySet", e);
        req.getRequestDispatcher(getViewUrl()).forward(req, resp);
    }

    @Override
    protected Entity readEntityObject(HttpServletRequest request, String id) {
        throw new UnsupportedOperationException();
    }

    protected abstract Set<Entity> readEntitySet(String id) throws SQLException;
}
