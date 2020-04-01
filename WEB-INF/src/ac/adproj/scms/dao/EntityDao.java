package ac.adproj.scms.dao;

import ac.adproj.scms.entity.Entity;

public interface EntityDao {
    Entity getEntityObjectThroughDB();

    void writeEntityObjectToDatabase();
}
