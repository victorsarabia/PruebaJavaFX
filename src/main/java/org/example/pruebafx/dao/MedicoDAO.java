package org.example.pruebafx.dao;

import org.example.pruebafx.util.HibernateUtil;
import org.example.pruebafx.model.Medico;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class MedicoDAO {

    public void save(Medico medico) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(medico);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public List<Medico> getAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Medico", Medico.class).list();
        }
    }

    public List<Medico> getPaginated(int page, int offset) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            //return session.createQuery("select new Medico(m.id, m.nombre, m.apellido1) from Medico m", Medico.class)
            return session.createQuery("FROM Medico", Medico.class)
                    .setFirstResult((page - 1) * offset) // Va a la página page
                    .setMaxResults(offset) // Devuelve offset registros
                    .list();
        }
    }
}