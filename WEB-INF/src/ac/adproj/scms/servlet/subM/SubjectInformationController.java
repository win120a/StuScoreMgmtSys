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

package ac.adproj.scms.servlet.subM;

import ac.adproj.scms.dao.CourseDao;
import ac.adproj.scms.entity.Course;
import ac.adproj.scms.entity.Entity;
import ac.adproj.scms.servlet.base.detailed.TextOnlyDetailedFormControllerBase;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class SubjectInformationController extends TextOnlyDetailedFormControllerBase {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        setViewURL("/WEB-INF/subM/subInfo.jsp");
        super.doGet(req, resp);
    }

    @Override
    protected void uploadObjectToDataBase(HttpServletRequest request) throws SQLException {
        Course c = new Course(getParameter(request, "courseID"));

        c.setName(getParameter(request, "courseName"));
        c.setTerm(getParameter(request, "term"));
        c.setCourseHours(Integer.parseInt(getParameter(request,"courseHours")));
        c.setCredits(Integer.parseInt(getParameter(request, "credits")));

        CourseDao.writeCourseObjectToDatabase(c);
    }

    @Override
    protected Entity readEntityObject(HttpServletRequest request, String id) {
        return CourseDao.getCourseObjectThroughDB(id);
    }
}
