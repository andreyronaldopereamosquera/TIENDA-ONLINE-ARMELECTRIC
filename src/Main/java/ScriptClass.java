package Main.java;
public class ScriptClass {

    public static void main(String[] args) {
        // Ejemplo: Crear un cliente
        Cliente cliente = new Cliente();
        cliente.setNombre("Luis Gómez");
        cliente.setCorreoElectronico("luis.gomez@ejemplo.com");
        cliente.setTelefono("5559876543");
        cliente.setDireccion("Av. Siempre Viva 742");
        Cliente.crearCliente(cliente);

        // Ejemplo: Crear un producto
        Producto producto = new Producto();
        producto.setNombre("Taladro Eléctrico");
        producto.setDescripcion("Taladro inalámbrico potente");
        producto.setPrecio(new java.math.BigDecimal("479952.00"));
        producto.setStock(50);
        Producto.crearProducto(producto);

        // Ejemplo: Crear un pedido
        Pedido pedido = new Pedido();
        pedido.setCliente(cliente);
        pedido.setEstado("En proceso");
        pedido.setFechaPedido(new java.sql.Timestamp(System.currentTimeMillis()));
        pedido.setMontoTotal(new java.math.BigDecimal("479952.00"));
        Pedido.crearPedido(pedido);

        // Listar clientes
        System.out.println("Clientes en la base de datos:");
        for (Cliente c : Cliente.listarClientes()) {
            System.out.println(c.getId() + " - " + c.getNombre());
        }

        // Listar productos
        System.out.println("Productos en la base de datos:");
        for (Producto p : Producto.listarProductos()) {
            System.out.println(p.getId() + " - " + p.getNombre());
        }

        // Listar pedidos
        System.out.println("Pedidos en la base de datos:");
        for (Pedido ped : Pedido.listarPedidos()) {
            System.out.println(ped.getId() + " - Estado: " + ped.getEstado());
        }

        // Aquí puedes agregar más lógica o llamadas a otros métodos CRUD
    }
}
