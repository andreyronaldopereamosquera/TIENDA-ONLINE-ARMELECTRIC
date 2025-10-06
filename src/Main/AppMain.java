package Main;

import Main.entities.CarritoItem;
import Main.entities.ItemPedido;
import Main.repositories.HibernatePedidoRepository;
import Main.repositories.HibernateProductoRepository;
import Main.repositories.PedidoRepository;
import Main.repositories.ProductoRepository;
import Main.services.CarritoService;
import Main.services.PedidoService;
import Main.services.ProductoService;
import Main.utils.CotizacionService;
import com.mysql.cj.xdevapi.Client;
import entities.Cliente;
import entities.Pedido;
import entities.Producto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class AppMain {
    private static final Scanner scanner = new Scanner(System.in);
    private static ProductoService productoService;
    private static CarritoService carritoService;
    private static PedidoService pedidoService;
    private static CotizacionService cotizacionService;

   public static void main(String[] args) {
        System.out.println("=== Bienvenido a Tienda Armelectric ===");
        
        // Inicialización de servicios (DIP: inyecta interfaces en constructores)
        ProductoRepository prodRepo = new HibernateProductoRepository();
        productoService = new ProductoService(prodRepo);
        
        PedidoRepository pedidoRepo = new HibernatePedidoRepository();
        pedidoService = new PedidoService(pedidoRepo);
        
        cotizacionService = new CotizacionService();
        carritoService = new CarritoService();  // En memoria (SRP: solo carrito)

        // 1. Registro/Login de Cliente (simplificado)
        Cliente cliente = registrarCliente();
        System.out.println("Cliente logueado: " + cliente.getNombre());

        AppMain.inicializarProductosPrueba();


        scanner.close();
        System.out.println("¡Gracias por usar Armelectric! Sesión cerrada.");
    }

    public static void checkout(Cliente cliente) {
        throw new UnsupportedOperationException("Unimplemented method 'checkout'");
    }

    public static Cliente registrarCliente() {
        System.out.print("Ingresa tu nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Ingresa tu email: ");
        String email = scanner.nextLine();
        Cliente cliente = new Cliente(nombre, email);
        // En prod, busca en BD y actualiza; aquí crea nuevo
        System.out.println("Cliente registrado: " + nombre);
        return cliente;
    }


    public static void inicializarProductosPrueba() {
        List<Producto> productos = (List<Producto>) productoService.listarTodos();
        if (productos.isEmpty()) {
            System.out.println("Insertando productos de prueba...");
            productoService.guardar(new Producto("Laptop Gaming", "Alta performance", new BigDecimal("999.99")));
            productoService.guardar(new Producto("Teléfono Móvil", "Smartphone 128GB", new BigDecimal("599.99")));
            productoService.guardar(new Producto("Auriculares Wireless", "Noise cancelling", new BigDecimal("149.99")));
            System.out.println("Productos insertados en BD.");
        } else {
            System.out.println("Productos ya existen en BD (" + productos.size() + " items).");
        }
    }

    // Muestra menú principal
    private static void mostrarMenu() {
        System.out.println("\n--- Menú Principal ---");
        System.out.println("1. Ver Catálogo de Productos");
        System.out.println("2. Agregar Producto al Carrito");
        System.out.println("3. Ver Carrito");
        System.out.println("4. Checkout (Cotización y Finalizar Pedido)");
        System.out.println("5. Ver Historial de Pedidos");
        System.out.println("6. Salir");
        System.out.print("Elige una opción: ");
    
    }
    // 2. Listar productos (usa ProductoService - SRP)
    private static void listarProductos(Cliente cliente) {
        System.out.println("\n--- Catálogo de Productos ---");
        List<Producto> productos = productoService.listarTodos();
        if (productos.isEmpty()) {
            System.out.println("No hay productos disponibles.");
            return;
        }
        for (Producto p : productos) {
            System.out.println("ID: " + p.getId() + " | " + p.getNombre() + 
                             " | Descripción: " + p.getDescripcion() + 
                             " | Precio: $" + p.getPrecioUSD() + " USD");
        }
        System.out.println("Presiona Enter para continuar...");
        scanner.nextLine();
    }

    // 3. Agregar al carrito (usa CarritoService - SRP)
    private static void agregarAlCarrito(Cliente cliente) {
        System.out.print("Ingresa ID del producto: ");
        Long id = scanner.nextLong();
        scanner.nextLine();
        System.out.print("Ingresa cantidad: ");
        int cantidad = scanner.nextInt();
        scanner.nextLine();

        Producto producto = (productoService.findById(id));
        if (producto != null) {
            carritoService.agregarItem(new CarritoItem(producto, cantidad));
            System.out.println("Producto agregado al carrito: " + producto.getNombre() + " x" + cantidad);
        } else {
            System.out.println("Producto no encontrado.");
        }
        System.out.println("Presiona Enter para continuar...");
        scanner.nextLine();
    }

    // 4. Ver carrito (usa CarritoService - SRP)
    private static void verCarrito(Cliente cliente) {
        System.out.println("\n--- Tu Carrito ---");
        List<CarritoItem> items = carritoService.getItems();
        if (items.isEmpty()) {
            System.out.println("El carrito está vacío.");
            return;
        }
        BigDecimal totalUSD = carritoService.calcularTotal();
        for (CarritoItem item : items) {
            System.out.println("- " + item.producto().getNombre() +
                             " x" + item.cantidad() +
                             " | Subtotal: $" + item.producto().getPrecioUSD().multiply(BigDecimal.valueOf(item.cantidad())) + " USD");
        }
        System.out.println("Total en USD: $" + totalUSD);
        System.out.println("Presiona Enter para continuar...");
        scanner.nextLine();
    }

    // 5. Checkout: Cotización y finalizar (usa CotizacionService y PedidoService - SRP/DIP)
    private static void checkout(Client cliente) {
        List<CarritoItem> items = carritoService.getItems();
        if (items.isEmpty()) {
            System.out.println("Carrito vacío. Agrega productos primero.");
            return;
        }

        BigDecimal totalUSD = carritoService.calcularTotal();
        System.out.println("\n--- Checkout ---");
        System.out.println("Total en USD: $" + totalUSD);

        System.out.print("Selecciona moneda para cotización (USD/EUR/MXN/GBP): ");
        String moneda = scanner.nextLine().toUpperCase();

        BigDecimal totalConvertido = cotizacionService.convertir(totalUSD, moneda);
        System.out.println("Total cotizado en " + moneda + ": " + totalConvertido);

        System.out.print("¿Confirmar y finalizar pedido? (si/no): ");
        String confirmar = scanner.nextLine().toLowerCase();
        if ("si".equals(confirmar)) {
            // Crea y persiste pedido (usa PedidoService - SRP)
            Pedido pedido = new Pedido();
            pedido.setCliente(cliente);
            pedido.setFecha(LocalDateTime.now());
            pedido.setTotalUSD(totalUSD);
            pedido.setMoneda(moneda);
            pedido.setTotalConvertido(totalConvertido);

            // Convierte items del carrito a ItemPedido
            List<ItemPedido> itemsPedido = new ArrayList<>();
            for (CarritoItem item : items) {
                ItemPedido ip = new ItemPedido();
                itemsPedido.add(ip);
            }
            pedido.setItems(itemsPedido);

            pedidoService.finalizarPedido(pedido);  // Persiste en BD (simula pago exitoso)
            carritoService.limpiarCarrito();  // Limpia para próximo uso

            System.out.println("¡Pedido finalizado exitosamente! ID: " + pedido.getId());
            System.out.println("Simulación de pago completada. Revisa BD para detalles.");
        } else {
            System.out.println("Pedido cancelado.");
        }
        System.out.println("Presiona Enter para continuar...");
        scanner.nextLine();
    }

    // 6. Ver historial de pedidos (usa PedidoService - SRP)
    private static void verHistorialPedidos(Cliente cliente) {
        System.out.println("\n--- Historial de Pedidos ---");
        List<Main.entities.Pedido> pedidos = pedidoService.listarPorCliente(cliente);
        if (pedidos.isEmpty()) {
            System.out.println("No hay pedidos para este cliente.");
            return;
        }
        for (Main.entities.Pedido p : pedidos) {
            System.out.println("ID: " + p.getId() + " | Fecha: " + p.getFecha() + 
                             " | Total USD: $" + p.getTotalUSD() + 
                             " | Moneda: " + p.getMoneda() + " | Total Convertido: " + p.getTotalConvertido());
            // Detalles items (opcional)
            for (ItemPedido ip : p.getItems()) {
                System.out.println("  - " + ip.getProducto().getNombre() + " x" + ip.getCantidad());
            }
        }
        System.out.println("Presiona Enter para continuar...");
        scanner.nextLine();
    }
}


















































