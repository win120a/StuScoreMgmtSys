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
import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Object that represents an connection to Database.
 *
 * @author Andy Cheung
 */
public class DBDao implements DBConnectionHandler {
    private static final String ADDR_HEAD = "jdbc:mysql://";
    private final String driver;
    private final String serverAddr;
    private final String userName;
    private final String password;
    private final String serverTimeZone;
    private final String db;
    private final ComboPooledDataSource cpds = new ComboPooledDataSource();

    public DBDao(String driver, String serverAddr, String userName, String password, String serverTimeZone, String db) {
        this.driver = driver;
        this.serverAddr = serverAddr;
        this.userName = userName;
        this.password = password;
        this.serverTimeZone = serverTimeZone;
        this.db = db;

        init();
    }

    public void init() {
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException nfe) {
            throw new ServletProcessingException(nfe);
        }

        try {
            cpds.setDriverClass(driver);
        } catch (PropertyVetoException ignored) {
            // ignore
        }

        cpds.setJdbcUrl(ADDR_HEAD + serverAddr + "/" + db);
        cpds.setAcquireIncrement(2);
        cpds.setMinPoolSize(5);
        cpds.setMaxPoolSize(100);

        cpds.setUser(userName);
        cpds.setPassword(password);
    }

    @Override
    public Connection getConnection() throws SQLException {
        return cpds.getConnection();
    }

    public String getDriverClassName() {
        return driver;
    }

    public String getDatabaseName() {
        return db;
    }

    public String getServerTimeZone() {
        return serverTimeZone;
    }

    private PreparedStatement prepStmt(Connection conn, String sql, String... contents) throws SQLException {
        if (conn == null) {
            conn = getConnection();
        }

        PreparedStatement prepS = conn.prepareStatement(sql);

        for (int i = 0; i < contents.length; i++) {
            prepS.setString(i + 1, contents[i]);
        }

        return prepS;
    }

    public ResultSet query(Connection conn, String sql, String... contents) throws SQLException {
        PreparedStatement prepS = prepStmt(conn, sql, contents);

        ResultSet results = prepS.executeQuery();

        return results;
    }

    public void insert(Connection c, String sql, String... contents) throws SQLException {
        prepStmt(c, sql, contents).execute();
    }

    public int update(Connection conn, String sql, String... contents) throws SQLException {
        return prepStmt(conn, sql, contents).executeUpdate();
    }

    public int delete(Connection conn, String sql, String... contents) throws SQLException {
        return prepStmt(conn, sql, contents).executeUpdate();
    }

    @Override
    public void close() throws SQLException {
        cpds.close();
    }
}