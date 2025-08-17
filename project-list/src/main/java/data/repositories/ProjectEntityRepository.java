package data.repositories;

import data.entities.ProjectEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class ProjectEntityRepository{
    private final SessionFactory sessionFactory;

    public ProjectEntityRepository(SessionFactory sessionFactory) {
        this.sessionFactory = new Configuration()
                .configure("hibernate.properties")
                .buildSessionFactory();
    }

    // Сохранение проекта
    public void save(ProjectEntity project) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.persist(project);
            session.getTransaction().commit();
        }
    }
    // Получение всех проектов
    public List<ProjectEntity> findAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM ProjectEntity", ProjectEntity.class).list();
        }
    }

    // Закрытие фабрики сессий (вызовите при закрытии приложения)
    public void close() {
        sessionFactory.close();
    }

}
