package osipov.evgeny.repository;

import org.hibernate.Session;
import org.hibernate.query.Query;
import osipov.evgeny.entity.Customer;
import osipov.evgeny.util.HibernateUtil;

import java.util.List;

public interface CustomerRepo {

    static void setCustomer(Customer customer) {
        try (Session session = HibernateUtil.getSession()) {
            session.beginTransaction();
            session.save(customer);
            session.getTransaction().commit();
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
    }

    static void updateCustomer(Customer customer) {
        try (Session session = HibernateUtil.getSession()) {
            session.beginTransaction();
            session.update(customer);
            session.getTransaction().commit();
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
    }

    static void deleteCustomer(Customer customer) {
        try (Session session = HibernateUtil.getSession()) {
            session.beginTransaction();
            session.delete(customer);
            session.getTransaction().commit();
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
    }

    static Customer getCustomerByIdOrEmptyEntity(Long resultingIdValue) {
        Customer customer = new Customer();
        try (Session session = HibernateUtil.getSession()) {
            session.beginTransaction();
            customer = session.find(Customer.class, resultingIdValue);
            session.getTransaction().commit();
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
        return customer;
    }

    static Customer getCustomerByCustomerNameOrEmptyEntity(String customerName) {
        Customer customer = new Customer();
        try (Session session = HibernateUtil.getSession()) {
            session.beginTransaction();
            Query query = session.createQuery("from Customer where customer_name = :customName");
            query.setParameter("customName", customerName);
            List customerList = query.list();
            if (customerList.size() == 1) {
                customer = (Customer) customerList.get(0);
            } else if (customerList.size() > 1) {
                System.out.println("Error: [ Customer ] more than one entity with the same name [ " + customerName + " ]!");
            }
            session.getTransaction().commit();
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
        return customer;
    }
}
