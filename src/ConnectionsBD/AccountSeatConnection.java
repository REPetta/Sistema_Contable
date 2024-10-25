//Clase encarga de la conexion con la base de datos de los asientos//
//No funciona//
package ConnectionsBD;

import Model.Account;
import Model.AccountSeat;
import Model.Seat;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AccountSeatConnection {
    
    //Metodo para agregar un asiento a la base de datos//
    public int addSeat(Seat seat) throws ClassNotFoundException, SQLException, IOException{
        String sql="INSERT INTO asiento(fecha,idusuario) VALUES(?,?) RETURNING idAsiento;";
        ConnectionsBD.ConnectionBD objConect = new ConnectionsBD.ConnectionBD();//Crea una instancia de ConnectionBD//
        PreparedStatement ps=null;//Variable para preparar y ejecutar la consulta //
        ResultSet rs=null;//Variable para almacenar el resultado de la consulta//
        int generatedId=-1;
        try{
            ps=objConect.conect().prepareStatement(sql);
            ps.setDate(1, new java.sql.Date(seat.getDate().getTime()));
            ps.setInt(2, seat.getIdUser());
            rs=ps.executeQuery();
            if(rs.next()){
                generatedId=rs.getInt("idAsiento");
            }
        }catch(ClassNotFoundException | SQLException e){
            e.printStackTrace();
            throw e;
        }finally{
            if (ps != null) try { ps.close(); } catch (SQLException e) { e.printStackTrace(); }
            if (objConect != null) try { objConect.close(); } catch (SQLException e) { e.printStackTrace(); }
            }
        return generatedId;
        }
        //Metodo para ingregresar en la tabla asiento_cuenta//
    public void addAccountSeat(AccountSeat accountSeat) throws ClassNotFoundException, SQLException, IOException{
        //Atributos//
        String sql="INSERT  INTO asiento_cuenta (idasiento,idcuenta,tipo,monto,descripcionoperacion) VALUES(?,?,?,?,?);";
        ConnectionsBD.ConnectionBD objConect = new ConnectionsBD.ConnectionBD();//Crea una instancia de ConnectionBD//
        PreparedStatement ps=null;//Variable para preparar y ejecutar la consulta //
        try{
            ps=objConect.conect().prepareStatement(sql);
            ps.setInt(1, accountSeat.getIdSeat());
            ps.setInt(2, accountSeat.getIdAccount());
            ps.setString(3, accountSeat.getType());
            ps.setFloat(4, accountSeat.getAmount());
            ps.setString(5, accountSeat.getDecripcionOperacion());
            ps.executeUpdate();
        
        }catch(ClassNotFoundException | SQLException e){
            e.printStackTrace();
            throw e;
        }finally{
            if (ps != null) try { ps.close(); } catch (SQLException e) { e.printStackTrace(); }
            if (objConect != null) try { objConect.close(); } catch (SQLException e) { e.printStackTrace(); }
            }
        }
    //Metodo para obtener todos los asientos//
    public ArrayList<Seat> getSeats() throws IOException, ClassNotFoundException, SQLException{
        //Atributos//
        ArrayList<Seat> seats= new ArrayList<>();
        String sql="SELECT idasiento, numerooperacion, fecha, descripcionasiento, idusuario FROM Asiento ;";
        ResultSet rs=null;//Variable para almacenar el resultado de la consulta//
        PreparedStatement ps=null;//Variable para preparar y ejecutar la consulta //
        ConnectionsBD.ConnectionBD objConect = new ConnectionsBD.ConnectionBD();//Crea una instancia de ConnectionBD//
        try{
            ps=objConect.conect().prepareStatement(sql);//Establece la conexion con la base de datos//
            rs=ps.executeQuery();
            while(rs.next()){
                Seat seat= new Seat(
                        rs.getInt("idasiento"),
                        rs.getDate("fecha"),
                        rs.getInt("idusuario")
                );
                seats.add(seat);
            }
            return seats;    
        }catch(SQLException e){
            e.printStackTrace();
            throw e;
        }finally{
                if(rs!=null) try{rs.close(); }catch(SQLException e){e.printStackTrace(); }
                if (ps != null) try { ps.close(); } catch (SQLException e) { e.printStackTrace(); }
                 if (objConect != null) try { objConect.close(); } catch (SQLException e) { e.printStackTrace(); }
    }
          
    }
    //Metodo para obtener las cuentas asociadas a un asiento//
    public ArrayList<AccountSeat> getAccountsSeats(int idseat) throws IOException, SQLException, ClassNotFoundException{
        //Atributos//
        ArrayList<AccountSeat> accounts= new ArrayList<>();
        String sql="SELECT* FROM asiento_cuenta WHERE idasiento=?;";
        ResultSet rs=null;//Variable para almacenar el resultado de la consulta//
        PreparedStatement ps=null;//Variable para preparar y ejecutar la consulta //
        ConnectionsBD.ConnectionBD objConect = new ConnectionsBD.ConnectionBD();//Crea una instancia de ConnectionBD//
        try{
            ps=objConect.conect().prepareStatement(sql);//Establece la conexion con la base de datos//
            ps.setInt(1, idseat);
            rs=ps.executeQuery();
            while(rs.next()){
                 AccountSeat accountSeat= new AccountSeat(
                        rs.getInt("idasiento"),
                        rs.getInt("idcuenta"),
                        rs.getFloat("monto"),
                        rs.getString("tipo"),
                        rs.getString("descripcionoperacion")
                );
                accounts.add(accountSeat);
            }
        }catch(SQLException e){
            e.printStackTrace();
            throw e;
        }finally{
                if(rs!=null) try{rs.close(); }catch(SQLException e){e.printStackTrace(); }
                if (ps != null) try { ps.close(); } catch (SQLException e) { e.printStackTrace(); }
                if (objConect != null) try { objConect.close(); } catch (SQLException e) {}
    }
        return accounts;      
    }
        // metodo para traer las cuentas// 
        
     public ArrayList<Account> getAccount() throws IOException, SQLException, ClassNotFoundException{
        //Atributos//
        ArrayList<Account> accounts= new ArrayList<>();
        Account accountNula=new Account();
        accounts.add(accountNula);
        String sql="SELECT* FROM cuenta c WHERE c.estado='alta' and c.recibesaldo= 1 ;";
        ResultSet rs=null;//Variable para almacenar el resultado de la consulta//
        PreparedStatement ps=null;//Variable para preparar y ejecutar la consulta //
        ConnectionsBD.ConnectionBD objConect = new ConnectionsBD.ConnectionBD();//Crea una instancia de ConnectionBD//
        try{
            ps=objConect.conect().prepareStatement(sql);//Establece la conexion con la base de datos//
         
            rs=ps.executeQuery();
            while(rs.next()){
                 Account account= new Account(
                        rs.getInt("idCuenta"),
                        rs.getString("nombreCuenta"),
                        rs.getInt("codigo"),
                        rs.getString("tipo"),
                        rs.getString("estado"),
                        rs.getFloat("saldoInicial"),
                        rs.getFloat("saldoCuenta"),
                        rs.getInt("recibeSaldo")
                );
                accounts.add(account);
            }
        }catch(SQLException e){
            
            throw e;
        }finally{
                
    }
        return accounts; 
   
    }
    
    
    public int obtenerIdCuentaPorNombre(String nombreCuenta) {
    int idCuenta = 0;

    String sql = "SELECT c.idcuenta FROM cuenta c WHERE c.nombrecuenta = ?;";
    ConnectionsBD.ConnectionBD objConect = new ConnectionsBD.ConnectionBD();

    try (PreparedStatement ps = objConect.conect().prepareStatement(sql)) {
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
