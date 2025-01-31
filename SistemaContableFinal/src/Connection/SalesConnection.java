
package Connection;

import Model.Item;
import Model.Sale;
import Model.SaleType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Rodrigo
 */
public class SalesConnection {
    
    //Metodo para agregar un tipoVenta//
    public void addTypeSale(SaleType tipoVenta) throws SQLException{
        
            String sql= "INSERT INTO TipoVenta(descripcion,estado,plazoPago,cuotas,descuento) VALUES (?,?,?,?,?);";
            Connections con= new Connections();
            try(PreparedStatement ps= con.connect().prepareStatement(sql) ){
                ps.setString(1, tipoVenta.getSaleDescription());
                ps.setString(2, Character.toString(tipoVenta.getSaleState()));
                ps.setInt(3, tipoVenta.getPaymentTerm());
                ps.setInt(4, tipoVenta.getQuotas());
                ps.setDouble(5, tipoVenta.getDiscount());
            }catch(SQLException e){
                    System.err.println("Error al cargar el usuario: " + e.getMessage());
                     throw e; 
                }
        }
    //Metodo para obtener una lista de los tipos de ventas//
    public List<SaleType> getSalesTypes() throws SQLException{
        String sql= "SELECT * FROM TipoVenta";
        SaleType saleType;
        List<SaleType> saleTypes=new ArrayList<>();
        Connections con= new Connections();
            try(PreparedStatement ps= con.connect().prepareStatement(sql) ){
                 try(ResultSet rs=ps.executeQuery()){
                     while(rs.next()){
                         saleType=new SaleType();
                         saleType.setIdSaleType(rs.getInt("idTipoVenta"));
                         saleType.setSaleDescription(rs.getString("descripcion"));
                         saleType.setSaleState(rs.getString("estado").charAt(0));
                         saleType.setPaymentTerm(rs.getInt("plazoPago"));
                         saleType.setQuotas(rs.getInt("cuotas"));
                         saleType.setDiscount(rs.getDouble("descuento"));
                         saleTypes.add(saleType);
                     }
                 }catch(SQLException e){
                    System.err.println("Error al cargar el usuario: " + e.getMessage());
                     throw e; 
                }
            }
        return saleTypes;
    }
}
