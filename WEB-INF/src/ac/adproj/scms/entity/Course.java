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

import com.google.gson.JsonObject;

public class Course implements Entity {
    private String id;
    private String name;
    private String term;
    private int courseHours;
    private int credits;

    public Course(String id) {
        this.id = id;
    }

    public Course(String id, String name, String term, int courseHours, int credits) {
        this.id = id;
        this.name = name;
        this.term = term;
        this.courseHours = courseHours;
        this.credits = credits;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public void setCourseHours(int courseHours) {
        this.courseHours = courseHours;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getTerm() {
        return term;
    }

    public int getCourseHours() {
        return courseHours;
    }

    public int getCredits() {
        return credits;
    }

    @Override
    public JsonObject getJSON() {
        JsonObject jso = new JsonObject();
        jso.addProperty("name", getName());
        jso.addProperty("id", getId());
        jso.addProperty("term", getTerm());
        jso.addProperty("courseHours", getCourseHours());
        jso.addProperty("credits", getCredits());
        return jso;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Course other = (Course) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }
}
