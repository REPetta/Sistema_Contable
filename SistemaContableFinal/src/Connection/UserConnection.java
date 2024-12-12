//Clase encargada de las consultas de usuario//
package Connection;

import Model.SingletonUser;
import Model.User;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

public class UserConnection {
    
     //Metodo para buscar un usuario en base de datos y traer su datos//
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
    
    //Metodo para validar si el usuario ya existe//
    public boolean isUserExist(String userName) throws SQLException{
        String sql="SELECT * FROM Usuario WHERE username=?;";
        Connections con= new Connections();
            try(PreparedStatement ps= con.connect().prepareStatement(sql) ){
                ps.setString(1, userName);
                try(ResultSet rs=ps.executeQuery()){
                    if(rs.next()){
                        return true;
                    }
                }
                }catch(SQLException e){
                     System.err.println("Error al validar el usuario: " + e.getMessage());
                     throw e; 
                }
        return false;
    }
    
    //Metodo para cargar el usuario en la base de datos//
    public boolean addUser(User user , String rol) throws SQLException{
        String sql="INSERT INTO Usuario(nombre,apellido,dni,estado,username,password,idperfil) VALUES(?,?,?,'alta',?,?,?);";
         Connections con= new Connections();
            try(PreparedStatement ps= con.connect().prepareStatement(sql) ){
                ps.setString(1, user.getName());
                ps.setString(2, user.getLastName());
                ps.setInt(3, user.getDni());
                ps.setString(4, user.getUserName());
                ps.setString(5, user.getPassword());
                if(rol.equalsIgnoreCase("ADMINISTRADOR")){
                    ps.setInt(6, 1);
                }
               if(rol.equalsIgnoreCase("CONTADOR")){
                    ps.setInt(6, 2);
                }
                if(rol.equalsIgnoreCase("VENDEDOR")){
                    ps.setInt(6, 3);
                }
                
                int rowsAffected=ps.executeUpdate();
                return rowsAffected>0;
                
                }catch(SQLException e){
                    System.err.println("Error al cargar el usuario: " + e.getMessage());
                     throw e; 
                }
            }
    
   //Metodo para dar de baja un usuario//
      public boolean delUser(User user ) throws SQLException{
        String sql="UPDATE Usuario SET estado = 'baja' WHERE userName=?;";
         Connections con= new Connections();
            try(PreparedStatement ps= con.connect().prepareStatement(sql) ){
                ps.setString(1, user.getUserName());
                int columnsAffected=ps.executeUpdate();
                return columnsAffected>0;
                
                }catch(SQLException e){
                    System.err.println("Error al cargar el usuario: " + e.getMessage());
                     throw e; 
                }
            }
    
    
    }
    
