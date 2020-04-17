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

import ac.adproj.scms.util.ByteArrayUtils;
import com.google.gson.JsonObject;

public class Student implements Entity {
    private final String id;
    private String name;
    private String dob;
    private GenderEnum gender;
    private String major;
    private int totalCredits;
    private String remark;
    private byte[] photo;

    public Student(String id) {
        super();
        this.id = id;
    }

    public Student(String name, String dob, GenderEnum gender, String id, byte[] photo
            , String major, int totalCredits, String remark) {
        this(id);
        this.name = name;
        this.dob = dob;
        this.gender = gender;
        this.photo = photo;
        this.major = major;
        this.totalCredits = totalCredits;
        this.remark = remark;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public GenderEnum getGender() {
        return gender;
    }

    public void setGender(GenderEnum gender) {
        this.gender = gender;
    }

    public void setGenderThroughNumber(int number) {
        this.gender = GenderEnum.getGenderEnumThroughNumber(number);
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public boolean isFullyObtained() {
        return getId() != null && getName() != null && getMajor() != null;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public int getTotalCredits() {
        return totalCredits;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public int hashCode() {
        return id == null ? 0 : id.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null) {
            return false;
        }

        if (this.getClass() == obj.getClass()) {
            Student other = (Student) obj;
            if (this.getId() != null) {
                return this.getId().equals(other.getId());
            }
        }

        return false;
    }

    @Override
    public JsonObject getJSON() {
        JsonObject jso = new JsonObject();
        jso.addProperty("name", getName());
        jso.addProperty("gender", getGender().toString());
        jso.addProperty("id", getId());
        jso.addProperty("dob", getDob());
        jso.addProperty("major", getMajor());
        jso.addProperty("remark", getRemark());
        jso.addProperty("totalCredits", getTotalCredits());
        jso.addProperty("photo", ByteArrayUtils.convertByteArrayToString(getPhoto()));
        return jso;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Student{");
        sb.append("name='").append(name).append('\'');
        sb.append(", dob='").append(dob).append('\'');
        sb.append(", gender=").append(gender);
        sb.append(", id='").append(id).append('\'');
        sb.append(", major='").append(major).append('\'');
        sb.append(", totalCredits=").append(totalCredits);
        sb.append(", remark='").append(remark).append('\'');
        sb.append('}');
        return sb.toString();
    }
}