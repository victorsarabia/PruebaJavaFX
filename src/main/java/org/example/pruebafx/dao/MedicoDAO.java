package org.example.pruebafx.dao;

import org.example.pruebafx.util.HibernateUtil;
import org.example.pruebafx.model.Medicos;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class MedicoDAO {

    public void save(Medicos medico) {
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

    public List<Medicos> getAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Medicos", Medicos.class).list();
        }
    }

    public List<Medicos> getPaginated() {
        int pageSize = 10;
        int pageNumber = 2;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("select new Medicos(m.id, m.nombre, m.apellido1) from Medicos m", Medicos.class)
                    .setFirstResult((pageNumber - 1) * pageSize) // Salta los primeros 10 registros
                    .setMaxResults(pageSize) // Devuelve 10 registros
                    .list();
        }
    }
}