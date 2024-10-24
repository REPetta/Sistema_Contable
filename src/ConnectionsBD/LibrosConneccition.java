//Encargaga de la coneccion con la base de datos//
package ConnectionsBD;

import Controller.AccountSeatController;
import Model.AccountSeat;
import Model.Seat;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;


public class LibrosConneccition {
   
    //Metodo par obtener todos los asientos entre dos fechas dadas//
    public ArrayList<AccountSeatController> obtenerListaAsientos(Date fechaMenor , Date fechaMayor) throws ClassNotFoundException, SQLException, IOException{
        ArrayList<AccountSeatController> listaAsientos=new ArrayList<AccountSeatController>();
        String sql= "SELECT a.idAsiento, a.fecha , ac.idAsiento , ac.idCuenta, ac.tipo, ac.monto, ac.descripcionOperacion FROM Asiento a INNER JOIN Asiento_Cuenta ac ON a.idAsiento=ac.idAsiento WHERE a.fecha BETWEEN ? AND ? ORDER BY a.fecha ASC;";
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
                            
                            currentAsiento = new AccountSeatController();
                            currentAsiento.setSeat(seat);
                            currentAsiento.setAccountSeats(new ArrayList<>());
                            // Agregar el nuevo AccountSeatController a la lista
                            listaAsientos.add(currentAsiento);
                            // Actualizar el idSeat actual
                            lastIdSeat = idSeat;
                        }
                    // Si hay datos de Asiento_Cuenta, agregarlos a la lista del asiento actual
                        if(rs.getInt("ac.idAsiento")!=0){
                            AccountSeat asientoCuenta= new AccountSeat();
                            asientoCuenta.setIdSeat(rs.getInt("ac.idAsiento"));
                            asientoCuenta.setIdAccount(rs.getInt("ac.idCuenta"));
                            asientoCuenta.setType(rs.getString("ac.tipo"));
                            asientoCuenta.setAmount(rs.getFloat("ac.monto"));
                            asientoCuenta.setDecripcionOperacion(rs.getString("descripcionOperacion"));
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