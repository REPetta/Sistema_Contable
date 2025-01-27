
package Connection;


import Model.AccountSeat;
import Model.Seat;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Rodrigo
 */
public class AccountSeatConnection {
    
    //Metodo para obtener el d
    //Metodo para agregar un asiento a la base de datos//
    public int addSeat(Seat seat) throws ClassNotFoundException, SQLException, IOException{
        String sql="INSERT INTO asiento(fecha,descripcionAsiento,idusuario) VALUES(?,?,?) RETURNING idAsiento;";   
        int generatedId=-1;
         Connections con= new Connections();
         try(PreparedStatement ps= con.connect().prepareStatement(sql) ){
              java.sql.Date sqlDate = new java.sql.Date(seat.getSeatDate().getTime());
             ps.setDate(1, sqlDate);
             ps.setString(2,seat.getSeatDescrip());
             ps.setInt(3, seat.getIdUsuario());
            try(ResultSet rs=ps.executeQuery()){
                if(rs.next()){
                    generatedId=rs.getInt("idAsiento");
                }
                    }catch(SQLException e){
                        e.printStackTrace();
                    }
        
        }
        return generatedId;
    }
    //Metodo para ingregresar en la tabla asiento_cuenta//
    public void addAccountSeat(AccountSeat accountSeat) throws ClassNotFoundException, SQLException, IOException{
        //Atributos//
        String sql="INSERT  INTO asiento_cuenta (idasiento,idcuenta,tipo,monto,saldoPostOperacion) VALUES(?,?,?,?,?);";
        Connections con= new Connections();
            try(PreparedStatement ps= con.connect().prepareStatement(sql) ){
                ps.setInt(1, accountSeat.getIdSeat());
                ps.setInt(2, accountSeat.getIdCuenta());
                ps.setString(3,accountSeat.getDestiny());
                ps.setDouble(4, accountSeat.getAmount());
                ps.setDouble(5, accountSeat.getSaldoPostOperacion());
                ps.executeUpdate();
            }catch(SQLException e){
                        e.printStackTrace();
                    }
    }
     //Metodo para obtener el id de una cuenta//
    public int obtenerIdCuentaPorNombre(String nombreCuenta) {
    int idCuenta = 0;

    String sql = "SELECT c.idcuenta FROM cuenta c WHERE c.nombrecuenta = ?;";
    Connections con= new Connections();
    try (PreparedStatement ps = con.connect().prepareStatement(sql)) {
        ps.setString(1, nombreCuenta);
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                idCuenta = rs.getInt("idcuenta");
            }
        }
    } catch (Exception e) {
        System.out.println("Error al obtener id cuenta: " + e.getMessage());
    }

    return idCuenta;
}
}
