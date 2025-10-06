package entities;

  import jakarta.persistence.*;
  import java.util.List;

  @Entity
  @Table(name = "clientes")
  public class Cliente {
      @Id
      @GeneratedValue(strategy = GenerationType.IDENTITY)
      private Long id;
      @Column(nullable = false)
      private String nombre;
      private String email;

      @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
      private List<Pedido> pedidos;

      // Constructor vacío (requerido por JPA)
      public Cliente() {}

      public Cliente(String nombre, String email) {
          this.nombre = nombre;
          this.email = email;
      }

      // Getters y Setters
      public Long getId() { return id; }
      public void setId(Long id) { this.id = id; }
      public String getNombre() { return nombre; }
      public void setNombre(String nombre) { this.nombre = nombre; }
      public String getEmail() { return email; }
      public void setEmail(String email) { this.email = email; }
      public List<Pedido> getPedidos() { return pedidos; }
      public void setPedidos(List<Pedido> pedidos) { this.pedidos = pedidos; }
  }
  