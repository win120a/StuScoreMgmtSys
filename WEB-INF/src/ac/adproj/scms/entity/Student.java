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
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class Student implements Entity {
    private String name;
    private String dob;
    private GenderEnum gender;
    private String id;
    private byte[] photo;

    public Student(String name, String dob, GenderEnum gender, String id, byte[] photo) {
        super();
        this.name = name;
        this.dob = dob;
        this.gender = gender;
        this.id = id;
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public String getDob() {
        return dob;
    }

    public GenderEnum getGender() {
        return gender;
    }

    public String getId() {
        return id;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public JsonElement getJSON() {
        JsonObject jso = new JsonObject();
        jso.addProperty("name", getName());
        jso.addProperty("gender", getGender().toString());
        jso.addProperty("id", getId());
        jso.addProperty("dob", getDob());
        jso.addProperty("photo", ByteArrayUtils.convertByteArrayToString(getPhoto()));
        return jso;
    }

}