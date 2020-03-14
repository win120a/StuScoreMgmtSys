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