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

package ac.adproj.scms.servlet.base.list;

import ac.adproj.scms.servlet.ControllerStatusEnum;
import ac.adproj.scms.servlet.ServletProcessingException;
import ac.adproj.scms.servlet.base.ControllerBase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public abstract class ListFormControllerBase extends ControllerBase {
    protected abstract void deleteDBEntry(String id)
            throws SQLException;

    protected void onPartialSuccess(HttpServletRequest request,
                                    HttpServletResponse response,
                                    Set<String> undoneIDs) { }

    protected void onSuccess(HttpServletRequest request,
                             HttpServletResponse response) { }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");

        try {

            String delF = request.getParameter("del");
            HashSet<String> undoneIDs = new HashSet<>();
            boolean notFullyDel = false;

            if (delF != null) {
                Map<String, String[]> paramMap = request.getParameterMap();

                HashSet<String> delKeys = new HashSet<>();

                for (Map.Entry<String, String[]> s : paramMap.entrySet()) {
                    if ("del".equals(s.getKey())) {
                        continue;
                    }
                    if ("on".equals(s.getValue()[0].toLowerCase())) {
                        delKeys.add(s.getKey());
                    }
                }

                for (String s : delKeys) {
                    try {
                        deleteDBEntry(s);
                    } catch (java.sql.SQLIntegrityConstraintViolationException sicve) {
                        notFullyDel = true;
                        undoneIDs.add(s);
                    }
                }

                if (notFullyDel) {
                    status = ControllerStatusEnum.PARTIAL_SUCCESS;
                    onPartialSuccess(request, response, undoneIDs);
                } else {
                    status = ControllerStatusEnum.SUCCESS;
                    onSuccess(request, response);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            status = ControllerStatusEnum.FAIL;
            throw new ServletProcessingException(e);
        }
    }
}
