//Clase encargada de las consultas e insersiones a tabla Usuarios de la base de datos//
package ConnectionsBD;

import Model.User;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserManagementConnection {


//Metodo para obtener las tareas del Usuario//
public ArrayList<String> getTasks(String userName) throws ClassNotFoundException, SQLException, IOException{
     String sql="SELECT t.tarea FROM Usuario AS u INNER JOIN Perfiles AS p ON u.idperfil=p.idperfil INNER JOIN Perfiles_Tareas AS tp ON p.idperfil=tp.idperfil INNER JOIN Tareas AS t ON tp.idtarea=t.idtarea WHERE u.username=?;";
     ArrayList<String> tasks=new ArrayList<String>();
     ResultSet rs=null;//Variable para almacenar el resultado de la consulta//
     PreparedStatement ps=null;//Variable para preparar y ejecutar la consulta //
     ConnectionsBD.ConnectionBD objConect = new ConnectionsBD.ConnectionBD();//Crea una instancia de ConnectionBD//
     try{
            ps=objConect.conect().prepareStatement(sql);//Establece la conexion con la base de datos//
            ps.setString(1, userName);
            rs=ps.executeQuery();//Ejecuta la consulta y almacena el resultado//
            //Agrega todas las tareas del usuario en la base de datos a la instancia de usuario//
            while(rs.next()){   
                  tasks.add(rs.getString("tarea"));             
            }
            return tasks;
 }catch(ClassNotFoundException | SQLException e){
     e.printStackTrace();
     throw e;
 }finally{
     if(rs!=null) try{rs.close(); }catch(SQLException e){e.printStackTrace(); }
     if (ps != null) try { ps.close(); } catch (SQLException e) { e.printStackTrace(); }
     if (objConect != null) try { objConect.close(); } catch (SQLException e) { e.printStackTrace(); }
    }
     };
    
//Metodo para guardar las columnas de usuario en la clase User y retornar esta//
public User getUserColumns(String username) throws SQLException, ClassNotFoundException, IOException{
     String sql="SELECT u.nombre, u.apellido, u.dni, u.username, u.estado, p.perfil FROM Usuario AS u INNER JOIN Perfiles   AS p ON u.idperfil=p.idperfil WHERE username=?;";//Consulta sql//
     ResultSet rs=null;//Variable para almacenar el resultado de la consulta//
     PreparedStatement ps=null;//Variable para preparar y ejecutar la consulta //
     ConnectionsBD.ConnectionBD objConect = new ConnectionsBD.ConnectionBD();//Crea una instancia de ConnectionBD//
     try{
            ps=objConect.conect().prepareStatement(sql);//Establece la conexion con la base de datos//
            ps.setString(1, username);//Asigna al primer ? al username//
            rs=ps.executeQuery();//Ejecuta la consulta y almacena el resultado//
            if(rs.next()){
                String name=rs.getString("nombre");
                String lastName=rs.getString("apellido");
                int dni=rs.getInt("dni");
                String userName=rs.getString("username");
                String rol=rs.getString("perfil");
                String state=rs.getString("estado");
                User user= new User(name,lastName,dni,userName,rol,state);
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
//Metodo para dar de baja a un usuario//
public void cancelUser(User user)throws SQLException, ClassNotFoundException, IOException{
    String sql="UPDATE Usuario SET estado=? WHERE username=?;";
    PreparedStatement ps=null;//Variable para preparar y ejecutar la consulta //
    ConnectionsBD.ConnectionBD objConect = new ConnectionsBD.ConnectionBD();//Crea una instancia de ConnectionBD//
    try{
            ps=objConect.conect().prepareStatement(sql);//Establece la conexion con la base de datos//
            ps.setString(1, "baja");//Asigna al primer ? al username//
            ps.setString(2, user.getUserName());
            ps.executeUpdate();//Ejecuta la consulta y almacena el resultado//
  }catch(ClassNotFoundException | SQLException e){
     e.printStackTrace();
     throw e;
 }finally{
     if (ps != null) try { ps.close(); } catch (SQLException e) { e.printStackTrace(); }
     if (objConect != null) try { objConect.close(); } catch (SQLException e) { e.printStackTrace(); }
    }   
}
}