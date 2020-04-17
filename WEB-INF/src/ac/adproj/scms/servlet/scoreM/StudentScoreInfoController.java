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

package ac.adproj.scms.servlet.scoreM;

import ac.adproj.scms.dao.CourseDAO;
import ac.adproj.scms.dao.StudentDAO;
import ac.adproj.scms.dao.StudentScoreDAO;
import ac.adproj.scms.entity.Entity;
import ac.adproj.scms.entity.StudentScore;
import ac.adproj.scms.servlet.base.detailed.MultipleStudentsTextOnlyDetailedControllerBase;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

public class StudentScoreInfoController extends MultipleStudentsTextOnlyDetailedControllerBase {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        setViewUrl("/WEB-INF/scoreM/scoreInfo.jsp");
        super.doGet(req, resp);
    }

    @Override
    protected void uploadObjectToDataBase(HttpServletRequest request) throws SQLException, IOException {
        Map<String, String[]> paramMap = request.getParameterMap();
        Map<String, String> scoreMap = new HashMap<>(paramMap.size());
        String courseID = request.getParameter("id");

        for (Map.Entry<String, String[]> s : paramMap.entrySet()) {
            if ("update".equals(s.getKey()) || "id".equals(s.getKey())) {
                continue;
            }
            if (s.getKey().contains("score_")) {
                scoreMap.put(s.getKey().replace("score_", ""), s.getValue()[0]);
            }
        }

        // "score_" + StuID, score
        for (Map.Entry<String, String> s : scoreMap.entrySet()) {
            String stuid = s.getKey();
            String score = s.getValue();
            StudentScore stuS;

            if (score.isEmpty()) {
                stuS = new StudentScore(stuid, courseID, StudentScoreDAO.EMPTY_SCORE_VALUE);
                stuS.deleteScore();
            } else {
                stuS = new StudentScore(stuid, courseID, Integer.parseInt(score));
            }

            StudentScoreDAO.writeScoreObjectToDatabase(stuS);
        }
    }

    @Override
    protected Set<Entity> readEntitySet(String courseID) throws SQLException {
        Set<String> studentIDs = StudentDAO.getStudentIDSet();
        Set<Entity> scoreSet = new HashSet<>();

        for (String id : studentIDs) {
            scoreSet.add(StudentScoreDAO.getScoreObjectThroughDatabase(
                    Objects.requireNonNull(StudentDAO.getStudentObjectThroughDB(id)),
                    Objects.requireNonNull(CourseDAO.getCourseObjectThroughDB(courseID))));
        }

        return scoreSet;
    }
}