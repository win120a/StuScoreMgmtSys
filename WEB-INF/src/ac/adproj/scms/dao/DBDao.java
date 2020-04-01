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

package ac.adproj.scms.dao;

import ac.adproj.scms.servlet.ServletProcessingException;

import java.sql.*;
import java.util.HashMap;
import java.util.Properties;

/**
 * Object that represents an connection to Database.
 *
 * @author Andy Cheung
 */
public class DBDao implements DBConnectionHandler {
    private static final String ADDR_HEAD = "jdbc:mysql://";
    private HashMap<Connection, Integer> pool;
    private Connection connectionI;
    private String driver;
    private String serverAddr;
    private String userName;
    private String password;
    private String serverTimeZone;
    private String db;

    public DBDao(String driver, String serverAddr, String userName, String password, String serverTimeZone, String db) {
        this.driver = driver;
        this.serverAddr = serverAddr;
        this.userName = userName;
        this.password = password;
        this.serverTimeZone = serverTimeZone;
        this.db = db;
        this.pool = new HashMap<>();
    }

    public Connection getConnection() throws SQLException {
        if (connectionI == null || connectionI.isClosed()) {
            try {
                Class.forName(driver);
            } catch (ClassNotFoundException nfe) {
                throw new ServletProcessingException(nfe);
            }


            Properties p = new Properties();

            p.put("user", userName);
            p.put("password", password);
            p.put("timezone", serverTimeZone);

            String dbAddress = ADDR_HEAD + serverAddr + "/";

            Connection conn = DriverManager.getConnection(dbAddress + db, p);

            connectionI = conn;
        }

        return connectionI;
    }

    public String getDriverClassName() {
        return driver;
    }

    public String getDBName() {
        return db;
    }

    public String getServerTimeZone() {
        return serverTimeZone;
    }

    public ResultSet query(String sql, String... contents) throws SQLException {
        PreparedStatement prepS = prepStmt(sql, contents);

        ResultSet results = prepS.executeQuery();

        return results;
    }

    public void insert(String sql, String... contents) throws SQLException {
        prepStmt(sql, contents).execute();
    }

    public int update(String sql, String... contents) throws SQLException {
        return prepStmt(sql, contents).executeUpdate();
    }

    public void delete(String sql, String... contents) throws SQLException {
        prepStmt(sql, contents).execute();
    }

    @Override
    public void close() throws SQLException {
        connectionI.close();
    }
}