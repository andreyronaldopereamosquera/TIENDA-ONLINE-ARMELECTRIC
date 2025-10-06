package Main.entities;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "items_pedido")
public class ItemPedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;

    @ManyToOne
    @JoinColumn(name = "producto_id")
    private Producto producto;

    private int cantidad;

    @Column(precision = 10, scale = 2)
    private BigDecimal precioUnitarioUSD;

    public ItemPedido() {}

    public ItemPedido(Pedido pedido, Producto producto, int cantidad, BigDecimal precioUnitarioUSD) {
        this.pedido = pedido;
        this.producto = producto;
        this.cantidad = cantidad;
        this.precioUnitarioUSD = precioUnitarioUSD;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Pedido getPedido() { return pedido; }
    public void setPedido(Pedido pedido2) { this.pedido = pedido2; }
    public Producto getProducto() { return producto; }
    public void setProducto(Producto producto) { this.producto = producto; }
    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }
    public BigDecimal getPrecioUnitarioUSD() { return precioUnitarioUSD; }
    public void setPrecioUnitarioUSD(BigDecimal precioUnitarioUSD) { this.precioUnitarioUSD = precioUnitarioUSD; }
}