/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Connection;

import Model.Account;
import Model.AccountSeat;
import Model.AccountSeatBook;
import Model.SalesBook;
import Model.Seat;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Rodrigo
 */
public class BooksConnection {
    
     //Metodo par obtener todos los asientos entre dos fechas dadas//
    public ArrayList<AccountSeatBook> obtenerListaAsientos(Date fechaMenor , Date fechaMayor) throws ClassNotFoundException, SQLException, IOException{
        ArrayList<AccountSeatBook> listaAsientos=new ArrayList<AccountSeatBook>();
        String sql= "SELECT  u.username , a.idAsiento, a.fecha , ac.idAsientoCuenta , ac.idCuenta, ac.tipo, ac.monto, a.descripcionAsiento , c.nombreCuenta , ac.saldoPostOperacion FROM Asiento a INNER JOIN Asiento_Cuenta ac ON a.idAsiento=ac.idAsiento INNER JOIN Cuenta as c ON ac.idCuenta=c.idCuenta  INNER JOIN Usuario as u ON u.idUsuario=a.idUsuario WHERE a.fecha BETWEEN ? AND  ?  ORDER BY a.fecha ASC,ac.idAsientoCuenta ASC ;";
        ResultSet rs=null;//Variable para almacenar el resultado de la consulta//
        PreparedStatement ps=null;//Variable para preparar y ejecutar la consulta //
        Connections con= new Connections();        
        AccountSeatBook currentAsiento = null;
        int lastIdSeat = -1;
          try{
            ps=con.connect().prepareStatement(sql);//Establece la conexion con la base de datos//
            ps.setDate(1, new java.sql.Date(fechaMenor.getTime() ));
            ps.setDate(2, new java.sql.Date(fechaMayor.getTime()));
            rs=ps.executeQuery(); //Ejecuta la consulta y almacena el resultado//
            
            while(rs.next()){
                int idSeat= rs.getInt("idAsiento");
                        if(idSeat!=lastIdSeat){
                            // Si estamos en un nuevo asiento, crear uno nuevo y agregarlo a la lista
                            Seat seat= new Seat();
                            seat.setUserName(rs.getString("userName"));
                            seat.setIdSeat(rs.getInt("idAsiento"));
                            seat.setSeatDate(rs.getDate("fecha"));
                            seat.setSeatDescrip(rs.getString("descripcionAsiento"));
                            currentAsiento = new AccountSeatBook();
                            currentAsiento.setSeat(seat);
                            currentAsiento.setAccountSeats(new ArrayList<>());
                            // Agregar el nuevo AccountSeatController a la lista
                            listaAsientos.add(currentAsiento);
                            // Actualizar el idSeat actual
                            lastIdSeat = idSeat;
                        }
                    // Si hay datos de Asiento_Cuenta, agregarlos a la lista del asiento actual
                        if(rs.getInt("idAsiento")!=0){
                            AccountSeat asientoCuenta= new AccountSeat();
                            asientoCuenta.setIdSeat(rs.getInt("idAsiento"));
                            asientoCuenta.setIdCuenta(rs.getInt("idCuenta"));
                            asientoCuenta.setDestiny(rs.getString("tipo"));
                            asientoCuenta.setAmount(rs.getDouble("monto"));
                            asientoCuenta.setSaldoPostOperacion(rs.getDouble("saldoPostOperacion"));
                            asientoCuenta.setAccount(rs.getString("nombreCuenta"));
                             // Agregar Asiento_Cuenta a la lista del AccountSeatController actual
                            if(currentAsiento!=null){
                                currentAsiento.getAccountSeats().add(asientoCuenta);
                            }
                        }
            }
            return listaAsientos;
            
    }catch(SQLException e){
            e.printStackTrace();
            throw e;
        }finally{
                if(rs!=null) try{rs.close(); }catch(SQLException e){e.printStackTrace(); }
                if (ps != null) try { ps.close(); } catch (SQLException e) { e.printStackTrace(); }
                if (con!= null) con.close();
    }     
} 
    
    //Metodo par obtener todos los asientos entre dos fechas dadas//
    public ArrayList<AccountSeatBook> obtenerListaFinal(Date fechaMayor ) throws ClassNotFoundException, SQLException, IOException{
        ArrayList<AccountSeatBook> listaAsientos=new ArrayList<AccountSeatBook>();
        String sql= "SELECT a.idUsuario ,a.idAsiento, a.fecha , ac.idAsientoCuenta , ac.idCuenta, ac.tipo, ac.saldoPostOperacion ,ac.monto, a.descripcionAsiento FROM Asiento a INNER JOIN Asiento_Cuenta ac ON a.idAsiento=ac.idAsiento WHERE a.fecha <=? ORDER BY a.fecha ASC,ac.idAsientoCuenta ASC ;";
        ResultSet rs=null;//Variable para almacenar el resultado de la consulta//
        PreparedStatement ps=null;//Variable para preparar y ejecutar la consulta //
        Connections con= new Connections();   //Crea una instancia de ConnectionBD//
        AccountSeatBook currentAsiento = null;
        int lastIdSeat = -1;
          try{
            ps=con.connect().prepareStatement(sql);//Establece la conexion con la base de datos//
            ps.setDate(1, new java.sql.Date(fechaMayor.getTime() ));
            rs=ps.executeQuery(); //Ejecuta la consulta y almacena el resultado//
            
            while(rs.next()){
                int idSeat= rs.getInt("idAsiento");
                        if(idSeat!=lastIdSeat){
                            // Si estamos en un nuevo asiento, crear uno nuevo y agregarlo a la lista
                            Seat seat= new Seat();
                            seat.setIdUsuario(rs.getInt("idUsuario"));
                            seat.setIdSeat(rs.getInt("idAsiento"));
                            seat.setSeatDate(rs.getDate("fecha"));
                            seat.setSeatDescrip(rs.getString("descripcionAsiento"));
                            
                            currentAsiento = new AccountSeatBook();
                            currentAsiento.setSeat(seat);
                            currentAsiento.setAccountSeats(new ArrayList<>());
                            // Agregar el nuevo AccountSeatController a la lista
                            listaAsientos.add(currentAsiento);
                            // Actualizar el idSeat actual
                            lastIdSeat = idSeat;
                        }
                    // Si hay datos de Asiento_Cuenta, agregarlos a la lista del asiento actual
                        if(rs.getInt("idAsiento")!=0){
                            AccountSeat asientoCuenta= new AccountSeat();
                            asientoCuenta.setIdSeat(rs.getInt("idAsiento"));
                            asientoCuenta.setIdCuenta(rs.getInt("idCuenta"));
                            asientoCuenta.setDestiny(rs.getString("tipo"));
                            asientoCuenta.setAmount(rs.getDouble("monto"));
                            asientoCuenta.setSaldoPostOperacion(rs.getDouble("saldoPostOperacion"));
                             // Agregar Asiento_Cuenta a la lista del AccountSeatController actual
                            if(currentAsiento!=null){
                                currentAsiento.getAccountSeats().add(asientoCuenta);
                            }
                        }
            }
            return listaAsientos;
            
    }catch(SQLException e){
            e.printStackTrace();
            throw e;
        }finally{
                if(rs!=null) try{rs.close(); }catch(SQLException e){e.printStackTrace(); }
                if (ps != null) try { ps.close(); } catch (SQLException e) { e.printStackTrace(); }
                if (con != null) con.close();
    }     
} 
    public String obtenerUltimaFecha(Date fechaMenor , Date fechaMayor , Account cuenta) throws ClassNotFoundException, SQLException, IOException{
        String fechaFinal="";
        String sql= "SELECT MAX(a.fecha) AS ultima_fecha\n" +
"FROM Asiento a \n" +
"INNER JOIN asiento_cuenta ac ON a.idAsiento = ac.idAsiento \n" +
"INNER JOIN cuenta c ON ac.idCuenta = c.idCuenta\n" +
"WHERE a.fecha BETWEEN ? AND  ? AND c.idCuenta = ?  ;";
        ResultSet rs=null;//Variable para almacenar el resultado de la consulta//
        PreparedStatement ps=null;//Variable para preparar y ejecutar la consulta //
        Connections con= new Connections();   //Crea una instancia de ConnectionBD//
          try{
            ps=con.connect().prepareStatement(sql);//Establece la conexion con la base de datos//
            ps.setDate(1, new java.sql.Date(fechaMenor.getTime() ));
            ps.setDate(2, new java.sql.Date(fechaMayor.getTime()));
            ps.setInt(3, cuenta.getIdAccount());
            rs=ps.executeQuery(); //Ejecuta la consulta y almacena el resultado//
            
            if(rs.next()){
                fechaFinal=rs.getString("ultima_fecha");
            }
            return fechaFinal;
            
    }catch(SQLException e){
            e.printStackTrace();
            throw e;
        }finally{
                if(rs!=null) try{rs.close(); }catch(SQLException e){e.printStackTrace(); }
                if (ps != null) try { ps.close(); } catch (SQLException e) { e.printStackTrace(); }
                if (con != null) con.close();
    }     
} 
    public String obtenerFechaInicial(Date fechaMenor , Date fechaMayor , Account cuenta) throws ClassNotFoundException, SQLException, IOException{
        String fechaFinal="";
        String sql= "SELECT MIN(a.fecha) AS ultima_fecha\n" +
"FROM Asiento a \n" +
"INNER JOIN asiento_cuenta ac ON a.idAsiento = ac.idAsiento \n" +
"INNER JOIN cuenta c ON ac.idCuenta = c.idCuenta\n" +
"WHERE a.fecha BETWEEN ? AND  ? AND c.idCuenta = ?  ;";
        ResultSet rs=null;//Variable para almacenar el resultado de la consulta//
        PreparedStatement ps=null;//Variable para preparar y ejecutar la consulta //
        Connections con= new Connections();   //Crea una instancia de ConnectionBD//
          try{
            ps=con.connect().prepareStatement(sql);//Establece la conexion con la base de datos//
            ps.setDate(1, new java.sql.Date(fechaMenor.getTime() ));
            ps.setDate(2, new java.sql.Date(fechaMayor.getTime()));
            ps.setInt(3, cuenta.getIdAccount());
            rs=ps.executeQuery(); //Ejecuta la consulta y almacena el resultado//
            
            if(rs.next()){
                fechaFinal=rs.getString("ultima_fecha");
            }
            return fechaFinal;
            
    }catch(SQLException e){
            e.printStackTrace();
            throw e;
        }finally{
                if(rs!=null) try{rs.close(); }catch(SQLException e){e.printStackTrace(); }
                if (ps != null) try { ps.close(); } catch (SQLException e) { e.printStackTrace(); }
                if (con != null) con.close();
    }     
}
    
    public List<SalesBook> obtenerListaVentas(int mes , int anio){
        List<SalesBook> listaVentas= new ArrayList<>();
         String sql="WITH ventas_mes AS (\n" +
"    SELECT \n" +
"        a.nombreArticulo AS Producto, \n" +
"        SUM(dv.cantidad) AS Cantidad_Vendida, \n" +
"        SUM(dv.cantidad * dv.precioVenta) AS Total_Recaudado\n" +
"    FROM DetalleVenta dv\n" +
"    JOIN Venta v ON dv.idVenta = v.idVenta\n" +
"    JOIN Articulo a ON dv.idArticulo = a.idArticulo\n" +
"    WHERE \n" +
"        EXTRACT(MONTH FROM v.FechaVenta) = ? \n" +
"        AND EXTRACT(YEAR FROM v.FechaVenta) = ?\n" +
"    GROUP BY a.nombreArticulo\n" +
"),\n" +
"ventas_mes_anterior AS (\n" +
"    SELECT \n" +
"        a.nombreArticulo AS Producto, \n" +
"        SUM(dv.cantidad) AS Cantidad_Vendida_Anterior, \n" +
"        SUM(dv.cantidad * dv.precioVenta) AS Total_Recaudado_Anterior\n" +
"    FROM DetalleVenta dv\n" +
"    JOIN Venta v ON dv.idVenta = v.idVenta\n" +
"    JOIN Articulo a ON dv.idArticulo = a.idArticulo\n" +
"    WHERE \n" +
"        EXTRACT(MONTH FROM v.FechaVenta) = CASE \n" +
"            WHEN ?  = 1 THEN 12 ELSE ?  - 1 \n" +
"        END\n" +
"        AND EXTRACT(YEAR FROM v.FechaVenta) = CASE \n" +
"            WHEN ?  = 1 THEN ?  - 1 ELSE ? \n" +
"        END\n" +
"    GROUP BY a.nombreArticulo\n" +
")\n" +
"SELECT \n" +
"    COALESCE(vm.Producto, vma.Producto) AS Producto,\n" +
"    COALESCE(vm.Cantidad_Vendida, 0) AS Cantidad_Vendida,\n" +
"    COALESCE(vma.Cantidad_Vendida_Anterior, 0) AS Cantidad_Vendida_Anterior,\n" +
"    COALESCE(vm.Total_Recaudado, 0) AS Total_Recaudado,\n" +
"    COALESCE(vma.Total_Recaudado_Anterior, 0) AS Total_Recaudado_Anterior,\n" +
"    -- Variación de cantidad vendida\n" +
"    CASE \n" +
"        WHEN vma.Cantidad_Vendida_Anterior = 0 THEN NULL\n" +
"        ELSE ROUND(((vm.Cantidad_Vendida - vma.Cantidad_Vendida_Anterior) * 100.0) / vma.Cantidad_Vendida_Anterior, 2) \n" +
"    END AS Variacion_Cantidad,\n" +
"    -- Variación de total recaudado\n" +
"    CASE \n" +
"        WHEN vma.Total_Recaudado_Anterior = 0 THEN NULL\n" +
"        ELSE ROUND(((vm.Total_Recaudado - vma.Total_Recaudado_Anterior) * 100.0) / vma.Total_Recaudado_Anterior, 2) \n" +
"    END AS Variacion_Recaudado\n" +
"FROM ventas_mes vm\n" +
"FULL JOIN ventas_mes_anterior vma ON vm.Producto = vma.Producto\n" +
"ORDER BY Variacion_Cantidad DESC;";
             Connections con= new Connections();
            try(PreparedStatement ps= con.connect().prepareStatement(sql) ){
                ps.setInt(1, mes);
                ps.setInt(2, anio);
                ps.setInt(3, mes);
                ps.setInt(4, mes);
                ps.setInt(5, mes);
                ps.setInt(6, anio);
                ps.setInt(7, anio);
                try(ResultSet rs=ps.executeQuery()){
              
                    while(rs.next()){  

                        SalesBook libro=new SalesBook();
                        libro.setProducto(rs.getString("Producto"));
                        libro.setCantidad_vendida(rs.getInt("Cantidad_Vendida"));
                        libro.setTotal_reacudado(rs.getInt("Total_Recaudado"));
                        libro.setVariacion_cantidad(rs.getInt("Variacion_Cantidad"));
                        libro.setVariacion_recaudado(rs.getInt("Variacion_Recaudado"));
                        libro.setCantidad_vendida_anterior(rs.getInt("Cantidad_Vendida_Anterior"));
                        libro.setCantidad_recaudado_anterior(rs.getInt("Total_Recaudado_Anterior"));
                        listaVentas.add(libro);           
                        }
                    }
            }catch(SQLException e){
                        e.printStackTrace();
      }
    
            return listaVentas;
    }
}

