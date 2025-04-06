
package Connection;



import Model.Bill;
import Model.BillNode;
import Model.Remito;
import Model.Sale;
import Model.SaleDetails;
import Model.SaleType;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Rodrigo
 */
public class SalesConnection {
    
    //Metodo para agregar un tipoVenta//
    public boolean addTypeSale(SaleType tipoVenta) throws SQLException{
        
            String sql= "INSERT INTO TipoVenta(descripcion,estado,plazoPago,codigo,cuotas,descuento,tipo) VALUES (?,?,?,?,?,?,?);";
            Connections con= new Connections();
            try(PreparedStatement ps= con.connect().prepareStatement(sql) ){
                ps.setString(1, tipoVenta.getSaleDescription());
                ps.setString(2, "disponible");
                ps.setInt(3, tipoVenta.getPaymentTerm());
                ps.setInt(4, tipoVenta.getCode());
                ps.setInt(5, tipoVenta.getQuotas());
                ps.setDouble(6, tipoVenta.getDiscount());
                ps.setString(7, tipoVenta.getType());
                
                 int rowsAffected=ps.executeUpdate();
                return rowsAffected>0;
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
                         saleType.setSaleState(rs.getString("estado"));
                         saleType.setPaymentTerm(rs.getInt("plazoPago"));
                         saleType.setQuotas(rs.getInt("cuotas"));
                         saleType.setDiscount(rs.getDouble("descuento"));
                         saleType.setCode(rs.getInt("codigo"));
                         saleType.setType(rs.getString("tipo"));
                         saleTypes.add(saleType);
                     }
                 }catch(SQLException e){
                    System.err.println("Error al cargar el usuario: " + e.getMessage());
                     throw e; 
                }
            }
        return saleTypes;
    }
     //Metodo para obtener una un usuario//
    public SaleType  getMethod(int code) throws SQLException{
        String sql= "SELECT * FROM TipoVenta WHERE  codigo=?;";
        SaleType method=new SaleType();
        Connections con= new Connections();
            try(PreparedStatement ps= con.connect().prepareStatement(sql) ){
                ps.setInt(1, code);
                 try(ResultSet rs=ps.executeQuery()){
                     if(rs.next()){
                         method.setIdSaleType(rs.getInt("idTipoVenta"));
                         method.setSaleDescription(rs.getString("descripcion"));
                         method.setSaleState(rs.getString("estado"));
                         method.setPaymentTerm(rs.getInt("plazoPago"));
                         method.setQuotas(rs.getInt("cuotas"));
                         method.setDiscount(rs.getDouble("descuento"));
                         method.setType(rs.getString("tipo"));
                     }
                 }catch(SQLException e){
                    System.err.println("Error al cargar el usuario: " + e.getMessage());
                     throw e; 
                }
            }
            return method;
    }
    public boolean updateMethod(SaleType method) throws SQLException{
        String sql = "UPDATE TipoVenta SET descripcion=?, estado=?, plazoPago=?, cuotas=?, descuento=?, tipo=? WHERE codigo=?;";
    Connections con = new Connections();
    try (PreparedStatement ps = con.connect().prepareStatement(sql)) {
        ps.setString(1, method.getSaleDescription());
        ps.setString(2, method.getSaleState());
        ps.setInt(3, method.getPaymentTerm());
        ps.setInt(4, method.getQuotas());
        ps.setDouble(5, method.getDiscount());
        ps.setString(6, method.getType());
        ps.setInt(7, method.getCode());
        
        int rowsAffected = ps.executeUpdate();
        return rowsAffected > 0;
        
    } catch (SQLException e) {
        System.err.println("Error al actualizar el cliente: " + e.getMessage());
        throw e;
    }
    }
      //Metodo para cargar una factura//
    public void addBill(Bill factura, int idVenta) throws SQLException{
        
            String sql= "INSERT INTO Factura(idVenta, numeroFactura, fechaFactura, totalFactura ,estadoFactura ,tipoFactura) VALUES (?,?,?,?,?,?);";
            Connections con= new Connections();
            try(PreparedStatement ps= con.connect().prepareStatement(sql) ){
                ps.setInt(1, idVenta);
                ps.setInt(2, factura.getBillNumber());
                ps.setDate(3, (Date) factura.getBillDate());
                ps.setDouble(4, factura.getBillTotal());
                ps.setString(5,Character.toString(factura.getBillState()));
                ps.setString(6,Character.toString(factura.getBillType()));
                
                ps.executeUpdate();
            }catch(SQLException e){
                    System.err.println("Error al cargar la factura: " + e.getMessage());
                     throw e; 
                }
        }
    //Metodo para cargar una factura//
    public int getIDBill() throws SQLException{
        
            String sql= "SELECT idFactura FROM Factura ORDER BY idFactura DESC LIMIT 1;";
            int idFactura=-1;
            Connections con= new Connections();
            try(PreparedStatement ps= con.connect().prepareStatement(sql) ){
                try(ResultSet rs=ps.executeQuery()){
                    if (rs.next()) { // Esto mueve el cursor a la primera fila
                    idFactura = rs.getInt("idFactura");
                }
                }
            }catch(SQLException e){
                    System.err.println("Error al cargar la factura: " + e.getMessage());
                     throw e; 
                }
        return idFactura;
        }
    //Metodo para obtener una lista de factura  en una fecha dada//
     public List<BillNode> getBills(Date fecha,int codigo,int dni) throws SQLException{
        String sql= "SELECT f.idFactura, f.numeroFactura, f.totalFactura, f.tipoFactura, \n" +
"       c.nombreCliente AS nombre_cliente, \n" +
"       a.nombreArticulo AS nombre_producto \n" +
"FROM Factura AS f \n" +
"INNER JOIN Venta AS v ON f.idVenta = v.idVenta \n" +
"INNER JOIN Cliente AS c ON v.idCliente = c.idCliente \n" +
"INNER JOIN DetalleVenta AS dv ON v.idVenta = dv.idVenta \n" +
"INNER JOIN Articulo AS a ON dv.idArticulo = a.idArticulo \n" +
"WHERE f.fechaFactura = ?  \n" +
"AND f.idFactura IN (\n" +
"    SELECT DISTINCT f.idFactura \n" +
"    FROM Factura f\n" +
"    INNER JOIN Venta v ON f.idVenta = v.idVenta\n" +
"    INNER JOIN DetalleVenta dv ON v.idVenta = dv.idVenta\n" +
"    INNER JOIN Articulo a ON dv.idArticulo = a.idArticulo\n" +
"    WHERE f.fechaFactura = ? -- Mismo filtro de fecha \n" +
"    AND (a.codigoArticulo = ? OR ? = -1) -- Filtro por código de artículo\n" +
")\n" +
"AND (c.dni = ? OR ? = -1) -- Filtro opcional por DNI\n" +
"ORDER BY f.idFactura;";
        List<BillNode> facturas=new ArrayList<>();
        Connections con= new Connections();
            try(PreparedStatement ps= con.connect().prepareStatement(sql) ){
                    ps.setDate(1, fecha);
                    ps.setDate(2, fecha);
                    ps.setInt(5, dni);
                    ps.setInt(6, dni);
                    ps.setInt(3, codigo);
                    ps.setInt(4, codigo);
                    try (ResultSet rs = ps.executeQuery()) {
            Map<Integer, BillNode> facturaMap = new HashMap<>();

            while (rs.next()) {
                int idFactura = rs.getInt("idFactura");

                if (facturaMap.containsKey(idFactura)) {
                    facturaMap.get(idFactura).setItem(rs.getString("nombre_producto"));
                } else {
                    Bill bill = new Bill(idFactura, rs.getInt("numeroFactura"), rs.getDouble("totalFactura"), rs.getString("tipoFactura").charAt(0));
                    List<String> items = new ArrayList<>();
                    items.add(rs.getString("nombre_producto"));

                    BillNode billNode = new BillNode(bill, rs.getString("nombre_cliente"), items);
                    facturaMap.put(idFactura, billNode);
                }
            }

            facturas.addAll(facturaMap.values());
        }
    
                 }catch(SQLException e){
                    System.err.println("Error en obtener las facturas: " + e.getMessage());
                     throw e; 
                }
            
        return facturas;
    }

          //Metodo para cargar un remito//
     public void addRemito(Remito remito, int idVenta) throws SQLException{
        
            String sql= "INSERT INTO remito(idVenta, numeroFactura, fechaRemito ,estadoFactura ,destino) VALUES (?,?,?,?,?);";
            Connections con= new Connections();
            try(PreparedStatement ps= con.connect().prepareStatement(sql) ){
                ps.setInt(1, idVenta);
                ps.setInt(2, remito.getRemitoNumber());
                ps.setDate(3, (Date) remito.getRemitoDate());
                ps.setString(4,Character.toString(remito.getRemitoState()));
                ps.setString(6,remito.getDestiny());
                
                ps.executeQuery();
            }catch(SQLException e){
                    System.err.println("Error al cargar el remito: " + e.getMessage());
                     throw e; 
                }
        }
    //Metodo para obtener remitos//
     public List<Remito> getRemitos() throws SQLException{
        String sql= "SELECT * FROM Factura";
        Remito remito;
        List<Remito> remitos=new ArrayList<>();
        Connections con= new Connections();
            try(PreparedStatement ps= con.connect().prepareStatement(sql) ){
                 try(ResultSet rs=ps.executeQuery()){
                     while(rs.next()){
                         remito=new Remito();
                         remito.setIdRemito(rs.getInt("idRemito"));
                         remito.setIdSale(rs.getInt("idVenta"));
                         remito.setRemitoNumber(rs.getInt("numeroRemito"));
                         remito.setRemitoDate(rs.getDate("fechaRemito"));
                         remito.setRemitoState(rs.getString("estadoFactura").charAt(0));
                         remito.setDestiny(rs.getString("tipoFactura"));
                         remitos.add(remito);
                     }
                 }catch(SQLException e){
                    System.err.println("Error en obtener los remitos: " + e.getMessage());
                     throw e; 
                }
            }
        return remitos;
    }
       //Metodo para obtener el numero de comprobante una factura//
     public int getReceiptNumberBill() throws SQLException{
        String sql="SELECT COALESCE(MAX(numeroFactura), -1) AS ultimoNumeroFactura FROM Factura WHERE estadoFactura = 'V' ";
        Connections con= new Connections();
            try(PreparedStatement ps= con.connect().prepareStatement(sql) ){
                try(ResultSet rs=ps.executeQuery()){
                    if(rs.next()){
                        return rs.getInt("ultimoNumeroFactura");
                    }
                }catch(SQLException e){
                        e.printStackTrace();
                    }
        }
        return -1;
    
      }
      //Metodo para obtener el numero de comprobante una venta//
     public int getReceiptNumber() throws SQLException{
        String sql="SELECT COALESCE(MAX(numeroComprobante), -1) AS ultimoNumeroComprobante FROM Venta WHERE estado = 'V' ";
        Connections con= new Connections();
            try(PreparedStatement ps= con.connect().prepareStatement(sql) ){
                try(ResultSet rs=ps.executeQuery()){
                    if(rs.next()){
                        return rs.getInt("ultimoNumeroComprobante");
                    }
                }catch(SQLException e){
                        e.printStackTrace();
                    }
        }
        return -1;
    
      }
    //Metodo el id una venta//
     public int getIdLastSale() throws SQLException{
        String sql="SELECT COALESCE(MAX(idVenta), 0) AS ultimoIdVenta FROM Venta WHERE estado = 'V' ";
        Connections con= new Connections();
            try(PreparedStatement ps= con.connect().prepareStatement(sql) ){
                try(ResultSet rs=ps.executeQuery()){
                    if(rs.next()){
                        return rs.getInt("ultimoIdVenta");
                    }
                }catch(SQLException e){
                        e.printStackTrace();
                    }
        }
        return 0;
    
      }     
     
    //Metodo para cargar una venta//
     public void  addSale(Sale sale ) throws SQLException{
           String sql= "INSERT INTO Venta(idUsuario, idCliente, fechaVenta , numeroComprobante , totalVenta, estado, idTipoVenta) VALUES (?,?,?,?,?,?,?);";
           Connections con= new Connections();
            try(PreparedStatement ps= con.connect().prepareStatement(sql) ){
                ps.setInt(1, sale.getIdUser());
                ps.setInt(2, sale.getIdClient());
                ps.setDate(3, (Date) sale.getSaleDate());
                ps.setInt(4,sale.getReceiptNumber());
                ps.setDouble(5,sale.getSalesTotal());
                ps.setString(6,Character.toString(sale.getSaleState()));
                ps.setInt(7, sale.getIdSaleType());
                
                ps.executeUpdate();
                
            }catch(SQLException e){
                    System.err.println("Error al cargar la venta: " + e.getMessage());
                     throw e; 
                }
     }
     //Metodo para obtener una lista de ventas//
     public List<Sale> getSales() throws SQLException{
        String sql= "SELECT * FROM Venta";
        Sale sale;
        List<Sale> sales=new ArrayList<>();
        Connections con= new Connections();
            try(PreparedStatement ps= con.connect().prepareStatement(sql) ){
                 try(ResultSet rs=ps.executeQuery()){
                     while(rs.next()){
                         sale=new Sale();
                         sale.setIdUser(rs.getInt("idUsuario"));
                         sale.setIdClient(rs.getInt("idCliente"));
                         sale.setSaleDate(rs.getDate("fechaVenta"));
                         sale.setReceiptNumber(rs.getInt("numeroComprobante"));
                         sale.setSalesTotal(rs.getDouble("totalVenta"));
                         sale.setSaleState(rs.getString("estado").charAt(0));
                         sale.setIdSaleType(rs.getInt("idTipoVenta"));
                         sales.add(sale);
                     }
                 }catch(SQLException e){
                    System.err.println("Error en obtener los remitos: " + e.getMessage());
                     throw e; 
                }
            }
        return sales;
    }
     //Metodo para caragar el detalle de una venta //
     public void  addSaleDetails(SaleDetails detalleVenta ) throws SQLException{
            String sql= "INSERT INTO DetalleVenta(idArticulo, idVenta, cantidad , precioVenta ,subTotal) VALUES (?,?,?,?,?);";
            Connections con= new Connections();
            try(PreparedStatement ps= con.connect().prepareStatement(sql) ){
                ps.setInt(1, detalleVenta.getIdItem());
                ps.setInt(2, detalleVenta.getIdSale());
                ps.setInt(3, detalleVenta.getQuantity());
                ps.setDouble(4, detalleVenta.getSalePrice());
                ps.setDouble(5,detalleVenta.getSubTotal());
               
                ps.executeUpdate();

            }catch(SQLException e){
                    System.err.println("Error al cargar la venta: " + e.getMessage());
                     throw e; 
                }
     }
     //Metodo para sabe si exitste ese metodo de pago//
     public boolean isMethodExist(int code) throws SQLException{
        String sql="SELECT * FROM TipoVenta WHERE codigo=?;";
        Connections con= new Connections();
            try(PreparedStatement ps= con.connect().prepareStatement(sql) ){
                ps.setInt(1, code);
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
     //Metodo para obtener las ventas entre dos fechas dadas
      public List<Sale> getSalesBetweenDates(Date startDate, Date endDate) throws SQLException {
    String sql = "SELECT * FROM Venta WHERE fechaVenta BETWEEN ? AND ?";
    List<Sale> sales = new ArrayList<>();
    Connections con = new Connections();

    try (PreparedStatement ps = con.connect().prepareStatement(sql)) {
        ps.setDate(1, startDate);
        ps.setDate(2, endDate);

        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Sale sale = new Sale();
                sale.setIdUser(rs.getInt("idUsuario"));
                sale.setIdClient(rs.getInt("idCliente"));
                sale.setSaleDate(rs.getDate("fechaVenta"));
                sale.setReceiptNumber(rs.getInt("numeroComprobante"));
                sale.setSalesTotal(rs.getDouble("totalVenta"));
                sale.setSaleState(rs.getString("estado").charAt(0));
                sale.setIdSaleType(rs.getInt("idTipoVenta"));
                sales.add(sale);
            }
        }
    } catch (SQLException e) {
        System.err.println("Error al obtener las ventas entre fechas: " + e.getMessage());
        throw e;
    }
    return sales;
}  
      
} 
     

