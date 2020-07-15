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

package ac.adproj.scms.servlet.multipartform;

import ac.adproj.scms.servlet.ServletProcessingException;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The Apache Commons (commons-fileupload.jar) implementation of
 * MultipartFormHandler.
 *
 * @author Andy Cheung
 */
class MultipartFormHandlerApacheCommonsImpl implements MultipartFormHandler {
    /**
     * The Map of storing form contents.
     */
    private final Map<String, DataWrap> formContents;

    /**
     * Constructs the MultipartForm Object from HttpServletRequest object.
     *
     * @param request The request from client.
     * @param tempdir The temporary directory.
     */
    public MultipartFormHandlerApacheCommonsImpl(HttpServletRequest request, String tempdir)
            throws UnsupportedEncodingException {

        this.formContents = getFormContents(request, tempdir);
    }

    /**
     * Core method to gather the multipart/form-data form's data.
     */
    private static Map<String, DataWrap> getFormContents(HttpServletRequest request, String tempdir)
            throws UnsupportedEncodingException {

        HashMap<String, DataWrap> contents = new HashMap<>();

        if (!ServletFileUpload.isMultipartContent(request)) {
            throw new IllegalArgumentException();
        }

        // Create a factory for disk-based file items
        DiskFileItemFactory factory = new DiskFileItemFactory();

        // Configure a repository (to ensure a secure temp location is used)
        File repository = new File(tempdir);
        factory.setRepository(repository);

        // Create a new file upload handler
        ServletFileUpload upload = new ServletFileUpload(factory);

        // Parse the request
        try {
            List<FileItem> items = upload.parseRequest(request);

            for (FileItem fi : items) {
                if (fi.isFormField()) {
                    DataWrap dw = new DataWrap(fi.getString("utf-8"), true);
                    contents.put(fi.getFieldName(), dw);
                } else {
                    DataWrap dw = new DataWrap(fi.get(), false);
                    contents.put(fi.getFieldName(), dw);
                }
            }
        } catch (FileUploadException e) {
            e.printStackTrace();
            throw new ServletProcessingException(e);
        }

        return contents;
    }

    @Override
    public Object getNonFormFieldObject(String key) {
        if (formContents.get(key) == null) {
            return null;
        }

        if (formContents.get(key).isFormField()) {
            throw new IllegalArgumentException();
        }

        return formContents.get(key).getObject();
    }

    @Override
    public String getStringParameter(String key) {
        if (formContents.get(key) == null) {
            return null;
        }

        if (!formContents.get(key).isFormField()) {
            throw new IllegalArgumentException();
        }

        return (String) formContents.get(key).getObject();
    }

    /**
     * A simple Data Wrapper class to wrap a form entry.
     *
     * @author Andy Cheung
     */
    private static class DataWrap {
        private final Object object;
        private final boolean formField;

        public DataWrap(Object object, boolean formField) {
            super();
            this.object = object;
            this.formField = formField;
        }

        public Object getObject() {
            return object;
        }

        public boolean isFormField() {
            return formField;
        }
    }
}