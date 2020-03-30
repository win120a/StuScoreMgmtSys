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

/**
 * The initialization Servlet that provides connection DAO object.
 *
 * @author Andy Cheung
 */
public class InitServlet extends HttpServlet {
    public static DBDao daoO;
    private String driver = "";
    private String serverAddr = "";
    private String userName = "";
    private String password = "";
    private String serverTimeZone = "";
    private String db = "";

    @Override
    public void init() {
        ServletContext application = getServletContext();
        boolean configured = false;

        driver = (String) application.getInitParameter("driver");
        driver = driver != null ? (String) driver : "";

        serverAddr = (String) application.getInitParameter("serverAddr");
        serverAddr = driver != null ? (String) serverAddr : "";

        userName = (String) application.getInitParameter("userName");

        Object confO = application.getAttribute("configured");
        configured = confO == null ? false : (Boolean) confO;

        userName = userName != null ? (String) userName : "";

        password = (String) application.getInitParameter("password");
        password = password != null ? (String) password : "";

        serverTimeZone = (String) application.getInitParameter("serverTimeZone");
        serverTimeZone = serverTimeZone != null ? (String) serverTimeZone : "";

        db = (String) application.getInitParameter("db");
        db = db != null ? (String) db : "";

        daoO = new DBDao(driver, serverAddr, userName, password, serverTimeZone, db);
    }
}