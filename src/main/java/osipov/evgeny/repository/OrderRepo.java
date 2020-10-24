package osipov.evgeny.repository;

import org.hibernate.Session;
import org.hibernate.query.Query;
import osipov.evgeny.entity.Order;
import osipov.evgeny.util.HibernateUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Интерфейс для обращения к базе данных. Реализует запросы к таблице "orders".
 */
public interface OrderRepo {
    /**
     * Метод для сохранения объекта заказа в базе данных.
     *
     * @param order объект заказа, передается с единственным незаполненным полем - id
     */
    static void setOrder(Order order) {
        try (Session session = HibernateUtil.getSession()) {
            session.beginTransaction();
            session.save(order);
            session.getTransaction().commit();
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Метод для обновления данных объекта заказа в базе данных.
     *
     * @param order объект заказа
     */
    static void updateOrder(Order order) {
        try (Session session = HibernateUtil.getSession()) {
            session.beginTransaction();
            session.update(order);
            session.getTransaction().commit();
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Метод для удаления данных объекта заказа в базе данных.
     *
     * @param order объект заказа
     */
    static void deleteOrder(Order order) {
        try (Session session = HibernateUtil.getSession()) {
            session.beginTransaction();
            session.delete(order);
            session.getTransaction().commit();
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Метод для поиска объекта заказа в базе данных по инентификатору [ id ].
     * Возврашает найденный объект или объект с полями, равными null.
     *
     * @param resultingIdValue предаваемое значение идентификатора
     */
    static Order getOrderByIdOrEmptyEntity(Long resultingIdValue) {
        Order order = new Order();
        try (Session session = HibernateUtil.getSession()) {
            session.beginTransaction();
            order = session.find(Order.class, resultingIdValue);
            session.getTransaction().commit();
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
        return order;
    }

    /**
     * Метод для поиска объекта заказа в базе данных по идентификатора заказчика [ customer_id ].
     * Возврашает список объектов или пустой список.
     *
     * @param customerId предаваемое значение идентификатора
     */
    static List<Order> getOrdersByCustomerIdOrEmptyList(Long customerId) {
        List<Order> orders = new ArrayList<>();
        try (Session session = HibernateUtil.getSession()) {
            session.beginTransaction();
            Query query = session.createQuery("FROM Order WHERE customer_id =: custId");
            query.setParameter("custId", customerId);
            orders = query.getResultList();
            session.getTransaction().commit();
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
        return orders;
    }

    /**
     * Метод для поиска объекта заказа в базе данных по идентификатора исполнителя [ customer_id ].
     * Возврашает список объектов или пустой список.
     *
     * @param freelancerId предаваемое значение идентификатора
     */
    static List<Order> getOrdersByFreelancerIdOrEmptyList(Long freelancerId) {
        List<Order> orders = new ArrayList<>();
        try (Session session = HibernateUtil.getSession()) {
            session.beginTransaction();
            Query query = session.createQuery("FROM Order WHERE freelancer_id =: freeId");
            query.setParameter("freeId", freelancerId);
            orders = query.getResultList();
            session.getTransaction().commit();
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
        return orders;
    }

    /**
     * Метод для поиска всех объектов заказа в базе данных.
     * Возврашает список объектов или пустой список.
     */
    static List<Order> getAllOrdersOrEmptyList() {
        List<Order> orders = new ArrayList<>();
        try (Session session = HibernateUtil.getSession()) {
            session.beginTransaction();
            Query query = session.createQuery("FROM Order");
            orders = query.getResultList();
            session.getTransaction().commit();
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
        return orders;
    }
}
