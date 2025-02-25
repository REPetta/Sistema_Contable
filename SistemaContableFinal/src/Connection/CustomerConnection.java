
package Connection;

import Model.Customer;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Rodrigo
 */
public class CustomerConnection {
    
    //Metodo para agregar un cliente//
        public boolean addCustomer(Customer customer) throws SQLException{
        
            String sql= "INSERT INTO Cliente(nombreCliente,apellidoCliente,razonSocial,dni,condicionIva,tipoCliente,email) VALUES (?,?,?,?,?,?,?);";
            Connections con= new Connections();
            try(PreparedStatement ps= con.connect().prepareStatement(sql) ){
                ps.setString(1, customer.getClientName());
                ps.setString(2, customer.getClientSurname());
                ps.setString(3, customer.getSocialReason());
                ps.setInt(4, customer.getDni());
                ps.setString(5,customer.getIvaCondition());
                ps.setString(6, customer.getClientType());
                ps.setString(7, customer.getEmail());
                
                int rowsAffected=ps.executeUpdate();
                return rowsAffected>0;
                
            }catch(SQLException e){
                    System.err.println("Error al cargar el usuario: " + e.getMessage());
                     throw e; 
                }
        }
    //Metodo para obtener una lista de clientes//
    public List<Customer> getCustomers() throws SQLException{
        String sql= "SELECT * FROM Cliente";
        Customer customer;
        List<Customer> customers=new ArrayList<>();
        Connections con= new Connections();
            try(PreparedStatement ps= con.connect().prepareStatement(sql) ){
                 try(ResultSet rs=ps.executeQuery()){
                     while(rs.next()){
                         customer=new Customer();
                         customer.setIdClient(rs.getInt("idCliente"));
                         customer.setClientName(rs.getString("nombreCliente"));
                         customer.setClientSurname(rs.getString("apellidoCliente"));
                         customer.setSocialReason(rs.getString("razonSocial"));
                         customer.setDni(rs.getInt("dni"));
                         customer.setIvaCondition(rs.getString("condicionIva"));
                         customer.setClientType(rs.getString("tipoCliente"));
                         customer.setEmail(rs.getString("email"));
                         customers.add(customer);
                     }
                 }catch(SQLException e){
                    System.err.println("Error al cargar el usuario: " + e.getMessage());
                     throw e; 
                }
            }
        return customers;
    }
    //Metodo para validar si el usuario ya existe//
    public boolean isClientExist(int dni) throws SQLException{
        String sql="SELECT * FROM Cliente WHERE dni=?;";
        Connections con= new Connections();
            try(PreparedStatement ps= con.connect().prepareStatement(sql) ){
                ps.setInt(1, dni);
                try(ResultSet rs=ps.executeQuery()){
                    if(rs.next()){
                        return true;
                    }
                }
                }catch(SQLException e){
                     System.err.println("Error al validar el Cliente: " + e.getMessage());
                     throw e; 
                }
        return false;
    }
}
