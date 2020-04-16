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

package ac.adproj.scms.service.photo;

import ac.adproj.scms.servlet.InitServlet;
import ac.adproj.scms.servlet.ServletProcessingException;

import javax.servlet.ServletContext;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Simple factory of Photo Service.
 *
 * @author Andy Cheung
 */
public final class PhotoServiceFactory {
    /**
     * Factory method of form handler.
     *
     * @param context The servlet context.
     * @return Instance of the class.
     * @author Andy Cheung
     */
    public static PhotoService getPhotoService(ServletContext context) {
        final String CLASSNAME = context.getInitParameter("photoServiceClass");

        PhotoService service = null;

        try {
            @SuppressWarnings("unchecked")
            Class<PhotoService> photoServiceClass = (Class<PhotoService>) Class.forName(CLASSNAME);
            Constructor<PhotoService> ctor = photoServiceClass.getConstructor(ServletContext.class);
            service = ctor.newInstance(context);
        } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException
                | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            throw new ServletProcessingException(e);
        }

        return service;
    }

    public static PhotoService getPhotoService() {
        return getPhotoService(InitServlet.context);
    }
}
