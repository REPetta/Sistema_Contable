//Clase encargada de la conexion con la base de datos con los datos relacionados a las cuentas//
package Connection;

import Model.Account;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AccountConnection {
    
    //Metodo para obtener las cuentas validas de la base de datos//
    public List<Account> getAccounts() throws SQLException{
        List<Account> accounts= new ArrayList<>();
        String sql ="SELECT * FROM  Cuenta WHERE estado='alta' ORDER BY codigo;";
        Connections con= new Connections();
            try(PreparedStatement ps= con.connect().prepareStatement(sql) ){
                  try(ResultSet rs=ps.executeQuery()){
                      while(rs.next()){
                          Account account =new Account( 
                                  rs.getString("nombreCuenta"),
                                  rs.getInt("codigo"),
                                  rs.getString("tipo"),
                                  rs.getDouble("saldoCuenta"),
                                  rs.getInt("recibeSaldo"),
                                  rs.getString("estado")
                          );
                          accounts.add(account);
                      }
                  }catch(SQLException e){
                      e.printStackTrace();
                  }
                  return accounts;
            }
    }
}
