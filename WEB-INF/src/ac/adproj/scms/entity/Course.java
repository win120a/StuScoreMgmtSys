package ac.adproj.scms.entity;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class Course implements Entity {
    private String id;
    private String name;
    private String term;
    private int courseHours;
    private int credits;

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
    public JsonElement getJSON() {
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
