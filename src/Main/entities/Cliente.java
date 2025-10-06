package Main.entities;

  import jakarta.persistence.*;
  import java.util.List;

  @Entity
  @Table(name = "clientes")
  public class Cliente {
      @Id
      @GeneratedValue(strategy = GenerationType.IDENTITY)
      private Long id;

      public Cliente(List<Pedido> pedido) {
      }

      public Cliente(String nombre, String email, List<Pedido> pedido) {
      }

     
  }
  
  