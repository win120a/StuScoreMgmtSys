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

package ac.adproj.scms.servlet.stuM;

import ac.adproj.scms.dao.StudentDao;
import ac.adproj.scms.entity.Entity;
import ac.adproj.scms.entity.Student;
import ac.adproj.scms.servlet.base.MultiPartFormControllerBase;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class StuInfoController extends MultiPartFormControllerBase {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        setViewURL("/WEB-INF/stuM/stuInfo.jsp");
        super.doGet(req, resp);
    }

    @Override
    protected void uploadObjectToDataBase(HttpServletRequest request) throws SQLException {
        Student s = new Student(getStringParameter(request, "id"));

        s.setName(getStringParameter(request, "name"));
        s.setMajor(getStringParameter(request, "major"));
        s.setGenderThroughNumber(Integer.parseInt(getStringParameter(request, "gender")));
        s.setDob(getStringParameter(request, "dob"));
        s.setRemark(getStringParameter(request, "remark"));

        byte[] pictW = (byte[]) getNonFormFieldObject(request, "headSet");

        if (pictW != null && pictW.length != 0) {
            s.setPhoto(pictW);
        }

        StudentDao.writeStudentObjectToDatabase(s);
    }

    @Override
    protected Entity readEntityObject(HttpServletRequest request, String id) {
        return StudentDao.getStudentObjectThroughDB(id);
    }
}
