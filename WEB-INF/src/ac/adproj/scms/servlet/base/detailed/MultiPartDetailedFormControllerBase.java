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

package ac.adproj.scms.servlet.base.detailed;

import ac.adproj.scms.servlet.multipartform.MultipartFormHandler;
import ac.adproj.scms.servlet.multipartform.MultipartFormHandlerFactory;

import javax.servlet.http.HttpServletRequest;

public abstract class MultiPartDetailedFormControllerBase extends DetailedFormControllerBase {
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
