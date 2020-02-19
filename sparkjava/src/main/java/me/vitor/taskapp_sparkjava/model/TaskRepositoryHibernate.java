package me.vitor.taskapp_sparkjava.model;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

public class TaskRepositoryHibernate implements TaskRepository {

    private static SessionFactory factory = new Configuration().configure().buildSessionFactory();

    @Override
    public Task findBy(Long id) {
        List<Task> tasks;
        try (Session session = factory.openSession()) {
            Query query = session.createQuery("FROM Task where id = :id");
            query.setParameter("id", id);
            tasks = query.list();
        }

        if (tasks == null || tasks.isEmpty()) {
            return null;
        }
        return tasks.get(0);
    }

    @Override
    public List<Task> findAll() {
        List<Task> tasks;
        try (Session session = factory.openSession()) {
            tasks = session.createQuery("FROM Task").list();
        }
        return tasks;
    }

    @Override
    public Task save(Task task) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.saveOrUpdate(task);
            session.getTransaction().commit();
        }
        return task;
    }

    @Override
    public void deleteById(Long id) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            Task task = new Task();
            task.setId(id);
            session.remove(task);
            session.getTransaction().commit();
        }
    }

}
