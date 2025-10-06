package Main;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import com.sun.net.httpserver.HttpExchange; 
import entities.Producto; 
public class WebServer {
private Object productoService;

private String leerHTML(String archivo) throws IOException {
    File file = new File("web/" + archivo);
    
    if (!file.exists()) {
        return "<html><body><h1>Error: Archivo no encontrado</h1></body></html>";
    }


    StringBuilder sb = new StringBuilder();
    try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line).append("\n");
        }
    }
    
    return sb.toString();
}

// ---
// Método auxiliar: Reemplaza placeholder en HTML
private String reemplazarPlaceholder(String html, String placeholder, String contenido) {
    return html.replace(placeholder, contenido);
}

public void handle(HttpExchange exchange) throws IOException {
    if ("GET".equals(exchange.getRequestMethod())) {
        Object server = null;
        String htmlBase = ((WebServer) server).leerHTML("index.html");
        
        // Genera contenido dinámico para productos
        StringBuilder productosHTML = new StringBuilder();
        for (Producto p : ((Producto) ((WebServer) server).productoService).listIterable()) {
            productosHTML.append("<div class=\"col-md-4\">")
                        .append("<div class=\"card producto-card\">")
                        .append("<div class=\"card-body\">")
                        .append("<h5 class=\"card-title\">").append(p.getNombre()).append("</h5>")
                        .append("<p class=\"card-text\">").append(p.getDescripcion()).append("</p>")
                        .append("<p class=\"card-text\"><strong>Precio: $").append(p.getPrecioUSD()).append(" USD</strong></p>")
                        .append("<form action=\"/agregar-carrito\" method=\"post\">")
                        .append("<input type=\"hidden\" name=\"id\" value=\"").append(p.getId()).append("\">")
                        .append("<input type=\"number\" name=\"cantidad\" value=\"1\" min=\"1\" class=\"form-control d-inline w-auto me-2\">")
                        .append("<button type=\"submit\" class=\"btn btn-primary\">Agregar al Carrito</button>")
                        .append("</form>")
                        .append("</div></div></div>");
        }
        
        String htmlFinal = ((WebServer) server).reemplazarPlaceholder(htmlBase, "", productosHTML.toString());
        
        // Envío de la respuesta
        byte[] responseBytes = htmlFinal.getBytes("UTF-8");
        exchange.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
        exchange.sendResponseHeaders(200, responseBytes.length);
        
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(responseBytes);
        }
    }
}

}