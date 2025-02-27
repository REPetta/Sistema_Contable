
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
    public boolean addItem(Item item) throws SQLException{
        
            String sql= "INSERT INTO Item(nombreArticulo,descripcion,precioUnitario,stock,estado , codigoArticulo) VALUES (?,?,?,?,?,?);";
            Connections con= new Connections();
            try(PreparedStatement ps= con.connect().prepareStatement(sql) ){
                ps.setString(1, item.getItemName());
                ps.setString(2, item.getItemDescription());
                ps.setDouble(3, item.getUnitPrice());
                ps.setInt(4, item.getStock());
                ps.setString(5, "alta");
                ps.setInt(6, item.getItemCode());
                
                 int rowsAffected=ps.executeUpdate();
                return rowsAffected>0;
            }catch(SQLException e){
                    System.err.println("Error al cargar el usuario: " + e.getMessage());
                     throw e; 
                }
        }
    //Metodo para obtener una lista de items//
    public List<Item> getItems() throws SQLException{
        String sql= "SELECT * FROM Articulo WHERE estado='alta';";
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
                         item.setItemCode(rs.getInt("codigoArticulo"));
                         items.add(item);
                     }
                 }catch(SQLException e){
                    System.err.println("Error al cargar el usuario: " + e.getMessage());
                     throw e; 
                }
            }
        return items;
    }
    // Método para dar de baja a un cliente (cambiar estado a "baja")
public boolean desactivateItem(int itemCode) throws SQLException {
    String sql = "UPDATE Articulo SET estado=? WHERE codigoArticulo=?;";
    Connections con = new Connections();

    try (PreparedStatement ps = con.connect().prepareStatement(sql)) {
        ps.setString(1, "baja"); // Solo actualiza el estado a "baja"
        ps.setInt(2, itemCode); // Filtra por el DNI del cliente

        int rowsAffected = ps.executeUpdate();
        return rowsAffected > 0;

    } catch (SQLException e) {
        System.err.println("Error al dar de baja al cliente: " + e.getMessage());
        throw e; 
    }
}
  // Método para dar de baja a un cliente (cambiar estado a "baja")
public boolean increaseStock(int itemCode,int newStock) throws SQLException {
    String sql = "UPDATE Articulo SET stock=? WHERE codigoArticulo=?;";
    Connections con = new Connections();

    try (PreparedStatement ps = con.connect().prepareStatement(sql)) {
        ps.setInt(1, newStock); // Solo actualiza el estado a "baja"
        ps.setInt(2, itemCode); // Filtra por el DNI del cliente

        int rowsAffected = ps.executeUpdate();
        return rowsAffected > 0;

    } catch (SQLException e) {
        System.err.println("Error al dar de baja al cliente: " + e.getMessage());
        throw e; 
    }
}
}
