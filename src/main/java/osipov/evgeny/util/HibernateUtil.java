package osipov.evgeny.util;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import osipov.evgeny.controller.UserAndOrderController;

/**
 * Класс-утилита для работы с базой данных.
 */
public class HibernateUtil {

    /**
     * Поле объявления переменной для логгирования.
     */
    private static final Logger log = Logger.getLogger(UserAndOrderController.class.getName());
    /**
     * Фабрика сессий, для подключения к базе данных.
     */
    private static final SessionFactory sessionFactory;

    /**
     * Настройка Hibernate для соединения с базой данных.
     */
    static {
        try {
            Configuration configuration = new Configuration();
            configuration.configure("hibernate.cfg.xml");
            sessionFactory = configuration.buildSessionFactory();
        } catch (Throwable ex) {
            log.error("Database connection error: " + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    /**
     * Метод возвращает новую сессию для взаимодействия с базой данных.
     *
     * @return открытая сессия (подключение)
     * @throws HibernateException бросает exception при ошибках соединения с базой данных.
     */
    public static Session getSession() throws HibernateException {
        return sessionFactory.openSession();
    }
}
