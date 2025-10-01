import jakarta.persistence.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "clientes")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 150)
    private String nombre;

    @Column(name = "correo_electronico", nullable = false, unique = true, length = 150)
    private String correoElectronico;

    @Column(length = 150)
    private String telefono;

    @Column(length = 150)
    private String direccion;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Pedido> pedidos;

    // Getters y setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getCorreoElectronico() { return correoElectronico; }
    public void setCorreoElectronico(String correoElectronico) { this.correoElectronico = correoElectronico; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public Set<Pedido> getPedidos() { return pedidos; }
    public void setPedidos(Set<Pedido> pedidos) { this.pedidos = pedidos; }

    // --- Métodos CRUD ---

    private static SessionFactory factory = new Configuration()
            .configure("hibernate.cfg.xml")
            .addAnnotatedClass(Cliente.class)
            .addAnnotatedClass(Producto.class)
            .addAnnotatedClass(Pedido.class)
            .addAnnotatedClass(ItemPedido.class)
            .addAnnotatedClass(Proveedores.class)
            .buildSessionFactory();

    // Crear cliente
    public static void crearCliente(Cliente cliente) {
        Session session = factory.getCurrentSession();
        try {
            session.beginTransaction();
            session.persist(cliente);
            session.getTransaction().commit();
            System.out.println("Cliente creado con ID: " + cliente.getId());
        } finally {
            session.close();
        }
    }

    // Leer cliente por ID
    public static Cliente leerCliente(int id) {
        Session session = factory.getCurrentSession();
        Cliente cliente = null;
        try {
            session.beginTransaction();
            cliente = session.get(Cliente.class, id);
            session.getTransaction().commit();
        } finally {
            session.close();
        }
        return cliente;
    }

    // Actualizar cliente
    public static void actualizarCliente(Cliente cliente) {
        Session session = factory.getCurrentSession();
        try {
            session.beginTransaction();
            session.merge(cliente);
            session.getTransaction().commit();
            System.out.println("Cliente actualizado: ID " + cliente.getId());
        } finally {
            session.close();
        }
    }

    // Eliminar cliente
    public static void eliminarCliente(int id) {
        Session session = factory.getCurrentSession();
        try {
            session.beginTransaction();
            Cliente cliente = session.get(Cliente.class, id);
            if (cliente != null) {
                session.remove(cliente);
                System.out.println("Cliente eliminado: ID " + id);
            } else {
                System.out.println("Cliente no encontrado: ID " + id);
            }
            session.getTransaction().commit();
        } finally {
            session.close();
        }
    }

    // Listar todos los clientes
    public static List<Cliente> listarClientes() {
        Session session = factory.getCurrentSession();
        List<Cliente> clientes = null;
        try {
            session.beginTransaction();
            clientes = session.createQuery("from Cliente", Cliente.class).getResultList();
            session.getTransaction().commit();
        } finally {
            session.close();
        }
        return clientes;
    }
}
