package data;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;

public class ProjectRepository {
    private final SessionFactory sessionFactory;

    public ProjectRepository() {
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

    // Все методы остаются без изменений
    public void save(ProjectEntity project) {
        Transaction transaction = null;
        Session session = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.persist(project);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw new RuntimeException("Ошибка при сохранении проекта", e);
        } finally {
            if (session != null) session.close();
        }
    }

    public Optional<ProjectEntity> findById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            ProjectEntity project = session.getReference(ProjectEntity.class, id);
            return Optional.ofNullable(project);
        } catch (Exception e) {
            throw new RuntimeException("Failed to find project by id: " + id, e);
        }
    }

    public List<ProjectEntity> findAll() {
        try (Session session = sessionFactory.openSession()) {
            Query<ProjectEntity> query = session.createQuery("FROM ProjectEntity", ProjectEntity.class);
            return query.getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve all projects", e);
        }
    }

    public void update(ProjectEntity project) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.merge(project);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Failed to update project", e);
        }
    }

    public void delete(int id) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            ProjectEntity project = session.getReference(ProjectEntity.class, id);
            if (project != null) {
                session.remove(project);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Failed to delete project", e);
        }
    }

    public Optional<ProjectEntity> findByDocumentCode(String documentCode) {
        try (Session session = sessionFactory.openSession()) {
            Query<ProjectEntity> query = session.createQuery(
                    "FROM ProjectEntity WHERE code = :documentCode", ProjectEntity.class);
            query.setParameter("documentCode", documentCode);
            return query.uniqueResultOptional();
        } catch (Exception e) {
            throw new RuntimeException("Failed to find project by document code: " + documentCode, e);
        }
    }

    public void close() {
        if (sessionFactory != null && !sessionFactory.isClosed()) {
            sessionFactory.close();
            System.out.println("Database connection closed");
        }
    }
}