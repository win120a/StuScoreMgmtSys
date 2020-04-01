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

import ac.adproj.scms.dao.DBDao;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import java.sql.SQLException;

/**
 * The initialization Servlet that provides connection DAO object.
 *
 * @author Andy Cheung
 */
public class InitServlet extends HttpServlet {
    public static DBDao daoO;

    @Override
    public void init() {
        ServletContext application = getServletContext();

        String driver = application.getInitParameter("driver");
        driver = driver != null ? driver : "";

        String serverAddr = application.getInitParameter("serverAddr");
        serverAddr = serverAddr != null ? serverAddr : "";

        String userName = application.getInitParameter("userName");
        userName = userName != null ? userName : "";

        String password = application.getInitParameter("password");
        password = password != null ? password : "";

        String serverTimeZone = application.getInitParameter("serverTimeZone");
        serverTimeZone = serverTimeZone != null ? serverTimeZone : "";

        String db = application.getInitParameter("db");
        db = db != null ? db : "";

        daoO = new DBDao(driver, serverAddr, userName, password, serverTimeZone, db);
    }

    @Override
    public void destroy() {
        try {
            daoO.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ServletProcessingException(e);
        }
    }
}