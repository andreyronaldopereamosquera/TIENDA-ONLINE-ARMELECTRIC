package Main.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class CotizacionService {

    private static final String API_URL = "https://api.exchangerate-api.com/v4/latest/USD";

    public BigDecimal obtenerCotizacion(String moneda) {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(API_URL))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(response.body());
            JsonNode ratesNode = rootNode.path("rates");

            if (ratesNode.has(moneda)) {
                return ratesNode.get(moneda).decimalValue();
            } else {
                throw new IllegalArgumentException("Moneda no soportada: " + moneda);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return BigDecimal.ONE; // Retorna 1 en caso de error para evitar fallos en el cálculo
        }
    }

    public BigDecimal convertir(BigDecimal totalUSD, String moneda) {
        throw new UnsupportedOperationException("Unimplemented method 'convertir'");
    }
}
   