package Main.repositories;

import entities.Producto;
import org.hibernate.Session;
import org.hibernate.Transaction;

import Main.utils.HibernateUtil;

import java.util.List;

public class HibernateProductoRepository implements ProductoRepository {
    @Override
    public void guardar(Producto producto) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.persist(producto);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    public List listarTodos() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            return session.createQuery("FROM Producto", Producto.class).list();
        } finally {
            session.close();
        }
    }

    @Override
    public Producto findById(Long id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            return session.get(Producto.class, id);
        } finally {
            session.close();
        }
    }
}