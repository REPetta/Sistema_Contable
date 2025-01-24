//Clase encargada de la conexion con la base de datos con los datos relacionados a las cuentas//
package Connection;

import Model.Account;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class AccountConnection {
    
    //Metodo para obtener los datos de una cuenta//
    public Account getAccountDates(int code) throws SQLException{
        Account account= new Account();
        String sql="SELECT * FROM Cuenta WHERE estado='alta' AND codigo=?;";
        Connections con= new Connections();
         try(PreparedStatement ps= con.connect().prepareStatement(sql) ){
                  ps.setInt(1, code);
                  try(ResultSet rs=ps.executeQuery()){
                        account.setAccountName(rs.getString("nombreCuenta"));
                        account.setCode(rs.getInt("codigo"));
                        account.setType(rs.getString("tipo"));
                        account.setBalance(rs.getDouble("saldoCuenta"));                
            }catch(SQLException e){
                      e.printStackTrace();
            }
                  return account;
         }        
    }
    //Metodo para obtener las cuentas validas de la base de datos//    //Metodo para obtener las cuentas validas de la base de datos//
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
    //Metodo para cargar una cuenta//
    public void loadAccount(Account account) throws SQLException{
      String sql="INSERT INTO Cuenta(nombrecuenta,codigo,tipo,estado,saldocuenta,recibesaldo) VALUES(?,?,?,?,?,?) ON CONFLICT(codigo) DO NOTHING;";
      Connections con= new Connections(); 
        try(PreparedStatement ps= con.connect().prepareStatement(sql)){
                ps.setString(1,account.getAccountName());
                ps.setInt(2,account.getCode());
                ps.setString(3, account.getType().toUpperCase());
                ps.setString(4, "alta");
                ps.setDouble(5, account.getBalance());
                ps.setFloat(6, account.getReceiveBalance());
                 
                int affectedRows=ps.executeUpdate();//Ejecuta la secuencia e inserta la cuenta en la tabla de la base de datos//
                if(affectedRows==0){
                        JOptionPane.showMessageDialog(null, "La cuenta con código " + account.getCode() + " ya existe.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                 }
                else{
                JOptionPane.showMessageDialog(null, "La cuenta ha sido agregada correctamente","Confirmacion",JOptionPane.INFORMATION_MESSAGE);
                }
             }catch(SQLException e){
                      e.printStackTrace();
           }
        }
    //Metodo dar de baja una cuenta//
    public boolean cancelAccount(int code) throws SQLException{
         String sql="UPDATE cuenta\n" +
                            "SET estado = 'baja'\n" +
                            "WHERE codigo = ?\n" +
                            "AND NOT EXISTS (\n" +
                            "SELECT 1\n" +
                            "FROM asiento_cuenta\n" +
                            "WHERE asiento_cuenta.idCuenta = cuenta.idCuenta\n" +
                            "  );";
         Connections con= new Connections();
         try(PreparedStatement ps= con.connect().prepareStatement(sql)){
             ps.setInt(1, code);
             int affectedRows=ps.executeUpdate();//Ejecuta la secuencia e inserta la cuenta en la tabla de la base de datos//
                if(affectedRows!=0){
                  return true;
                }
             }catch(SQLException e){
                      e.printStackTrace();
           }
        return false;
        }
    
    //Metodo para editar  una cuenta//
    public boolean editAccount(int code, String newName) throws SQLException{
         String sql="UPDATE cuenta\n" +
                          "SET nombreCuenta = ?\n" +
                          "WHERE codigo = ? \n" +
                            "AND NOT EXISTS ( \n" +
                            "SELECT 1\n" +
                            "FROM cuenta\n" +
                            "WHERE nombreCuenta = ?);";
         Connections con= new Connections();
         try(PreparedStatement ps= con.connect().prepareStatement(sql)){
             ps.setString(1, newName);
             ps.setInt(2, code);
             ps.setString(3, newName);
             int affectedRows=ps.executeUpdate();//Ejecuta la secuencia e inserta la cuenta en la tabla de la base de datos//
             if(affectedRows!=0){
                return true;    
                }
             }catch(SQLException e){
                      e.printStackTrace();
           }
        return false;
        }
    
}
    
    

    
