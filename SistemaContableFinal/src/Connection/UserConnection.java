//Clase encargada de las consultas de usuario//
package Connection;

import Model.User;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

public class UserConnection {
 
    public User getUserValid(String userName ) throws SQLException{
    String sql ="SELECT u.* , t.tarea  FROM Usuario u INNER JOIN Perfiles p ON u.idPerfil=p.idPerfil INNER JOIN Perfiles_Tareas pt ON p.idPerfil=pt.idPerfil INNER JOIN Tareas t ON pt.idtarea=t.idTarea  WHERE userName=? AND estado='alta'; ";
    User user =null;
    Connections con= new Connections();
            try(PreparedStatement ps= con.connect().prepareStatement(sql) ){
                ps.setString(1, userName);
                try(ResultSet rs=ps.executeQuery()){
                        if(rs.next()){
                        user=new User();
                        user.setUserName(userName);
                        user.setPassword(rs.getString("password"));
                        user.setDni(rs.getInt("dni"));
                        user.setLastName(rs.getString("apellido"));
                        user.setName(rs.getString("nombre"));
                        
                        //Obtener las tareas//
                        List<String> tasks=new ArrayList<>();
                        do{
                            String tarea =rs.getString("tarea");
                            if(tarea!=null){
                                tasks.add(tarea);
                            }
                        }while(rs.next());
                        
                        user.setTasks(tasks);
                        }
                }
            }catch(SQLException e){
                // Manejo de excepciones//
                System.err.println("Error al obtener el usuario: " + e.getMessage());
                throw e; // Rethrow la excepción para que el llamador pueda manejarla//
            }
            return user;
     }
        
    
}
