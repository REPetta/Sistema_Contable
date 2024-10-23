//Clase encargada de la conexion con la base de datos//
package ConnectionsBD;

import java.sql.Connection;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;
public class ConnectionBD {
    //Atributos//
    Connection con=null;
    String url="jdbc:postgresql://localhost/Sistema_Contable";
    String user="postgres";
    String key="usuario";
    //Metodo para conectar a la BD//
    public Connection conect() throws ClassNotFoundException, SQLException{//Clase encargada de establecer la conexion con la BD//
        Class.forName("org.postgresql.Driver");//Carga el driver jbdc en memoria , si no lo encuentra lanza la primera excepcion//
        con=DriverManager.getConnection(url,user,key);//Estable la conexion con la BD , si no lo consigue lanza la segund excepcion//
        return con;//Retorna la conexion establecida para realizar consulas a la BD desde otra parte//
    }
    //Metodo para cerrar la conexion con la BD//
    public void close() throws IOException, SQLException{
        con.close();
    }
}
