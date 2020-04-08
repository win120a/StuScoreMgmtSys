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

package ac.adproj.scms.servlet.photo;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Represents a photo service.
 *
 * @author Andy Cheung
 */
public interface PhotoService extends AutoCloseable {
    /**
     * Placeholder when the photo doesn't exist.
     */
    String PHOTO_PLACEHOLDER_RELATIVE_PATH = "/images/none.png";

    /**
     * Get the byte array of photo.
     * @param id The Student ID.
     * @return The byte array of the photo.
     * @throws IOException If IO Error encountered.
     * @throws SQLException If SQL Error encountered.
     */
    byte[] getPhoto(String id) throws IOException, SQLException;

    /**
     * Check the photo corresponding to the student id whether exists or not.
     * @param id The student ID.
     * @return Exists, true. Not exist, false.
     * @throws IOException If IO Error encountered.
     * @throws SQLException If SQL Error encountered.
     */
    boolean isPhotoExists(String id) throws IOException, SQLException;

    void uploadPhoto(String id, byte[] photoArray) throws IOException, SQLException;
}
