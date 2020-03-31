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

import ac.adproj.scms.entity.GenderEnum;
import ac.adproj.scms.entity.Student;
import ac.adproj.scms.servlet.InitServlet;
import ac.adproj.scms.servlet.ServletProcessingException;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentDao {
    public static Student getStudentObjectThroughDB (String stuid) {
        String name;
        GenderEnum gender;
        String major;
        String birthdate;
        String remark;
        byte[] photo;
        int totalCredits;

        try (DBDao daoO = InitServlet.daoO) {
            ResultSet rs = daoO.query("select * from xs where stuid=?;", stuid);

            if (!rs.next())
                return null;

            name = rs.getString("name");
            birthdate = rs.getString("birthdate");
            major = rs.getString("major");
            gender = rs.getInt("gender") == 1 ? GenderEnum.MALE : GenderEnum.FEMALE;

            ResultSet rs_totalC = daoO.query("select sum(credit) as c from xs_kc where stuid=? and score >= 60;"
                                            , stuid);

            Blob photoBlob = rs.getBlob("photo");
            InputStream is = photoBlob.getBinaryStream();

            remark = rs.getString("remark");

            rs_totalC.next();
            totalCredits = rs_totalC.getInt("c");

            photo = is.readAllBytes();

            return new Student(name, birthdate, gender, stuid, photo, major, totalCredits, remark);
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            throw new ServletProcessingException(e);
        }
    }
}
