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

import ac.adproj.scms.entity.Entity;
import ac.adproj.scms.entity.GenderEnum;
import ac.adproj.scms.entity.Student;
import ac.adproj.scms.service.photo.PhotoService;
import ac.adproj.scms.service.photo.PhotoServiceFactory;
import ac.adproj.scms.servlet.InitServlet;
import ac.adproj.scms.servlet.ServletProcessingException;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;

public final class StudentDao {
    private StudentDao() {
    }

    /**
     * Returns a Student object according to the DB's record.
     *
     * @param stuid The student ID.
     * @return The Student object, or null if the student object did not exist.
     */
    public static Student getStudentObjectThroughDB(String stuid) {
        String name;
        GenderEnum gender;
        String major;
        String birthdate;
        String remark;
        byte[] photo;
        int totalCredits;

        try (DBDao daoO = InitServlet.daoO) {
            ResultSet rs = daoO.query("select * from xs where stuid=?;", stuid);

            if (!rs.next()) {
                return null;
            }

            name = rs.getString("name");
            birthdate = rs.getString("birthdate");
            major = rs.getString("major");
            gender = rs.getInt("gender") == 1 ? GenderEnum.MALE : GenderEnum.FEMALE;

            ResultSet rs_totalC = daoO.query("select sum(credit) as c from xs_kc where stuid=? and score >= 60;"
                    , stuid);

            Blob photoBlob = rs.getBlob("photo");

            InputStream is;

            if (photoBlob != null) {
                is = photoBlob.getBinaryStream();
                photo = is.readAllBytes();
            } else {
                photo = new byte[]{};
            }

            remark = rs.getString("remark");

            rs_totalC.next();
            totalCredits = rs_totalC.getInt("c");

            return new Student(name, birthdate, gender, stuid, photo, major, totalCredits, remark);
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            throw new ServletProcessingException(e);
        }
    }

    /**
     * Upload the Student object to the database.
     *
     * @param s The student object that needs to upload.
     * @throws SQLException If database ran into error.
     */
    public static void writeStudentObjectToDatabase(Student s) throws SQLException, IOException {
        try (DBDao daoO = InitServlet.daoO) {

            if (daoO.query("select name from xs where stuid=?", s.getId()).next()) {
                daoO.update("update xs set name=?, major=?, gender=?, birthdate=?, "
                                + "remark=? where stuid=?;"
                        , s.getName()
                        , s.getMajor()
                        , Integer.toString(s.getGender().getGenderNumber())
                        , s.getDob(), s.getRemark(), s.getId());
            } else {
                // id name major gender dob totalCredits(=0) photo remark
                daoO.insert("insert into xs values (?, ?, ?, ?, ?, 0, NULL, ?);"
                        , s.getId()
                        , s.getName()
                        , s.getMajor()
                        , Integer.toString(s.getGender().getGenderNumber())
                        , s.getDob()
                        , s.getRemark());
            }

            if (s.getPhoto() != null && s.getPhoto().length != 0) {
                updatePhoto(s.getId(), s.getPhoto());
            }
        }
    }

    /**
     * Updates the photo.
     *
     * @param id    The student ID.
     * @param pictW The picture array.
     * @throws SQLException If DB ran into error.
     */
    private static void updatePhoto(String id, byte[] pictW) throws SQLException, IOException {
        if (pictW != null && pictW.length != 0) {
            PhotoService ps = PhotoServiceFactory.getPhotoService();
            ps.uploadPhoto(id, pictW);
        }
    }

    public static void deleteObject(String id) throws SQLException {
        try (DBDao daoO = InitServlet.daoO) {
            daoO.delete("delete from xs where xs.stuid=?;", id);
        }
    }

    public static void deleteObject(Entity e) throws SQLException {
        deleteObject(e.getId());
    }
}
