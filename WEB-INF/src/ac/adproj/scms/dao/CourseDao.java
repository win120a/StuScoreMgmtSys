package ac.adproj.scms.dao;

import ac.adproj.scms.entity.Course;
import ac.adproj.scms.servlet.InitServlet;
import ac.adproj.scms.servlet.ServletProcessingException;

import java.sql.PreparedStatement;
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

            if (daoO.query("select name from kc where courseID=?;", c.getId()).next()) {
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
}
