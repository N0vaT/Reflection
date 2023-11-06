package edu.school21.OrmManager.ormManager;

public interface OrmManager {
    void save(Object entity);
    void update(Object entity);
    <T> T findById(Long id, Class<T> aClass);
}
