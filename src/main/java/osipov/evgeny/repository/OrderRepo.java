package osipov.evgeny.repository;

import org.hibernate.Session;
import org.hibernate.query.Query;
import osipov.evgeny.entity.Order;
import osipov.evgeny.util.HibernateUtil;

import java.util.ArrayList;
import java.util.List;

public interface OrderRepo {

    static void setOrder(Order order) {
        try (Session session = HibernateUtil.getSession()) {
            session.beginTransaction();
            session.save(order);
            session.getTransaction().commit();
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
    }

    static void updateOrder(Order order) {
        try (Session session = HibernateUtil.getSession()) {
            session.beginTransaction();
            session.update(order);
            session.getTransaction().commit();
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
    }

    static void deleteOrder(Order order) {
        try (Session session = HibernateUtil.getSession()) {
            session.beginTransaction();
            session.delete(order);
            session.getTransaction().commit();
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
    }

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
