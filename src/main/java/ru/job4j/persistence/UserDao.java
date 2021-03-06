package ru.job4j.persistence;

import org.hibernate.query.Query;
import ru.job4j.model.User;
import java.util.List;

public class UserDao extends CommonDao {
    private static final String ERROR_MESSAGE_FIND_BY_EMAIL_AND_PASSWORD = "Поиск пользователя по паролю и почте";
    private static final String FIND_USER_BY_EMAIL_AND_PASSWORD = "from User where email=:email and password=:password";
    private static final String ERROR_MESSAGE_FIND_BY_ID = "Смотри в метод поиска пользователя по id";
    private final ConnectionPool connectionPool = ConnectionPool.getInstance();
    private static final UserDao INSTANCE = new UserDao();
    private static final String ERROR_MESSAGE_ADD = "Смотри в добавление новой задачи";

    private UserDao() {

    }

    public static UserDao getInstance() {
        return INSTANCE;
    }

    @Override
    public <T> int add(T user) {
        return transactionWithResult(
                session -> {
                    return (int) session.save(user);
                },
                connectionPool,
                ERROR_MESSAGE_ADD
        );
    }

    public User find(User user) {
        return transactionWithResult(
                session -> {
                    return session.get(User.class, user.getId());
                },
                connectionPool,
                ERROR_MESSAGE_FIND_BY_ID
        );
    }

    public List<User> findByPasswordAndEmail(User user) {
        return transactionWithResult(
                session -> {
                    Query query = session.createQuery(FIND_USER_BY_EMAIL_AND_PASSWORD);
                    query.setParameter("email", user.getEmail());
                    query.setParameter("password", user.getPassword());
                    return query.list();
                },
                connectionPool,
                ERROR_MESSAGE_FIND_BY_EMAIL_AND_PASSWORD
        );
    }
}
