package ac.adproj.scms.servlet.base;

import ac.adproj.scms.servlet.forms.MultipartFormHandler;
import ac.adproj.scms.servlet.forms.MultipartFormHandlerFactory;

import javax.servlet.http.HttpServletRequest;

public abstract class MultiPartFormControllerBase extends DetailedFormControllerBase {
    protected MultipartFormHandler getMultipartFormHandler(HttpServletRequest request) {
        MultipartFormHandler mpf = MultipartFormHandlerFactory.getFormHandler(request);
        return mpf;
    }

    protected String getStringParameter(HttpServletRequest request, String key) {
        return getMultipartFormHandler(request).getStringParameter(key);
    }

    @Override
    protected String getParameter(HttpServletRequest request, String key) {
        return getStringParameter(request, key);
    }

    protected Object getNonFormFieldObject(HttpServletRequest request, String key) {
        return getMultipartFormHandler(request).getNonFormFieldObject(key);
    }
}
