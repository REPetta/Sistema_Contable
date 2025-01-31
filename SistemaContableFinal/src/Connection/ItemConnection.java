
package Connection;

import Model.Item;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Rodrigo
 */
public class ItemConnection {
    
    //Metodo para agregar un item//
    public void addItem(Item item) throws SQLException{
        
            String sql= "INSERT INTO Item(nombreArticulo,descripcion,precioUnitario,stock) VALUES (?,?,?,?);";
            Connections con= new Connections();
            try(PreparedStatement ps= con.connect().prepareStatement(sql) ){
                ps.setString(1, item.getItemName());
                ps.setString(2, item.getItemDescription());
                ps.setDouble(3, item.getUnitPrice());
                ps.setInt(4, item.getStock());
            }catch(SQLException e){
                    System.err.println("Error al cargar el usuario: " + e.getMessage());
                     throw e; 
                }
        }
    //Metodo para obtener una lista de items//
    public List<Item> getItems() throws SQLException{
        String sql= "SELECT * FROM Articulo";
        Item item;
        List<Item> items=new ArrayList<>();
        Connections con= new Connections();
            try(PreparedStatement ps= con.connect().prepareStatement(sql) ){
                 try(ResultSet rs=ps.executeQuery()){
                 while(rs.next()){
                         item=new Item();
                         item.setIdItem(rs.getInt("idArticulo"));
                         item.setItemDescription(rs.getString("descripcion"));
                         item.setItemName(rs.getString("nombreArticulo"));
                         item.setUnitPrice(rs.getDouble("precioUnitario"));
                         item.setStock(rs.getInt("stock"));
                         items.add(item);
                     }
                 }catch(SQLException e){
                    System.err.println("Error al cargar el usuario: " + e.getMessage());
                     throw e; 
                }
            }
        return items;
    }

}
