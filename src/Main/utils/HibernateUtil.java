package Main.utils;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import Main.entities.Cliente;
import Main.entities.ItemPedido;
import Main.entities.Pedido;
import Main.entities.Producto;

public class HibernateUtil {
    private static SessionFactory factory;

    public static SessionFactory getSessionFactory() {
        if (factory == null) {
            try {
                Configuration cfg = new Configuration();
                cfg.configure();  // Carga hibernate.cfg.xml
                // Mapeo explícito (DIP: centralizado)
                cfg.addAnnotatedClass(Cliente.class);
                cfg.addAnnotatedClass(Producto.class);
                cfg.addAnnotatedClass(Pedido.class);
                cfg.addAnnotatedClass(ItemPedido.class);
                factory = cfg.buildSessionFactory();
                System.out.println("Hibernate SessionFactory creado.");
            } catch (Exception e) {
                System.err.println("Error inicializando Hibernate: " + e.getMessage());
                e.printStackTrace();
            }
        }
        return factory;
    }

    public static void shutdown() {
        if (factory != null) {
            factory.close();
        }
    }
}