package Main.utils;

import org.hibernate.Session;
import org.hibernate.Transaction;

public class TestHibernate {
    public static void main(String[] args) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        // Prueba: Crea producto de ejemplo
        entities.Producto p = new entities.Producto(null, null, null);
        session.persist(p);

        tx.commit();
        session.close();
        System.out.println("Prueba OK: Producto guardado. Verifica en MySQL.");
        HibernateUtil.shutdown();
    }
}