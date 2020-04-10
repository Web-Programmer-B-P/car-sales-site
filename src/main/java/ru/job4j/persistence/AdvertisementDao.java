package ru.job4j.persistence;

import org.hibernate.query.Query;
import ru.job4j.model.Advertisement;
import java.util.List;

public class AdvertisementDao extends CommonDao {
    private static final String ERROR_MESSAGE_FIND_BY_ID = "Поиск объявления по id";
    private static final String QUERY_BY_LAST_DATE = "select * from Advertisement where create_date >= (select date_trunc('day', max(advert.create_date)) from Advertisement as advert)";
    private static final String QUERY_BY_CRITERION = "select advert from Advertisement advert inner join advert.car car where car.carBody=:carBody";
    private final ConnectionPool connectionPool = ConnectionPool.getInstance();
    private static final AdvertisementDao INSTANCE = new AdvertisementDao();
    private static final String ERROR_MESSAGE_ADD = "Смотри в добавление новой задачи";
    private static final String ERROR_MESSAGE_FIND_ALL = "Смотри в получение списка всех заданий";
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

    public List<Advertisement> findAllByCriterion(String criterion) {
        return transactionWithResult(
                session -> {
                    Query query = session.createQuery(QUERY_BY_CRITERION);
                    query.setParameter("carBody", criterion);
                    return query.list();
                },
                connectionPool,
                ERROR_MESSAGE_FIND_ALL
        );
    }

    public List<Advertisement> findAllByLastDate() {
        return transactionWithResult(
                session -> session.createSQLQuery(QUERY_BY_LAST_DATE).addEntity(Advertisement.class).list(),
                connectionPool,
                ERROR_MESSAGE_FIND_ALL
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
