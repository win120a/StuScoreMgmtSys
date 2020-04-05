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

package ac.adproj.scms.servlet.forms;

import ac.adproj.scms.servlet.ServletProcessingException;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Simple factory of Multipart Form Handler.
 *
 * @author Andy Cheung
 */
public class MultipartFormHandlerFactory {
    /**
     * Class name of the handler.
     */
    private static final String FORM_HANDLER_CLASS_NAME = "ac.adproj.scms.servlet.forms.MultipartFormHandlerApacheCommonsImpl";

    /**
     * Factory method of form handler.
     *
     * @param request The HTTP Request.
     * @return Instance of the class which name is the value of {@link #FORM_HANDLER_CLASS_NAME}.
     * @author Andy Cheung
     * @see #FORM_HANDLER_CLASS_NAME
     */
    public static MultipartFormHandler getFormHandler(HttpServletRequest request) {

        File tempdir = (File) request.getServletContext().getAttribute("javax.servlet.context.tempdir");

        try {
            @SuppressWarnings("unchecked")
            Class<MultipartFormHandler> handlerClass = (Class<MultipartFormHandler>) Class
                    .forName(FORM_HANDLER_CLASS_NAME);

            Constructor<MultipartFormHandler> ctor = handlerClass.getConstructor(HttpServletRequest.class,
                    String.class);

            return ctor.newInstance(request, tempdir.getAbsolutePath());

        } catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException
                | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {

            e.printStackTrace();
            throw new ServletProcessingException(e);
        }
    }
}
