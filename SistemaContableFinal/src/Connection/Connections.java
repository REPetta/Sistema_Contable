//Valida la coneccion con la base de datos//
package Connection;

import java.sql.Connection;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connections {
      //Atributos//
    Connection con=null;
    String url="jdbc:postgresql://localhost/Sistema_Contable";
    String user="postgres";
    String key="usuario";
   
    // Método para conectar a la base de datos//
    public Connection connect() throws SQLException {
        if (con == null || con.isClosed()) { // Verifica si la conexión ya está abierta//
            try {
                // Cargar el driver JDBC//
                Class.forName("org.postgresql.Driver");
                // Establecer la conexión//
                con = DriverManager.getConnection(url, user, key);
//                System.out.println("Conexión establecida exitosamente.");//
            } catch (ClassNotFoundException e) {
                System.err.println("Error: Driver JDBC no encontrado.");
                e.printStackTrace();
            } catch (SQLException e) {
                System.err.println("Error al conectar con la base de datos.");
                throw e; // Propaga la excepción para manejarla en otro nivel//
            }
        }
        return con;
    }
    //Metodo para cerrar la conexion con la BD//
      public void close() {
        if (con != null) {
            try {
                con.close();
//                System.out.println("Conexión cerrada exitosamente.");
            } catch (SQLException e) {
                System.err.println("Error al cerrar la conexión.");
                e.printStackTrace();
            }
        }
    }
}
