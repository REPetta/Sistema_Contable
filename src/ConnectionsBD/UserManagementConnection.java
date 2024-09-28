//Clase encargada de las consultas e insersiones a tabla Usuarios de la base de datos//
package ConnectionsBD;

import Model.User;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserManagementConnection {

//Metodo para retornar el idUsuario del userName pasado//
public int getIdUser(String userName)throws ClassNotFoundException, SQLException, IOException{
     String sql="SELECT idusuario FROM Usuario WHERE username=?;";
     int idUser=-1;
        try{
            //Atributos//
            
            ResultSet rs=null;//Variable para almacenar el resultado de la consulta//
            PreparedStatement ps=null;//Variable para preparar y ejecutar la consulta //
            
            ConnectionsBD.ConnectionBD objConect = new ConnectionsBD.ConnectionBD();//Crea una instancia de ConnectionBD//
            ps=objConect.conect().prepareStatement(sql);//Establece la conexion con la base de datos//
            ps.setString(1, userName);//Asigna al primer ? el userName//
            rs=ps.executeQuery();//Ejecuta la consulta y almacena el resultado//
            if(rs.next()){//Next verifica si el registro de rs coincide con la consulta y si no lo hace avanza al siguiente, hasta que no haya mas filas en la tabla//
                idUser=rs.getInt("idusuario");  //Guarda el idusuario en una variable para retornar//
                return idUser;
            }
            objConect.closeConnect();//Cierra la conexion con la base de datos//
     }catch(ClassNotFoundException | SQLException e){}
     return idUser;
}
//Metodo para obtener las tareas del Usuario//
public void getTasks(User user,String userName) throws ClassNotFoundException, SQLException, IOException{
     String sql="SELECT t.tarea FROM Usuario AS u INNER JOIN Perfiles AS p ON u.idperfil=p.idperfil INNER JOIN Perfiles_Tareas AS tp ON p.idperfil=tp.idperfil INNER JOIN Tareas AS t ON tp.idtarea=t.idtarea WHERE u.idusuario=?;";
     try{
            //Atributos//
            ResultSet rs=null;//Variable para almacenar el resultado de la consulta//
            PreparedStatement ps=null;//Variable para preparar y ejecutar la consulta //
            int usurID=getIdUser(userName);//Guarda el idusuario en una variable para retornar//
            
            ConnectionsBD.ConnectionBD objConect = new ConnectionsBD.ConnectionBD();//Crea una instancia de ConnectionBD//
            ps=objConect.conect().prepareStatement(sql);//Establece la conexion con la base de datos//
            ps.setInt(1,usurID);//Asigna al primer ? el usuarioId//
            rs=ps.executeQuery();//Ejecuta la consulta y almacena el resultado//
            //Agrega todas las tareas del usuario en la base de datos a la instancia de usuario//
            while(rs.next()){   
                String taskName=rs.getString("tarea");
                user.setTask(taskName);
            }
            objConect.closeConnect();//Cierra la conexion
 }catch(ClassNotFoundException | SQLException e){
 }
    
}
//Metodo para guardar las columnas de usuario en la clase User y retornar esta//
public User getUserColumns(int iduser) throws SQLException, ClassNotFoundException, IOException{
     String sql="SELECT u.nombre, u.apellido, u.dni, u.username, u.password, p.perfil FROM Usuario AS u INNER JOIN Perfiles   AS p ON u.idperfil=p.idperfil WHERE idusuario=?;";//Consulta sql//
     User user;
     try{
         //Atributos//
            
            ResultSet rs=null;//Variable para almacenar el resultado de la consulta//
            PreparedStatement ps=null;//Variable para preparar y ejecutar la consulta //
            
            ConnectionsBD.ConnectionBD objConect = new ConnectionsBD.ConnectionBD();//Crea una instancia de ConnectionBD//
            ps=objConect.conect().prepareStatement(sql);//Establece la conexion con la base de datos//
            ps.setInt(1, iduser);//Asigna al primer ? el iduser//
            rs=ps.executeQuery();//Ejecuta la consulta y almacena el resultado//
            if(rs.next()){
                String name=rs.getString("nombre");
                String lastName=rs.getString("apellido");
                int dni=rs.getInt("dni");
                String userName=rs.getString("username");
                String password=rs.getString("password");
                String rol=rs.getString("perfil");
                user=new User(name, lastName, dni, userName, password,rol);
                return user;
            }
            objConect.closeConnect();//Cierra la conexion
     }catch(ClassNotFoundException | SQLException e){}
    return null;
     }
     //Metodo para retornar el perfil//
     public String getPerfil(String userName) throws ClassNotFoundException, SQLException, IOException{
         String sql="SELECT p.perfil From Usuario AS u INNER JOIN Perfiles AS p ON u.idperfil=p.idperfil WHERE u.idusuario=?;";//Consulta sql//
         try{
             //Atributos//
            
            ResultSet rs=null;//Variable para almacenar el resultado de la consulta//
            PreparedStatement ps=null;//Variable para preparar y ejecutar la consulta //
            int usurID=getIdUser(userName);//Guarda el idusuario en una variable para retornar//
            
            ConnectionsBD.ConnectionBD objConect = new ConnectionsBD.ConnectionBD();//Crea una instancia de ConnectionBD//
            ps=objConect.conect().prepareStatement(sql);//Establece la conexion con la base de datos//
            ps.setInt(1,usurID);//Asigna al primer ? el usuarioId//
            rs=ps.executeQuery();//Ejecuta la consulta y almacena el resultado//
            //Si la consulta coicide // 
            if(rs.next()){
                 String rol=rs.getString("perfil");
                 return rol;
             }
         }catch(ClassNotFoundException | SQLException e){}
    return null;
     }
}
