package Main.repositories;

import java.util.List;

import Main.entities.Cliente;
import Main.entities.Pedido;

public interface PedidoRepository {
    void save(Pedido pedido);
    List<Main.entities.Pedido> findByCliente(entities.Cliente cliente);
    void persist(entities.Pedido pedido);
    List<Pedido> findByCliente(Cliente cliente);
    void persist(Pedido pedido);
}