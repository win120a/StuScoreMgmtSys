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

package ac.adproj.scms.servlet.base.detailed;

import ac.adproj.scms.entity.Entity;
import ac.adproj.scms.servlet.ServletProcessingException;
import ac.adproj.scms.servlet.base.ControllerBase;
import ac.adproj.scms.servlet.base.RecordModificationTypeEnum;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;

public abstract class DetailedFormControllerBase extends ControllerBase {
    private String viewUrl = null;

    protected abstract void uploadObjectToDataBase(HttpServletRequest request) throws SQLException, IOException;

    protected abstract String getParameter(HttpServletRequest request, String key);

    protected abstract Entity readEntityObject(HttpServletRequest request, String id);

    protected void beforeDoingPOST(HttpServletRequest request) {
    }

    protected void afterDoPOST(HttpServletRequest request) {
    }

    public String getViewUrl() {
        return viewUrl;
    }

    public void setViewUrl(String dispatchTo) {
        this.viewUrl = dispatchTo;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        if (viewUrl == null) {
            throw new IllegalArgumentException();
        }

        String id = req.getParameter("id");
        Entity e = readEntityObject(req, id);
        req.setAttribute("entityObject", e);
        req.getRequestDispatcher(getViewUrl()).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        super.doPost(request, response);

        try {
            Writer out = response.getWriter();

            beforeDoingPOST(request);

            String typeString = getParameter(request, "type");

            if (typeString == null || typeString.isEmpty()) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Unknown Type.");
                return;
            }

            RecordModificationTypeEnum type = RecordModificationTypeEnum
                    .getEnumByTypeName(typeString);

            uploadObjectToDataBase(request);

            out.write("<script>");

            switch (type) {
                case ADD:
                    out.write(new String("window.alert('添加成功! ');".getBytes(), StandardCharsets.UTF_8));
                    break;
                case MODIFY:
                    out.write(new String("window.alert('修改成功! ');".getBytes(), StandardCharsets.UTF_8));
                    break;
                case UPDATE:
                    out.write(new String("window.alert('更新成绩成功! ');".getBytes(), StandardCharsets.UTF_8));
                    break;
                default:
                    throw new IllegalArgumentException("[" + getClass().getName() + "] Illegal Type. ");
            }

            out.write("opener.location.reload();");
            out.write("window.opener = null;");
            out.write("window.close();");
            out.write("</script>");

            afterDoPOST(request);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ServletProcessingException(e);
        }
    }
}
