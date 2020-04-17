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

package ac.adproj.scms.taglib;

import ac.adproj.scms.entity.Entity;
import ac.adproj.scms.entity.Student;
import ac.adproj.scms.entity.StudentScore;
import ac.adproj.scms.servlet.ServletProcessingException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;

public class DataRowsTag extends SimpleTagSupport {
    private String content;
    private String type;

    private static final String ENTITY_PKG_NAME = "ac.adproj.scms.entity";

    public void setContent(String content) {
        this.content = content;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public void doTag() throws JspException, IOException {
        Set<Entity> entitySet = (Set<Entity>) getJspContext().getAttribute("entitySet");

        JspWriter writer = getJspContext().getOut();

        for(Entity e : entitySet) {
            try {
                Class<?> typeClass = Class.forName(ENTITY_PKG_NAME + "." +
                                                    type.substring(0, 1).toUpperCase() + type.substring(1));

                Class<?> targetClass = e.getClass().asSubclass(typeClass);

                String methodName = "get" + content.substring(0, 1).toUpperCase() + content.substring(1);
                Method getter = targetClass.getMethod(methodName);
                getJspContext().setAttribute("current", getter.invoke(e));
            } catch (ClassNotFoundException | NoSuchMethodException |
                    IllegalAccessException | InvocationTargetException | ClassCastException ex) {
                ex.printStackTrace();
                throw new ServletProcessingException(ex);
            }

            writer.print("<td>");

            getJspBody().invoke(null);

            writer.print("</td>");
        }
    }
}
