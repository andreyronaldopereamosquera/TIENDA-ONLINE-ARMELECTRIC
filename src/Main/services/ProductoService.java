package Main.services;

import entities.Producto;

import java.util.List;

import Main.repositories.ProductoRepository;

public class ProductoService {
    private static final int Prodcuto = 0;
    private final ProductoRepository repository;  // DIP: depende de interface

    public ProductoService(ProductoRepository repository) {
        this.repository = repository;
    }

    public void guardar(Producto producto) {
        repository.guardar(producto);
    }

    public Producto findById(Long id) {
        return (Producto) repository.findById(id);
    }

    public void guardar(Main.entities.Producto producto) {
        throw new UnsupportedOperationException("Unimplemented method 'guardar'");
    }
    
    public List<Producto> listarTodos() {
        return repository.listarTodos();
    }
}