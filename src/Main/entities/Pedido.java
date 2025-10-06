package Main.entities;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.mysql.cj.xdevapi.Client;

@Entity
@Table(name = "pedidos")
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
    private List<ItemPedido> items;

    @Column(precision = 10, scale = 2)
    private BigDecimal totalUSD;
    private String moneda;
    @Column(precision = 10, scale = 2)
    private BigDecimal totalConvertido;
    private LocalDateTime fecha;

    public Pedido() {}

    // Getters y Setters (simplificados; agrega todos)
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente2) { this.cliente = cliente2; }
    public List<ItemPedido> getItems() { return items; }
    public void setItems(List<ItemPedido> items) { this.items = items; }
    public BigDecimal getTotalUSD() { return totalUSD; }
    public void setTotalUSD(BigDecimal totalUSD) { this.totalUSD = totalUSD; }
    public String getMoneda() { return moneda; }
    public void setMoneda(String moneda) { this.moneda = moneda; }
    public BigDecimal getTotalConvertido() { return totalConvertido; }
    public void setTotalConvertido(BigDecimal totalConvertido) { this.totalConvertido = totalConvertido; }
    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }

    public void setCliente(Client cliente2) {
        throw new UnsupportedOperationException("Unimplemented method 'setCliente'");
    }
}