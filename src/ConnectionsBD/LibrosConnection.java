//Encargaga de la coneccion con la base de datos//
package ConnectionsBD;

import Controller.AccountSeatController;
import Model.Account;
import Model.AccountSeat;
import Model.Seat;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;


public class LibrosConnection {
   
    //Metodo par obtener todos los asientos entre dos fechas dadas//
    public ArrayList<AccountSeatController> obtenerListaAsientos(Date fechaMenor , Date fechaMayor) throws ClassNotFoundException, SQLException, IOException{
        ArrayList<AccountSeatController> listaAsientos=new ArrayList<AccountSeatController>();
        String sql= "SELECT a.idAsiento, a.fecha , ac.idAsientoCuenta , ac.idCuenta, ac.tipo, ac.monto, a.descripcionAsiento FROM Asiento a INNER JOIN Asiento_Cuenta ac ON a.idAsiento=ac.idAsiento WHERE a.fecha BETWEEN ? AND  ?  ORDER BY a.fecha ASC,ac.idAsientoCuenta ASC ;";
        ResultSet rs=null;//Variable para almacenar el resultado de la consulta//
        PreparedStatement ps=null;//Variable para preparar y ejecutar la consulta //
        ConnectionsBD.ConnectionBD objConect = new ConnectionsBD.ConnectionBD();//Crea una instancia de ConnectionBD//
        AccountSeatController currentAsiento = null;
        int lastIdSeat = -1;
          try{
            ps=objConect.conect().prepareStatement(sql);//Establece la conexion con la base de datos//
            ps.setDate(1, new java.sql.Date(fechaMenor.getTime() ));
            ps.setDate(2, new java.sql.Date(fechaMayor.getTime()));
            rs=ps.executeQuery(); //Ejecuta la consulta y almacena el resultado//
            
            while(rs.next()){
                int idSeat= rs.getInt("idAsiento");
                        if(idSeat!=lastIdSeat){
                            // Si estamos en un nuevo asiento, crear uno nuevo y agregarlo a la lista
                            Seat seat= new Seat();
                            seat.setIdSeat(rs.getInt("idAsiento"));
                            seat.setDate(rs.getDate("fecha"));
                            seat.setDescripcion(rs.getString("descripcionAsiento"));
                            currentAsiento = new AccountSeatController();
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
                            asientoCuenta.setIdAccount(rs.getInt("idCuenta"));
                            asientoCuenta.setType(rs.getString("tipo"));
                            asientoCuenta.setAmount(rs.getFloat("monto"));
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
                if (objConect != null) try { objConect.close(); } catch (SQLException e) {}
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
        ConnectionsBD.ConnectionBD objConect = new ConnectionsBD.ConnectionBD();//Crea una instancia de ConnectionBD//
          try{
            ps=objConect.conect().prepareStatement(sql);//Establece la conexion con la base de datos//
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
                if (objConect != null) try { objConect.close(); } catch (SQLException e) {}
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
        ConnectionsBD.ConnectionBD objConect = new ConnectionsBD.ConnectionBD();//Crea una instancia de ConnectionBD//
          try{
            ps=objConect.conect().prepareStatement(sql);//Establece la conexion con la base de datos//
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
                if (objConect != null) try { objConect.close(); } catch (SQLException e) {}
    }     
}
    
      //Metodo par obtener todos los asientos entre dos fechas dadas//
    public ArrayList<AccountSeatController> obtenerListaFinal(Date fechaMayor ) throws ClassNotFoundException, SQLException, IOException{
        ArrayList<AccountSeatController> listaAsientos=new ArrayList<AccountSeatController>();
        String sql= "SELECT a.idAsiento, a.fecha , ac.idAsientoCuenta , ac.idCuenta, ac.tipo, ac.monto, a.descripcionAsiento FROM Asiento a INNER JOIN Asiento_Cuenta ac ON a.idAsiento=ac.idAsiento WHERE a.fecha <=? ORDER BY a.fecha ASC,ac.idAsientoCuenta ASC ;";
        ResultSet rs=null;//Variable para almacenar el resultado de la consulta//
        PreparedStatement ps=null;//Variable para preparar y ejecutar la consulta //
        ConnectionsBD.ConnectionBD objConect = new ConnectionsBD.ConnectionBD();//Crea una instancia de ConnectionBD//
        AccountSeatController currentAsiento = null;
        int lastIdSeat = -1;
          try{
            ps=objConect.conect().prepareStatement(sql);//Establece la conexion con la base de datos//
            ps.setDate(1, new java.sql.Date(fechaMayor.getTime() ));
            rs=ps.executeQuery(); //Ejecuta la consulta y almacena el resultado//
            
            while(rs.next()){
                int idSeat= rs.getInt("idAsiento");
                        if(idSeat!=lastIdSeat){
                            // Si estamos en un nuevo asiento, crear uno nuevo y agregarlo a la lista
                            Seat seat= new Seat();
                            seat.setIdSeat(rs.getInt("idAsiento"));
                            seat.setDate(rs.getDate("fecha"));
                            seat.setDescripcion(rs.getString("descripcionAsiento"));
                            
                            currentAsiento = new AccountSeatController();
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
                            asientoCuenta.setIdAccount(rs.getInt("idCuenta"));
                            asientoCuenta.setType(rs.getString("tipo"));
                            asientoCuenta.setAmount(rs.getFloat("monto"));
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
                if (objConect != null) try { objConect.close(); } catch (SQLException e) {}
    }     
} 
}