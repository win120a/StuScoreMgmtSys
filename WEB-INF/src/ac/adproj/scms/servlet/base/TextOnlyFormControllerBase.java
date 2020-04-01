package ac.adproj.scms.servlet.base;

import javax.servlet.http.HttpServletRequest;

public abstract class TextOnlyFormControllerBase extends DetailedFormControllerBase {
    @Override
    protected String getParameter(HttpServletRequest request, String key) {
        return request.getParameter(key);
    }
}
