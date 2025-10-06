package Main.services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import Main.entities.CarritoItem;

public class CarritoService {
    private List<Object> items = new ArrayList<>();

    public void agregarItem(CarritoItem item) {
        // Verifica si existe y suma cantidad (opcional)
        for (Object existing : items) {
            if (((CarritoItem) existing).producto().getId().equals(item.producto().getId())) {
                ((CarritoItem) existing).setCantidad(((CarritoItem) existing).cantidad() + item.cantidad());  // Asume setter agregado
                return;
            }
        }
        items.add(item);
    }

    public BigDecimal calcularTotal() {
        BigDecimal total = BigDecimal.ZERO;
        for (Object item : items) {
            total = total.add(((CarritoItem) item).getSubtotal());
        }
        return total;
    }

    public void limpiarCarrito() {
        items.clear();
    }

    public List<CarritoItem> getItems() {
        throw new UnsupportedOperationException("Unimplemented method 'getItems'");
    }
}