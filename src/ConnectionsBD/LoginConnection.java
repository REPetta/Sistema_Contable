//Clase encarga de conectar el login con la base de datos//
package ConnectionsBD;


import Model.User;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.io.IOException;
public class LoginConnection {
   
    //Metodo para validar si el usuario y contraseña ingresado esta en registrado en la base de datos, ademas solo valida usuarios que su estado sea "alta"//
   public boolean validateUser(String userName, String password) throws ClassNotFoundException, SQLException, IOException{
       String sql="SELECT*FROM Usuario WHERE userName=? AND password=? AND estado=?";//Consulta sql a la base de datos//
        User user;
            ResultSet rs=null;//Variable para almacenar el resultado de la consulta//
            PreparedStatement ps=null;//Variable para preparar y ejecutar la consulta //
            ////
            ConnectionsBD.ConnectionBD objConect = new ConnectionsBD.ConnectionBD();//Crea una instancia de ConnectionBD//
        try{
            ps=objConect.conect().prepareStatement(sql);//Establece la conexion con la base de datos//
            ps.setString(1, userName);//Asigna al primer ? el userName//
            ps.setString(2, password);//Asigna al segundo ? el password//
            ps.setString(3, "alta");//Asigna al tercer ? el string "alta"//
            rs=ps.executeQuery();//Ejecuta la consulta y almacena el resultado//
            if(rs.next()){//Next verifica si el registro de rs coincide con la consulta y si no lo hace avanza al siguiente, hasta que no haya mas filas en la tabla//
                return true;
                }
           }catch(ClassNotFoundException | SQLException e){
     e.printStackTrace();
     throw e;
 }finally{
     if(rs!=null) try{rs.close();}catch(SQLException e){e.printStackTrace();}
     if (ps != null) try { ps.close(); } catch (SQLException e) { e.printStackTrace(); }
     if (objConect != null) try { objConect.close(); } catch (SQLException e) { e.printStackTrace(); }
    }   
       return false;
          
   }
   
          public User instanceUserGet(String userName) throws ClassNotFoundException, SQLException, IOException{
          String sql="SELECT u.nombre, u.apellido, u.dni, u.username, p.perfil From Usuario AS u INNER JOIN Perfiles AS p ON u.idperfil=p.idperfil WHERE u.username=?;";//Consulta sql a la base de datos//
          //Atributos//
            User user=new User();
            UserManagementConnection userManagementConnection = new UserManagementConnection();
            ResultSet rs=null;//Variable para almacenar el resultado de la consulta//
            PreparedStatement ps=null;//Variable para preparar y ejecutar la consulta //
            ////
            ConnectionsBD.ConnectionBD objConect = new ConnectionsBD.ConnectionBD();//Crea una instancia de ConnectionBD//
          try{
          
            ps=objConect.conect().prepareStatement(sql);//Establece la conexion con la base de datos//
            ps.setString(1, userName);//Asigna al primer ? el userName//
            rs=ps.executeQuery();//Ejecuta la consulta y almacena el resultado//
            if(rs.next()){//Next verifica si el registro de rs coincide con la consulta y si no lo hace avanza al siguiente, hasta que no haya mas filas en la tabla//
                    user.setName(rs.getString("nombre"));
                    user.setLastName(rs.getString("apellido"));
                    user.setDni(rs.getInt("dni"));
                    user.setUserName(rs.getString("userName"));
                    user.setRol(rs.getString("perfil"));
                    user.setTasks(userManagementConnection.getTasks( userName));
                    return user;
            }
                
                
    }catch(ClassNotFoundException | SQLException e){
     e.printStackTrace();
     throw e;
 }finally{
     if(rs!=null) try{rs.close();}catch(SQLException e){e.printStackTrace();}
     if (ps != null) try { ps.close(); } catch (SQLException e) { e.printStackTrace(); }
     if (objConect != null) try { objConect.close(); } catch (SQLException e) { e.printStackTrace(); }
    }   
       return null;

}
                
   }
    
