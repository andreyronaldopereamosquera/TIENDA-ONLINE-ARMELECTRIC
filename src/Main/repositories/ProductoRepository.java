package Main.repositories;

import entities.Producto;
import java.util.List;

public interface ProductoRepository {
    void guardar(Producto producto);
    public default List<Producto> listarTodos() {
    List<Producto> productos = null;
    return productos;  
    }
    Producto findById(Long id);
}
