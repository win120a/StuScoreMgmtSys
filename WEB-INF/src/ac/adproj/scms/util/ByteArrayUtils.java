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

package ac.adproj.scms.util;

import java.util.ArrayList;

public final class ByteArrayUtils {
    private ByteArrayUtils() {
    }

    public static String convertByteArrayToString(byte[] arr) {
        StringBuilder sBuilder = new StringBuilder();
        for (byte b : arr) {
            sBuilder.append(b);
            sBuilder.append(',');
        }
        sBuilder.deleteCharAt(sBuilder.length() - 1);
        return sBuilder.toString();
    }

    public static byte[] convertStringToByteArray(String str) {
        String[] splitArr = str.split("(\\s)*,(\\s)*");
        ArrayList<Byte> listOfByte = new ArrayList<>(splitArr.length);

        for (String s : splitArr) {
            listOfByte.add(Byte.parseByte(s));
        }

        byte[] bA = new byte[splitArr.length];
        for (int i = 0; i < listOfByte.size(); i++) {
            bA[i] = listOfByte.get(i);
        }

        return bA;
    }
}
