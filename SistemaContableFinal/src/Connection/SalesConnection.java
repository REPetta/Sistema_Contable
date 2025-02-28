
package Connection;

import Model.Bill;
import Model.Item;
import Model.Remito;
import Model.Sale;
import Model.SaleDetails;
import Model.SaleType;
import java.sql.Date;
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
    public boolean addTypeSale(SaleType tipoVenta) throws SQLException{
        
            String sql= "INSERT INTO TipoVenta(descripcion,estado,plazoPago,codigo,cuotas,descuento) VALUES (?,?,?,?,?,?);";
            Connections con= new Connections();
            try(PreparedStatement ps= con.connect().prepareStatement(sql) ){
                ps.setString(1, tipoVenta.getSaleDescription());
                ps.setString(2, "disponible");
                ps.setInt(3, tipoVenta.getPaymentTerm());
                ps.setInt(4, tipoVenta.getCode());
                ps.setInt(5, tipoVenta.getQuotas());
                ps.setDouble(6, tipoVenta.getDiscount());
                
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
                         method.setSaleDescription(rs.getString("descripcion"));
                         method.setSaleState(rs.getString("estado"));
                         method.setPaymentTerm(rs.getInt("plazoPago"));
                         method.setQuotas(rs.getInt("cuotas"));
                         method.setDiscount(rs.getDouble("descuento"));
                     }
                 }catch(SQLException e){
                    System.err.println("Error al cargar el usuario: " + e.getMessage());
                     throw e; 
                }
            }
            return method;
    }
    public boolean updateMethod(SaleType method) throws SQLException{
        String sql = "UPDATE TipoVenta SET descripcion=?, estado=?, plazoPago=?, cuotas=?, descuento=? WHERE codigo=?;";
    Connections con = new Connections();
    System.out.print("Entro aca");
    try (PreparedStatement ps = con.connect().prepareStatement(sql)) {
        ps.setString(1, method.getSaleDescription());
        ps.setString(2, method.getSaleState());
        ps.setInt(3, method.getPaymentTerm());
        ps.setInt(4, method.getQuotas());
        ps.setDouble(5, method.getDiscount());
        ps.setInt(6, method.getCode());
        System.out.print(method.getCode());
        System.out.print("Entro aca");
        int rowsAffected = ps.executeUpdate();
        System.out.print(rowsAffected);
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
            }catch(SQLException e){
                    System.err.println("Error al cargar la factura: " + e.getMessage());
                     throw e; 
                }
        }
    //Metodo para obtener una lista de factura //
     public List<Bill> getBills() throws SQLException{
        String sql= "SELECT * FROM Factura";
        Bill factura;
        List<Bill> facturas=new ArrayList<>();
        Connections con= new Connections();
            try(PreparedStatement ps= con.connect().prepareStatement(sql) ){
                 try(ResultSet rs=ps.executeQuery()){
                     while(rs.next()){
                         factura=new Bill();
                         factura.setIdBill(rs.getInt("idFactura"));
                         factura.setIdSale(rs.getInt("idVenta"));
                         factura.setBillNumber(rs.getInt("numeroFactura"));
                         factura.setBillDate(rs.getDate("fechaFactura"));
                         factura.setBillTotal(rs.getDouble("totalFactura"));
                         factura.setBillState(rs.getString("estadoFactura").charAt(0));
                         factura.setBillType(rs.getString("tipoFactura").charAt(0));
                         facturas.add(factura);
                     }
                 }catch(SQLException e){
                    System.err.println("Error en obtener las facturas: " + e.getMessage());
                     throw e; 
                }
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
                ps.setString(6,Character.toString(sale.getIdSaleType()));
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
     }

