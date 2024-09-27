//Clase encarga de conectar el login con la base de datos//
package ConnectionsBD;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.io.IOException;
public class LoginConnection {
   
    //Metodo para validar si el usuario y contraseña ingresado esta en registrado en la base de datos, ademas solo valida usuarios que su estado sea "alta"//
   public boolean validateUser(String userName, String password) throws ClassNotFoundException, SQLException, IOException{
       ConnectionBD con=new ConnectionBD();//Creo una instancia de una connecion//
       con.conect();//Inicia la coneccion con la bade de datos//
       String sql="SELECT*FROM Usuario WHERE userName=? AND password=? AND estado=?";//Consulta sql a la base de datos//
        try{
            //Atributos//
            ResultSet rs=null;//Variable para almacenar el resultado de la consulta//
            PreparedStatement ps=null;//Variable para preparar y ejecutar la consulta //
            ////
            ConnectionsBD.ConnectionBD objConect = new ConnectionsBD.ConnectionBD();//Crea una instancia de ConnectionBD//
            ps=objConect.conect().prepareStatement(sql);//Establece la conexion con la base de datos//
            ps.setString(1, userName);//Asigna al primer ? el userName//
            ps.setString(2, password);//Asigna al segundo ? el password//
            ps.setString(3, "alta");//Asigna al tercer ? el string "alta"//
            rs=ps.executeQuery();//Ejecuta la consulta y almacena el resultado//
            if(rs.next()){//Next verifica si el registro de rs coincide con la consulta y si no lo hace avanza al siguiente, hasta que no haya mas filas en la tabla//
                return true;
                }
            con.closeConnect();//Cierra la conexion con la base de datos//
            }catch(ClassNotFoundException | SQLException e){
                }
                return false;
                
   }
    
}
