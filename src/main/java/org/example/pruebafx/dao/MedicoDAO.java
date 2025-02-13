package org.example.pruebafx.dao;

import org.example.pruebafx.util.HibernateUtil;
import org.example.pruebafx.model.Medico;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.HashMap;
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

    public List<Medico> getPaginated(int page, int offset, HashMap<String, String> filtros) {

        // Forma sencilla
        /*try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            //return session.createQuery("select new Medico(m.id, m.nombre, m.apellido1) from Medico m", Medico.class)
            return session.createQuery("FROM Medico", Medico.class)
                    .setFirstResult((page - 1) * offset) // Va a la página page
                    .setMaxResults(offset) // Devuelve offset registros
                    .list();
        }
         */

        // Separando para añadir de forma dinámica los filtros
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            StringBuilder hql = new StringBuilder("FROM Medico m WHERE true");

            // Agregar condiciones dinámicas basadas en el HashMap
            if (filtros != null)
                for (String key : filtros.keySet()) {
                    if (key.equals("especialidad"))
                        hql.append(" AND m.especialidad.nombre LIKE :").append(key);// in (select nombre from Especialidad e where e.nombre LIKE :especialidad)");
                    else
                        hql.append(" AND m.").append(key).append(" LIKE :").append(key);
                }

            Query<Medico> query = session.createQuery(hql.toString(), Medico.class);

            // Asignar valores a los parámetros de la consulta
            if (filtros != null)
                for (HashMap.Entry<String, String> filtro : filtros.entrySet()) {
                    query.setParameter(filtro.getKey(),"%"+filtro.getValue()+"%");
                }

            // Configurar paginación
            query.setFirstResult((page - 1) * offset); // Página actual
            query.setMaxResults(offset); // Límite de resultados

            return query.list();
        }
    }
}