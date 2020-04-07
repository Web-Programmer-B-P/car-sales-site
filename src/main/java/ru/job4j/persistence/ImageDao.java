package ru.job4j.persistence;

import org.hibernate.query.Query;
import ru.job4j.model.Image;

import java.util.List;

public class ImageDao extends CommonDao {
    private static final String QUERY_FIND_IMAGES_BY_ADVERTISEMENT_ID = "From Image WHERE advertisementId=:advertisementId";
    private static final ImageDao INSTANCE = new ImageDao();
    private static final String ERROR_MESSAGE_ADD = "Смотри в добавление новой задачи";
    private static final String ERROR_MESSAGE_FIND_ALL = "Смотри в получение списка всех изображений";
    private static final String ERROR_MESSAGE_FIND_BY_ADVERTISEMENT_ID = "Смотри в изображения по ключу родителя";
    private static final String ERROR_MESSAGE_DELETE = "Смотри в удаление изображения";
    private static final String ERROR_MESSAGE_UPDATE = "Смотри в обновление изображения";
    private static final String QUERY_ALL_IMAGES = "From Image";
    private final ConnectionPool connectionPool = ConnectionPool.getInstance();

    private ImageDao() {

    }

    public static ImageDao getInstance() {
        return INSTANCE;
    }

    @Override
    public <T> int add(T image) {
        return transactionWithResult(
                session -> {
                    return (int) session.save(image);
                },
                connectionPool,
                ERROR_MESSAGE_ADD
        );
    }

    public void update(Image image) {
        transactionWithOutResult(session -> session.update(image),
                connectionPool,
                ERROR_MESSAGE_UPDATE
        );
    }

    public void delete(Image image) {
        transactionWithOutResult(session -> session.delete(image),
                connectionPool,
                ERROR_MESSAGE_DELETE
        );
    }

    public List<Image> findAll() {
        return transactionWithResult(
                session -> session.createQuery(QUERY_ALL_IMAGES).list(),
                connectionPool,
                ERROR_MESSAGE_FIND_ALL
        );
    }

    public Image find(Image image) {
        return transactionWithResult(
                session -> session.get(Image.class, image.getId()),
                connectionPool,
                ERROR_MESSAGE_FIND_ALL
        );
    }

    public List<Image> findImageByAdvertisementId(int id) {
        return transactionWithResult(
                session -> {
                    Query query = session.createQuery(QUERY_FIND_IMAGES_BY_ADVERTISEMENT_ID);
                    query.setParameter("advertisementId", id);
                    return query.list();
                },
                connectionPool,
                ERROR_MESSAGE_FIND_BY_ADVERTISEMENT_ID
        );
    }
}
