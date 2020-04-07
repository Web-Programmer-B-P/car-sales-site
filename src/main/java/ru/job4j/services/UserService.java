package ru.job4j.services;

import ru.job4j.model.User;
import ru.job4j.persistence.UserDao;
import java.util.List;

public class UserService {
    private final UserDao userDao = UserDao.getInstance();
    private static final UserService INSTANCE = new UserService();

    private UserService() {

    }

    public static UserService getInstance() {
        return INSTANCE;
    }

    public int add(User user) {
        return userDao.add(user);
    }

    public String findPhone(int id) {
        return userDao.find(new User(id)).getPhone();
    }

    public boolean isUserExist(User user) {
        return userDao.findByPasswordAndEmail(user).size() > 0;
    }

    public User findByEmailAndPassword(String email, String password) {
        User user = null;
        List<User> foundUsers = userDao.findByPasswordAndEmail(new User(email, password));
        if (foundUsers.size() > 0) {
            user = foundUsers.get(0);
        }
        return user;
    }
}
