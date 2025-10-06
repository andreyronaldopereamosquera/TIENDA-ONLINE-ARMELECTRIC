package Main.entities;  // O crea en services si prefieres

import java.math.BigDecimal;

public record CarritoItem(Producto producto, int cantidad) {
    public CarritoItem(Object producto, int cantidad) {
        this.producto = (Producto) producto;
        this.cantidad = cantidad;
    }

    public BigDecimal getSubtotal() {
        return producto.getPrecioUSD().multiply(BigDecimal.valueOf(cantidad));
    }

    public void setCantidad(int i) {
        throw new UnsupportedOperationException("Unimplemented method 'setCantidad'");
    }
}