package ru.job4j.persistence;

import ru.job4j.model.Advertisement;
import ru.job4j.model.Image;
import ru.job4j.model.User;

import java.util.List;

public class AdvertisementDao extends CommonDao {
    private static final String ERROR_MESSAGE_FIND_BY_ID = "Поиск объявления по id";
    private final ConnectionPool connectionPool = ConnectionPool.getInstance();
    private final ImageDao imageDao = ImageDao.getInstance();
    private static final AdvertisementDao INSTANCE = new AdvertisementDao();
    private static final String ERROR_MESSAGE_ADD = "Смотри в добавление новой задачи";
    private static final String ERROR_MESSAGE_FIND_ALL = "Смотри в получение списка всех заданий";
    private static final String ERROR_MESSAGE_DELETE = "Смотри в удаление записи";
    private static final String ERROR_MESSAGE_UPDATE = "Смотри в обновление записи";
    private static final String QUERY_ALL_ITEMS = "From Advertisement";

    private AdvertisementDao() {

    }

    public static AdvertisementDao getInstance() {
        return INSTANCE;
    }

    @Override
    public <T> int add(T advert) {
        return transactionWithResult(
                session -> {
                    return (int) session.save(advert);
                },
                connectionPool,
                ERROR_MESSAGE_ADD
        );
    }

    public Advertisement find(Advertisement advertisement) {
        return transactionWithResult(
                session -> {
                    return session.get(Advertisement.class, advertisement.getId());
                },
                connectionPool,
                ERROR_MESSAGE_FIND_BY_ID
        );
    }

    public void update(Advertisement advert) {
        transactionWithOutResult(session -> session.update(advert),
                connectionPool,
                ERROR_MESSAGE_UPDATE
        );
    }

    public void delete(Advertisement advert) {
        transactionWithOutResult(session -> {
                    imageDao.delete(new Image(advert.getId()));
                    session.delete(advert);
                },
                connectionPool,
                ERROR_MESSAGE_DELETE
        );
    }

    public List<Advertisement> findAll() {
        return transactionWithResult(
                session -> session.createQuery(QUERY_ALL_ITEMS).list(),
                connectionPool,
                ERROR_MESSAGE_FIND_ALL
        );
    }
}
