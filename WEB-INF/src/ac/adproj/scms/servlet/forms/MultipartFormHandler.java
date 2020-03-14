package ac.adproj.scms.servlet.forms;

/**
 * Represents a multipart form handler.
 * 
 * @author Andy Cheung
 */
public interface MultipartFormHandler {

    /**
     * Get a non-form field value (e.g. File).
     * 
     * @author Andy Cheung
     */
    Object getNonFormFieldObject(String key);

    /**
     * Get a form field value.
     * 
     * @param key The key to the value in the multipart form.
     * @author Andy Cheung
     */
    String getStringParameter(String key);

}