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

package ac.adproj.scms.entity;

import com.google.gson.JsonObject;

/**
 * Represents the score of a course that belongs to a student.
 *
 * @author Andy Cheung
 */
public class StudentScore implements Entity {
    private final Student student;
    private final Course course;
    private int score;

    private boolean deletePending = false;

    public StudentScore(Student student, Course course, int score) {
        this.student = student;
        this.course = course;
        this.score = score;
    }

    public StudentScore(String studentID, String courseID, int score) {
        this(new Student(studentID), new Course(courseID), score);
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        // TODO Score Boundary Check.
        this.score = score;
    }

    public Student getStudent() {
        return student;
    }

    public Course getCourse() {
        return course;
    }

    public String getStudentId() {
        return student.getId();
    }

    public String getCourseId() {
        return course.getId();
    }

    @Override
    public JsonObject getJSON() {
        JsonObject jso = new JsonObject();

        jso.addProperty("studentID", student.getId());
        jso.addProperty("courseID", course.getId());
        jso.addProperty("score", score);

        return jso;
    }

    @Override
    public String getId() {
        return "S" + student.getId() + " | C" + course.getId();
    }

    @Override
    public boolean isFullyObtained() {
        return student != null && course != null;
    }

    public void deleteScore() {
        deletePending = true;
    }

    public boolean isDeletePending() {
        return deletePending;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        StudentScore that = (StudentScore) o;

        if (that.getScore() == getScore()) {
            return false;
        }
        if (!student.equals(that.student)) {
            return false;
        }

        return course.equals(that.course);
    }

    @Override
    public int hashCode() {
        int result = student.hashCode();
        result = 31 * result + course.hashCode();
        return result;
    }
}
