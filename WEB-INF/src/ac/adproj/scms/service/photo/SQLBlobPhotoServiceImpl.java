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

package ac.adproj.scms.service.photo;

import ac.adproj.scms.dao.DBDao;
import ac.adproj.scms.servlet.InitServlet;

import javax.servlet.ServletContext;
import javax.sql.rowset.serial.SerialBlob;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;

/**
 * The implementation of photo service through SQL Blob Object.
 *
 * @author Andy Cheung
 */
final class SQLBlobPhotoServiceImpl implements PhotoService {
    private static final String QUERY_SQL = "select photo as p from xs where stuid=?;";
    private final ServletContext ctx;

    public SQLBlobPhotoServiceImpl(ServletContext ctx) {
        this.ctx = ctx;
    }

    private byte[] getNone() throws IOException {
        String rPath = ctx.getRealPath(PHOTO_PLACEHOLDER_RELATIVE_PATH);
        FileInputStream photoS = new FileInputStream(new File(rPath));

        return photoS.readAllBytes();
    }

    /**
     * Get the byte array of photo.
     *
     * @param id The Student ID.
     * @return The byte array of the photo.
     * @throws IOException  If IO Error encountered.
     * @throws SQLException If SQL Error encountered.
     */
    @Override
    public byte[] getPhoto(String id) throws SQLException, IOException {
        DBDao daoO = InitServlet.daoO;

        if (id == null || id.isEmpty()) {
            return getNone();
        }

        ResultSet resSet = daoO.query(QUERY_SQL, id);
        resSet.next();
        Blob photoO = resSet.getBlob("p");

        if (photoO == null) {
            return getNone();
        } else {
            InputStream photoS = photoO.getBinaryStream();
            return photoS.readAllBytes();
        }
    }

    /**
     * Check the photo corresponding to the student id whether exists or not.
     *
     * @param id The student ID.
     * @return Exists, true. Not exist, false.
     * @throws IOException  If IO Error encountered.
     * @throws SQLException If SQL Error encountered.
     */
    @Override
    public boolean doesPhotoExist(String id) throws SQLException {
        ResultSet rs = InitServlet.daoO.query(QUERY_SQL, id);

        return rs.next() && rs.getBlob("p") != null;
    }

    @Override
    public void uploadPhoto(String id, byte[] photoArray) throws SQLException {
        PreparedStatement ps_p = InitServlet.daoO
                .getConnection()
                .prepareStatement("update xs set photo=? where stuid=?;");

        if (photoArray != null && photoArray.length != 0) {
            ps_p.setBlob(1, new SerialBlob(photoArray));
            ps_p.setString(2, id);
            ps_p.execute();
        } else {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public void deletePhoto(String id) throws SQLException {
        PreparedStatement deletingStatement = InitServlet.daoO
                .getConnection()
                .prepareStatement("update xs set photo=NULL where stuid=?;");

        deletingStatement.setString(1, id);

        deletingStatement.execute();
    }

    @Override
    public void close() throws Exception {
        InitServlet.daoO.close();
    }
}
