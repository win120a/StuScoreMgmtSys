package ac.adproj.scms.servlet.subM;

import ac.adproj.scms.dao.CourseDao;
import ac.adproj.scms.entity.Course;
import ac.adproj.scms.entity.Entity;
import ac.adproj.scms.servlet.base.TextOnlyFormControllerBase;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class SubjectInformationController extends TextOnlyFormControllerBase {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        setViewURL("/WEB-INF/subM/subInfo.jsp");
        super.doGet(req, resp);
    }

    @Override
    protected void uploadObjectToDataBase(HttpServletRequest request) throws SQLException {
        Course c = new Course(getParameter(request, "id"));

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
