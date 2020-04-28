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
import ac.adproj.scms.entity.Student;
import ac.adproj.scms.entity.StudentScore;
import ac.adproj.scms.servlet.InitServlet;
import ac.adproj.scms.servlet.ServletProcessingException;

import java.sql.ResultSet;
import java.sql.SQLException;

public final class StudentScoreDAO {
    public static int EMPTY_SCORE_VALUE = -1;

    private StudentScoreDAO() {
        // NOOP
    }

    public static void writeScoreObjectToDatabase(StudentScore studentScore) {
        try (DBDao daoO = InitServlet.daoO) {
            ResultSet creditsS = daoO.query("select credits from kc where courseID=?;",
                    studentScore.getCourseId());
            creditsS.next();

            String credits = creditsS.getString("credits");
            String score = Integer.toString(studentScore.getScore());

            ResultSet selectRS = daoO.query("select * from xs_kc where stuid=? and courseID=?",
                    studentScore.getStudentId(), studentScore.getCourseId());

            if (!selectRS.next()) {
                // sID, cID, score, credit
                if (score.equals(Integer.toString(EMPTY_SCORE_VALUE))) {
                    return; // Do not do insert statement if the value is empty.
                }

                daoO.insert("insert into xs_kc values (?, ?, ?, ?);",
                        studentScore.getStudentId(), studentScore.getCourseId(),
                        score, credits);

            } else { // In case the data was inserted (i.e exists).
                // s c i
                if (studentScore.isDeletePending()) {
                    daoO.delete("delete from xs_kc where stuid=? and courseID=?;",
                            studentScore.getStudentId(), studentScore.getCourseId());
                } else {
                    daoO.update("update xs_kc set score=?, credit=? where stuid=? and courseID=?;",
                            score, credits, studentScore.getStudentId(), studentScore.getCourseId());
                }
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            throw new ServletProcessingException(sqle);
        }
    }

    public static StudentScore getScoreObjectThroughDatabase(Student student, Course c) {
        try (DBDao daoO = InitServlet.daoO) {
            ResultSet rs = daoO.query("select score from xs_kc where stuid=? and courseid=?;",
                    student.getId(), c.getId());

            int score = EMPTY_SCORE_VALUE;

            if (rs.next()) {
                score = Integer.parseInt(rs.getString("score"));
            }

            return new StudentScore(student, c, score);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ServletProcessingException(e);
        }
    }

    public static StudentScore getScoreObjectThroughDatabase(String studentID, String courseID) {
        return getScoreObjectThroughDatabase(new Student(studentID), new Course(courseID));
    }
}
