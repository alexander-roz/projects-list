package data;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.List;


public class EngineerRepository {
    private final SessionFactory sessionFactory;

    public EngineerRepository() {
        try {
            Configuration configuration = new Configuration();
            configuration.configure("hibernate.cfg.xml");
            sessionFactory = configuration.buildSessionFactory();
            System.out.println("H2 Database connected successfully");
            System.out.println("Database URL: jdbc:h2:file:./database/projects");
        } catch (Exception e) {
            System.err.println("Failed to initialize Hibernate SessionFactory: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to initialize Hibernate SessionFactory", e);
        }
    }

    public void saveEngineer(EngineerEntity engineer) {
        Transaction transaction = null;
        Session session = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.persist(engineer);
            transaction.commit();
            System.out.println("Saved engineer: " + engineer);
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Failed to save engineer: " + engineer.getEngineerName(), e);
        } finally {
            if (session != null) session.close();
        }
    }

    public List<EngineerEntity> findAllEngineers() {
        try (Session session = sessionFactory.openSession()) {
            Query<EngineerEntity> query = session.createQuery("from EngineerEntity", EngineerEntity.class);
            return query.getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve all projects", e);
        }
    }

    public void updateEngineer(EngineerEntity engineer) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.merge(engineer);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Failed to update engineer", e);
        }
    }

    public void deleteEngineer(int id) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            EngineerEntity engineer = session.getReference(EngineerEntity.class, id);
            if (engineer != null) {
                session.remove(engineer);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Failed to delete engineer", e);
        }
    }

    public EngineerEntity findEngineerByName(String name) {
        try (Session session = sessionFactory.openSession()) {
            Query<EngineerEntity> query = session.createQuery(
                    "FROM EngineerEntity WHERE engineerName = :name", EngineerEntity.class);
            query.setParameter("name", name);
            return query.uniqueResult();
        } catch (Exception e) {
            System.err.println("Error finding engineer: " + e.getMessage());
            return null;
        }
    }
}
