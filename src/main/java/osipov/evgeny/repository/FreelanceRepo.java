package osipov.evgeny.repository;

import org.hibernate.Session;
import org.hibernate.query.Query;
import osipov.evgeny.entity.Freelancer;
import osipov.evgeny.util.HibernateUtil;

import java.util.List;

public interface FreelanceRepo {

    static void setFreelancer(Freelancer freelancer) {
        try (Session session = HibernateUtil.getSession()) {
            session.beginTransaction();
            session.save(freelancer);
            session.getTransaction().commit();
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
    }

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
