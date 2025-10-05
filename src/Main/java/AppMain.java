package Main.java;
public class AppMain {
    public static void main(String[] args) {
        // Prueba Cliente
        Cliente c = new Cliente();
        c.setNombre("Test User");
        c.setCorreoElectronico("test@ejemplo.com");
        Cliente.crearCliente(c);

        // Prueba Producto
        Producto p = new Producto();
        p.setNombre("Test Product");
        p.setPrecio(new java.math.BigDecimal("100.00"));
        p.setStock(10);
        Producto.crearProducto(p);

        // Prueba Pedido
        Pedido ped = new Pedido();
        ped.setCliente(c);
        ped.setEstado("Pendiente");
        ped.setFechaPedido(new java.sql.Timestamp(System.currentTimeMillis()));
        ped.setMontoTotal(new java.math.BigDecimal("100.00"));
        Pedido.crearPedido(ped);

        // Listar
        System.out.println("Clientes: " + Cliente.listarClientes().size());
        System.out.println("Productos: " + Producto.listarProductos().size());
        System.out.println("Pedidos: " + Pedido.listarPedidos().size());
    }
}
