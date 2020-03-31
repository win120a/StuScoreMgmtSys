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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface DBConnectionHandler extends AutoCloseable {
    Connection getConnection() throws SQLException;

    default PreparedStatement prepStmt(String sql, String... contents) throws SQLException {
        PreparedStatement prepS = getConnection().prepareStatement(sql);

        for (int i = 0; i < contents.length; i++) {
            prepS.setString(i + 1, contents[i]);
        }

        return prepS;
    }
}