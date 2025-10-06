package Main.entities;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "productos")
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String nombre;
    private String descripcion;
    @Column(precision = 10, scale = 2)
    private BigDecimal precioUSD;  // Precio base USD

    public Producto() {}

    public Producto(String nombre, String descripcion, BigDecimal precioUSD) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precioUSD = precioUSD;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public BigDecimal getPrecioUSD() { return precioUSD; }
    public void setPrecioUSD(BigDecimal precioUSD) { this.precioUSD = precioUSD; }

    public Producto findById(Long id2) {
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }
}