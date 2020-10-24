package osipov.evgeny.repository;

import org.hibernate.Session;
import org.hibernate.query.Query;
import osipov.evgeny.entity.Freelancer;
import osipov.evgeny.util.HibernateUtil;

import java.util.List;

/**
 * Интерфейс для обращения к базе данных. Реализует запросы к таблице "freelancer".
 */
public interface FreelanceRepo {
    /**
     * Метод для сохранения объекта исполнителя в базе данных.
     *
     * @param freelancer объект исполнителя, передается с единственным незаполненным полем - id
     */
    static void setFreelancer(Freelancer freelancer) {
        try (Session session = HibernateUtil.getSession()) {
            session.beginTransaction();
            session.save(freelancer);
            session.getTransaction().commit();
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Метод для обновления данных объекта исполнителя в базе данных.
     *
     * @param freelancer объект исполнителя
     */
    static void updateFreelancer(Freelancer freelancer) {
        try (Session session = HibernateUtil.getSession()) {
            session.beginTransaction();
            session.update(freelancer);
            session.getTransaction().commit();
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Метод для удаления данных объекта исполнителя в базе данных.
     *
     * @param freelancer объект исполнителя
     */
    static void deleteFreelancer(Freelancer freelancer) {
        try (Session session = HibernateUtil.getSession()) {
            session.beginTransaction();
            session.delete(freelancer);
            session.getTransaction().commit();
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Метод для поиска объекта исполнителя в базе данных по инентификатору [ id ].
     * Возврашает найденный объект или объект с полями, равными null.
     *
     * @param resultingIdValue предаваемое значение идентификатора
     */
    static Freelancer getFreelancerByIdOrEmptyEntity(Long resultingIdValue) {
        Freelancer freelancer = new Freelancer();
        try (Session session = HibernateUtil.getSession()) {
            session.beginTransaction();
            freelancer = session.find(Freelancer.class, resultingIdValue);
            session.getTransaction().commit();
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
        return freelancer;
    }

    /**
     * Метод для поиска объекта исполнителя в базе данных по имени пользователя [ username ].
     * Возврашает найденный объект или объект с полями, равными null.
     *
     * @param freelancerUsername предаваемое значение имени пользователя
     */
    static Freelancer getFreelancerByUsernameOrEmptyEntity(String freelancerUsername) {
        Freelancer freelancer = new Freelancer();
        try (Session session = HibernateUtil.getSession()) {
            session.beginTransaction();
            Query query = session.createQuery("from Freelancer where username = :username");
            query.setParameter("username", freelancerUsername);
            List freelancerList = query.list();
            if (freelancerList.size() == 1) {
                freelancer = (Freelancer) freelancerList.get(0);
            } else if (freelancerList.size() > 1) {
                System.out.println("Error: [ Freelancer ] more than one entity with the same name [ "
                        + freelancerUsername + " ]!");
            }
            session.getTransaction().commit();
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
        return freelancer;
    }
}
