  package Main.java;
  public class TestCliente {
         public static void main(String[] args) {
             try {
                 System.out.println("Creando instancia...");
                 Cliente c = new Cliente();  // Tu línea
                 c.setNombre("Test");
                 System.out.println("Instancia OK: " + c.getNombre());
                 
                 // Prueba CRUD si factory OK
                 Cliente.crearCliente(c);
             } catch (Exception e) {
                 e.printStackTrace();
             }
         }
     }
     