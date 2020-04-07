package ru.job4j.persistence;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;

abstract public class CommonDao {
    private static final Logger LOG = LogManager.getLogger(CommonDao.class.getName());

    public abstract <T> int add(T entity);

    public static  <T> T transactionWithResult(Function<Session, T> command, ConnectionPool connectionPool, String message) {
        T result = null;
        Transaction transaction = null;
        try (Session session = connectionPool.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            result = command.apply(session);
            transaction.commit();
        } catch (Exception e) {
            LOG.error(message, e);
            if (Objects.nonNull(transaction)) {
                transaction.rollback();
            }
        }
        return result;
    }

    public static void transactionWithOutResult(final Consumer<Session> command, ConnectionPool connectionPool, String message) {
        Transaction transaction = null;
        try (final Session session = connectionPool.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            command.accept(session);
            transaction.commit();
        } catch (Exception e) {
            if (Objects.nonNull(transaction)) {
                transaction.rollback();
            }
            LOG.error(message, e);
        }
    }
}
