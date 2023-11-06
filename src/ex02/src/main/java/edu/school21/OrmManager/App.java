package edu.school21.OrmManager;

import edu.school21.OrmManager.models.User;
import edu.school21.OrmManager.models.UserWithoutAnnotation;
import edu.school21.OrmManager.ormManager.OrmManager;
import edu.school21.OrmManager.ormManager.OrmManagerImpl;

public class App {
    public static void main(String[] args) {
        User user = new User();
        OrmManager ormManager = new OrmManagerImpl();
        UserWithoutAnnotation userTest = new UserWithoutAnnotation();
        ormManager.save(user);
        ormManager.update(user);
        ormManager.findById(1L, user.getClass());
    }
}
