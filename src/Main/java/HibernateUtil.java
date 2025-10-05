 package Main.java;
 
 import org.hibernate.SessionFactory;
       import org.hibernate.cfg.Configuration;

       public class HibernateUtil {
           private static SessionFactory factory;

           public static SessionFactory getSessionFactory() {
               if (factory == null) {
                   try {
                       Configuration cfg = new Configuration();
                       cfg.configure("hibernate.cfg.xml");  // Asegúrate de que XML esté en classpath
                       // Agrega entidades si no mapeadas en XML
                       cfg.addAnnotatedClass(Cliente.class);
                       cfg.addAnnotatedClass(Producto.class);
                       cfg.addAnnotatedClass(Pedido.class);
                       cfg.addAnnotatedClass(ItemPedido.class);
                       cfg.addAnnotatedClass(Proveedores.class);
                       // ... otras clases
                       factory = cfg.buildSessionFactory();
                       System.out.println("SessionFactory inicializado correctamente.");
                   } catch (Exception e) {
                       System.err.println("Error en HibernateUtil: " + e.getMessage());
                       e.printStackTrace();
                       throw new RuntimeException("Fallo en inicialización de Hibernate", e);
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
       