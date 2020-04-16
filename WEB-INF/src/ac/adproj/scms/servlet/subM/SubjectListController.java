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

import ac.adproj.scms.dao.CourseDAO;
import ac.adproj.scms.servlet.ControllerStatusEnum;
import ac.adproj.scms.servlet.ServletProcessingException;
import ac.adproj.scms.servlet.base.list.ListFormControllerBase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.Set;

public class SubjectListController extends ListFormControllerBase {

    @Override
    protected void deleteDatabaseEntry(String id)
            throws SQLException {
        CourseDAO.deleteObject(id);
    }

    @Override
    protected void onPartialSuccess(HttpServletRequest request, HttpServletResponse response, Set<String> undoneIDs) {
        StringBuilder undoneMessageBuilder = new StringBuilder();
        undoneMessageBuilder.append("如下课程由于此前输入了成绩，不能删除。\\n\\n课程号：\\n");

        for (String id : undoneIDs) {
            undoneMessageBuilder.append(id);
            undoneMessageBuilder.append("\\n");
        }

        try (Writer out = response.getWriter()) {
            out.write("<script>alert(\"" + new String(undoneMessageBuilder.toString().getBytes(), StandardCharsets.UTF_8) + "\"); location.href=\"index.jsp\";</script>");
        } catch (IOException e) {
            e.printStackTrace();
            status = ControllerStatusEnum.FAIL;
            throw new ServletProcessingException(e);
        }
    }

    @Override
    protected void onSuccess(HttpServletRequest request, HttpServletResponse response) {
        try (Writer out = response.getWriter()) {
            out.write(new String(
                    ("<script>alert(\"删除成功! \");"
                            + "location.href=\"index.jsp\";</script>").getBytes()
                    , StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
            status = ControllerStatusEnum.FAIL;
            throw new ServletProcessingException(e);
        }
    }
}
