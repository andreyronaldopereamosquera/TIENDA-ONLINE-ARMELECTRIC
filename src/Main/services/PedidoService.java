package Main.services;

import entities.Cliente;
import java.util.List;

import Main.entities.Pedido;
import Main.repositories.PedidoRepository;

public class PedidoService {
    private final PedidoRepository repository;  // DIP

    public PedidoService(PedidoRepository repository) {
        this.repository = repository;
    }

    public void finalizarPedido(entities.Pedido pedido) {
        // Lógica adicional: validar, simular pago
        System.out.println("Simulando procesamiento de pago...");
        repository.persist(pedido);  // Persiste
    }

    public List<Pedido> listarPorCliente(Cliente cliente) {
        return repository.findByCliente(cliente);
    }

    public List<Pedido> listarPorCliente(Main.entities.Cliente cliente) {
        throw new UnsupportedOperationException("Unimplemented method 'listarPorCliente'");
    }

    public void finalizarPedido(Pedido pedido) {
        throw new UnsupportedOperationException("Unimplemented method 'finalizarPedido'");
    }
}