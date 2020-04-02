package ac.adproj.scms.servlet.base;

import ac.adproj.scms.servlet.forms.MultipartFormHandler;
import ac.adproj.scms.servlet.forms.MultipartFormHandlerFactory;

import javax.servlet.http.HttpServletRequest;

public abstract class MultiPartFormControllerBase extends DetailedFormControllerBase {
    MultipartFormHandler mpf = null;

    @Override
    protected void beforeDoingPOST(HttpServletRequest request) {
        mpf = MultipartFormHandlerFactory.getFormHandler(request);
    }

    protected String getStringParameter(HttpServletRequest request, String key) {
        return mpf.getStringParameter(key);
    }

    @Override
    protected String getParameter(HttpServletRequest request, String key) {
        return getStringParameter(request, key);
    }

    protected Object getNonFormFieldObject(HttpServletRequest request, String key) {
        return mpf.getNonFormFieldObject(key);
    }
}
