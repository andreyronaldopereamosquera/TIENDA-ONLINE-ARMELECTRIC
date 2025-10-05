package Main.java;

import jakarta.persistence.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "productos")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 150)
    private String nombre;

    @Column(length = 150)
    private String descripcion;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal precio;

    @Column(nullable = false)
    private Integer stock;

    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<ItemPedido> itemsPedido;

    // Getters y setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public BigDecimal getPrecio() { return precio; }
    public void setPrecio(BigDecimal precio) { this.precio = precio; }

    public Integer getStock() { return stock; }
    public void setStock(Integer stock) { this.stock = stock; }

    public Set<ItemPedido> getItemsPedido() { return itemsPedido; }
    public void setItemsPedido(Set<ItemPedido> itemsPedido) { this.itemsPedido = itemsPedido; }

    // --- Métodos CRUD ---

    private static SessionFactory factory = new Configuration()
            .configure("hibernate.cfg.xml")
            .addAnnotatedClass(Cliente.class)
            .addAnnotatedClass(Producto.class)
            .addAnnotatedClass(Pedido.class)
            .addAnnotatedClass(ItemPedido.class)
            .addAnnotatedClass(Proveedores.class)
            .buildSessionFactory();

    // Crear producto
    public static void crearProducto(Producto producto) {
        Session session = factory.getCurrentSession();
        try {
            session.beginTransaction();
            session.persist(producto);
            session.getTransaction().commit();
            System.out.println("Producto creado con ID: " + producto.getId());
        } finally {
            session.close();
        }
    }

    // Leer producto por ID
    public static Producto leerProducto(int id) {
        Session session = factory.getCurrentSession();
        Producto producto = null;
        try {
            session.beginTransaction();
            producto = session.get(Producto.class, id);
            session.getTransaction().commit();
        } finally {
            session.close();
        }
        return producto;
    }

    // Actualizar producto
    public static void actualizarProducto(Producto producto) {
        Session session = factory.getCurrentSession();
        try {
            session.beginTransaction();
            session.merge(producto);
            session.getTransaction().commit();
            System.out.println("Producto actualizado: ID " + producto.getId());
        } finally {
            session.close();
        }
    }

    // Eliminar producto
    public static void eliminarProducto(int id) {
        Session session = factory.getCurrentSession();
        try {
            session.beginTransaction();
            Producto producto = session.get(Producto.class, id);
            if (producto != null) {
                session.remove(producto);
                System.out.println("Producto eliminado: ID " + id);
            } else {
                System.out.println("Producto no encontrado: ID " + id);
            }
            session.getTransaction().commit();
        } finally {
            session.close();
        }
    }

    // Listar todos los productos
    public static List<Producto> listarProductos() {
        Session session = factory.getCurrentSession();
        List<Producto> productos = null;
        try {
            session.beginTransaction();
            productos = session.createQuery("from Producto", Producto.class).getResultList();
            session.getTransaction().commit();
        } finally {
            session.close();
        }
        return productos;
    }
}