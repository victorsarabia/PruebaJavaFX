package org.example.pruebafx.dao;

import org.example.pruebafx.model.Especialidad;
import org.example.pruebafx.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class EspecialidadDAO {
    public Especialidad getEspecialidadById(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Especialidad.class, id);
        }
    }

    public void save(Especialidad especialidad) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(especialidad);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public List<Especialidad> getAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Especialidad", Especialidad.class).list();
        }
    }

    public List<Especialidad> getPaginated(int page, int offset) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            //return session.createQuery("select new Medico(m.id, m.nombre, m.apellido1) from Medico m", Medico.class)
            return session.createQuery("FROM Especialidad ", Especialidad.class)
                    .setFirstResult((page - 1) * offset) // Va a la página page
                    .setMaxResults(offset) // Devuelve offset registros
                    .list();
        }
    }
}