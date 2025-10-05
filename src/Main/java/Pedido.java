package Main.java;

import jakarta.persistence.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "pedidos")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @Column(name = "fecha_pedido", nullable = false)
    private Timestamp fechaPedido;

    @Column(nullable = false, length = 150)
    private String estado;

    @Column(name = "monto_total", nullable = false, precision = 15, scale = 2)
    private BigDecimal montoTotal;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<ItemPedido> itemsPedido;

    // Getters y setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }

    public Timestamp getFechaPedido() { return fechaPedido; }
    public void setFechaPedido(Timestamp fechaPedido) { this.fechaPedido = fechaPedido; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public BigDecimal getMontoTotal() { return montoTotal; }
    public void setMontoTotal(BigDecimal montoTotal) { this.montoTotal = montoTotal; }

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

    // Crear pedido
    public static void crearPedido(Pedido pedido) {
        Session session = factory.getCurrentSession();
        try {
            session.beginTransaction();
            session.persist(pedido);
            session.getTransaction().commit();
            System.out.println("Pedido creado con ID: " + pedido.getId());
        } finally {
            session.close();
        }
    }

    // Leer pedido por ID
    public static Pedido leerPedido(int id) {
        Session session = factory.getCurrentSession();
        Pedido pedido = null;
        try {
            session.beginTransaction();
            pedido = session.get(Pedido.class, id);
            session.getTransaction().commit();
        } finally {
            session.close();
        }
        return pedido;
    }

    // Actualizar pedido
    public static void actualizarPedido(Pedido pedido) {
        Session session = factory.getCurrentSession();
        try {
            session.beginTransaction();
            session.merge(pedido);
            session.getTransaction().commit();
            System.out.println("Pedido actualizado: ID " + pedido.getId());
        } finally {
            session.close();
        }
    }

    // Eliminar pedido
    public static void eliminarPedido(int id) {
        Session session = factory.getCurrentSession();
        try {
            session.beginTransaction();
            Pedido pedido = session.get(Pedido.class, id);
            if (pedido != null) {
                session.remove(pedido);
                System.out.println("Pedido eliminado: ID " + id);
            } else {
                System.out.println("Pedido no encontrado: ID " + id);
            }
            session.getTransaction().commit();
        } finally {
            session.close();
        }
    }

    // Listar todos los pedidos
    public static List<Pedido> listarPedidos() {
        Session session = factory.getCurrentSession();
        List<Pedido> pedidos = null;
        try {
            session.beginTransaction();
            pedidos = session.createQuery("from Pedido", Pedido.class).getResultList();
            session.getTransaction().commit();
        } finally {
            session.close();
        }
        return pedidos;
    }
}
