
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
        
            String sql= "INSERT INTO Articulo(nombreArticulo,descripcion,precioUnitario,stock,stockMinimo,estado , codigoArticulo) VALUES (?,?,?,?,?,?,?);";
            Connections con= new Connections();
            try(PreparedStatement ps= con.connect().prepareStatement(sql) ){
                ps.setString(1, item.getItemName());
                ps.setString(2, item.getItemDescription());
                ps.setDouble(3, item.getUnitPrice());
                ps.setInt(4, item.getStock());
                ps.setInt(5, item.getStockMin());
                ps.setString(6, "alta");
                ps.setInt(7, item.getItemCode());
                
                 int rowsAffected=ps.executeUpdate();
                return rowsAffected>0;
            }catch(SQLException e){
                    System.err.println("Error al cargar el usuario: " + e.getMessage());
                     throw e; 
                }
        }
    //Metodo para obtener el item//
    public Item getItem(int itemCode) throws SQLException{
       String sql= "SELECT * FROM Articulo WHERE codigoArticulo=?;";
       Item item=new Item();
       Connections con= new Connections();
            try(PreparedStatement ps= con.connect().prepareStatement(sql) ){
            ps.setInt(1, itemCode);
             try(ResultSet rs=ps.executeQuery()){
                    if(rs.next()){
                        item.setIdItem(rs.getInt("idArticulo"));
                        item.setItemName(rs.getString("nombreArticulo"));
                        item.setItemDescription(rs.getString("descripcion"));
                        item.setUnitPrice(rs.getDouble("precioUnitario"));
                        item.setStock(rs.getInt("stock"));
                        item.setStockMin(rs.getInt("stockMinimo"));
                        item.setItemCode(itemCode);
                                }
            }catch(SQLException e){
                    System.err.println("Error al cargar el usuario: " + e.getMessage());
                     throw e; 
                }    
    }
            return item;
    }
    //Metodo para validar que el item exista//
    public boolean isItemExist(int itemCode) throws SQLException{
        String sql="SELECT * FROM Articulo WHERE codigoArticulo=?;";
        Connections con= new Connections();
            try(PreparedStatement ps= con.connect().prepareStatement(sql) ){
                ps.setInt(1, itemCode);
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
public boolean increaseStock(int itemCode,int newStock, double newCost) throws SQLException {
    String sql = "UPDATE Articulo SET stock =stock + ? , precioUnitario= CASE WHEN ?=0 THEN precioUnitario ELSE ((stock*precioUnitario)+(?*?))/(stock+?) END WHERE codigoArticulo=?;";
    Connections con = new Connections();

    try (PreparedStatement ps = con.connect().prepareStatement(sql)) {
        ps.setInt(1, newStock); // Solo actualiza el estado a "baja"
        ps.setDouble(2, newCost);
        ps.setInt(3, newStock);
        ps.setDouble(4, newCost);
        ps.setInt(5,newStock);
        ps.setInt(6, itemCode); // Filtra por el DNI del cliente
        
        int rowsAffected = ps.executeUpdate();
        return rowsAffected > 0;

    } catch (SQLException e) {
        System.err.println("Error al dar de baja al cliente: " + e.getMessage());
        throw e; 
    }
}
// Método para dar de baja a un cliente (cambiar estado a "baja")
public boolean decreaseStock(int itemCode,int newStock) throws SQLException {
    String sql = "UPDATE Articulo SET stock =stock - ?  WHERE codigoArticulo=?;";
    Connections con = new Connections();

    try (PreparedStatement ps = con.connect().prepareStatement(sql)) {
        
        ps.setInt(1, newStock);
        ps.setInt(2, itemCode);
        
        int rowsAffected = ps.executeUpdate();
        return rowsAffected > 0;

    } catch (SQLException e) {
        System.err.println("Error al dar de baja al cliente: " + e.getMessage());
        throw e; 
    }
}

 // Método para dar de baja a un cliente (cambiar estado a "baja")
public boolean editStockMin(int newStockMin,int itemCode) throws SQLException {
    String sql = "UPDATE Articulo SET stockMinimo =? WHERE codigoArticulo=?;";
    Connections con = new Connections();

    try (PreparedStatement ps = con.connect().prepareStatement(sql)) {
        ps.setInt(1, newStockMin); // Solo actualiza el estado a "baja"
        ps.setInt(2, itemCode);

        
        int rowsAffected = ps.executeUpdate();
        return rowsAffected > 0;

    } catch (SQLException e) {
        System.err.println("Error al dar de baja al cliente: " + e.getMessage());
        throw e; 
    }
}
}
