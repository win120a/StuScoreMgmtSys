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

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import javax.servlet.http.HttpServletRequest;

import ac.adproj.scms.servlet.ServletProcessingException;

public class MultipartFormHandlerFactory {
    private static final String FORM_HANDLER_CLASS_NAME = "ac.adproj.scms.servlet.forms.MultipartFormHandlerApacheCommonsImpl";

    public static MultipartFormHandler getFormHandler(HttpServletRequest request) {
        
        File tempdir = (File) request.getServletContext().getAttribute("javax.servlet.context.tempdir");

        try {
            @SuppressWarnings("unchecked")
            Class<MultipartFormHandler> handlerClass = (Class<MultipartFormHandler>) Class
                    .forName(FORM_HANDLER_CLASS_NAME);

            Constructor<MultipartFormHandler> ctor = handlerClass.getConstructor(HttpServletRequest.class,
                    String.class);

            return (MultipartFormHandler) ctor.newInstance(request, tempdir.getAbsolutePath());

        } catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException
                | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {

            e.printStackTrace();
            throw new ServletProcessingException(e);
        }
    }
}
