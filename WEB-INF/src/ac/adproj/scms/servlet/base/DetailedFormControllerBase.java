package ac.adproj.scms.servlet.base;

import ac.adproj.scms.servlet.ServletProcessingException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.sql.SQLException;

public abstract class DetailedFormControllerBase extends HttpServlet {
    protected abstract void uploadObjectToDataBase(HttpServletRequest request) throws SQLException;
    protected abstract String getParameter(HttpServletRequest request, String key);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");

        try {
            Writer out = response.getWriter();

            String typeString = getParameter(request, "type");

            if (typeString == null || typeString.isEmpty()) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Unknown Type.");
                return;
            }

            RecordModificationTypeEnum type = RecordModificationTypeEnum
                                            .getEnumByTypeName(typeString);

            uploadObjectToDataBase(request);

            out.write("<script>");

            if (type == RecordModificationTypeEnum.ADD) {
                out.write("window.alert('添加成功! ');");
            } else if (type == RecordModificationTypeEnum.MODIFY) {
                out.write("window.alert('修改成功! ');");
            }

            out.write("opener.location.reload();");
            out.write("window.opener = null;");
            out.write("window.close();");
            out.write("</script>");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ServletProcessingException(e);
        }
    }
}
