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

package ac.adproj.scms.entity;

public enum GenderEnum {
    MALE(1), FEMALE(0);

    private int genderN;

    GenderEnum(int genderN) {
        this.genderN = genderN;
    }

    public static GenderEnum getGenderEnumThroughNumber(int n) {
        if (n < 0 || n > 1) {
            throw new IllegalArgumentException();
        }

        return (n == 1) ? MALE : FEMALE;
    }

    public int getGenderNumber() {
        return genderN;
    }
}
