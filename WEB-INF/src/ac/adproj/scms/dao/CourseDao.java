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

import ac.adproj.scms.entity.Course;
import ac.adproj.scms.entity.Entity;
import ac.adproj.scms.servlet.InitServlet;
import ac.adproj.scms.servlet.ServletProcessingException;

import java.sql.ResultSet;
import java.sql.SQLException;

public final class CourseDao {
    private CourseDao() {
    }

    /**
     * Returns a Course object according to the DB's record.
     *
     * @param courseid The course ID.
     * @return The Student object, or null if the student object did not exist.
     */
    public static Course getCourseObjectThroughDB(String courseid) {
        try (DBDao daoO = InitServlet.daoO) {
            ResultSet rs = daoO.query("select * from kc where courseID=?;"
                    , courseid);

            if (!rs.next())
                return null;

            String courseName = rs.getString("courseName");
            String courseID = rs.getString("courseID");
            String term = rs.getString("term");
            int credits = rs.getInt("credits");
            int courseHours = rs.getInt("courseHours");

            Course c = new Course(courseID);
            c.setName(courseName);
            c.setCourseHours(courseHours);
            c.setTerm(term);
            c.setCredits(credits);

            return c;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ServletProcessingException(e);
        }
    }

    /**
     * Upload the Course object to the database.
     *
     * @param c The Course object that needs to upload.
     * @throws SQLException If SQL ran into error.
     */
    public static void writeCourseObjectToDatabase(Course c) throws SQLException {
        try (DBDao daoO = InitServlet.daoO) {

            if (daoO.query("select term from kc where courseID=?;", c.getId()).next()) {
                daoO.update("update kc set courseName=?, term=?, credits=?, "
                                + "courseHours=? where courseID=?;"
                            , c.getName()
                            , c.getTerm()
                            , Integer.toString(c.getCredits())
                            , Integer.toString(c.getCourseHours())
                            , c.getId());
            } else {
                daoO.insert("insert into kc values (?, ?, ?, ?, ?);"
                            , c.getId()
                            , c.getName()
                            , c.getTerm()
                            , Integer.toString(c.getCourseHours())
                            , Integer.toString(c.getCredits()));
            }
        }
    }

    public static void deleteObject(String id) throws SQLException {
        try (DBDao daoO = InitServlet.daoO) {
            daoO.delete("delete from kc where kc.courseId=?;", id);
        }
    }

    public static void deleteObject(Entity e) throws SQLException {
        deleteObject(e.getId());
    }
}
