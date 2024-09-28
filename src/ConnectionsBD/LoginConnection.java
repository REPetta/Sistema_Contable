//Clase encarga de conectar el login con la base de datos//
package ConnectionsBD;

import Controller.SingletonController;
import Model.User;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.io.IOException;
public class LoginConnection {
   
    //Metodo para validar si el usuario y contraseña ingresado esta en registrado en la base de datos, ademas solo valida usuarios que su estado sea "alta"//
   public boolean validateUser(String userName, String password, SingletonController singletonController) throws ClassNotFoundException, SQLException, IOException{
       String sql="SELECT*FROM Usuario WHERE userName=? AND password=? AND estado=?";//Consulta sql a la base de datos//
        try{
            //Atributos//
            User user;
            UserManagementConnection userManagementConnection;
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
                //Desde aqui se utiliza para usar el singleton//
                userManagementConnection= new UserManagementConnection();//Crea una instancia para manejar las consultas relacionadas con los usuarios en la base de datos//
                int idUser = userManagementConnection.getIdUser(userName);//Guarda el idUser del usuario actual//
                user=userManagementConnection.getUserColumns(idUser);//Guarda un intancia de User , con todos sus datos cargados desde la base de datos//
                userManagementConnection.getTasks(user, userName);//Agrega las tareas permitidas a esa instancia de User//
                singletonController.setUser(user);
                //Aqui acaba//
                return true;
                }
            objConect.closeConnect();//Cierra la conexion con la base de datos//
            }catch(ClassNotFoundException | SQLException e){
                }
                return false;
                
   }
    
}
