//Clase encarga de las consultas del plan de cuentas con la base de datos//
package ConnectionsBD;

import Controller.ChartAccountsController;
import Model.Account;
import Model.AccountNode;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class ChartAccountsConnection {
    
    //Metodo para crear el plan de cuentas con las cuentas de la base de datos//
    public ChartAccountsController createChartAccounts() throws SQLException, ClassNotFoundException, IOException{
        //Atributos
        ChartAccountsController chartAccounts=new ChartAccountsController();
        AccountNode auxNode;
        Account auxAccount;
        String sql="SELECT idcuenta,nombrecuenta,codigo,tipo,saldocuenta,recibesaldo FROM Cuenta WHERE estado='alta' ORDER BY codigo ASC;";
        ResultSet rs=null;//Variable para almacenar el resultado de la consulta//
        PreparedStatement ps=null;//Variable para preparar y ejecutar la consulta //
        ConnectionsBD.ConnectionBD objConect = new ConnectionsBD.ConnectionBD();//Crea una instancia de ConnectionBD//
        try{
              ps=objConect.conect().prepareStatement(sql);//Establece la conexion con la base de datos//
              rs=ps.executeQuery();//Ejecuta la consulta y almacena el resultado//
              while(rs.next()){//Next verifica si el registro de rs coincide con la consulta y si no lo hace avanza al siguiente, hasta que no haya mas filas en la tabla//
                  auxAccount=new Account();
                  auxAccount.setIdAccount(rs.getInt("idcuenta"));
                  auxAccount.setAccountName(rs.getString("nombrecuenta"));
                  auxAccount.setAccountCode(rs.getInt("codigo"));
                  auxAccount.setAccountType(rs.getString("tipo"));
                  auxAccount.setAccountBalance(rs.getFloat("saldocuenta"));
                  auxAccount.setReceiveBalance(rs.getInt("recibesaldo"));
                  auxNode= new AccountNode();
                  auxNode.setAccount(auxAccount);
                  chartAccounts.addAccount(chartAccounts.getRoot(),auxNode);           
              }
              return chartAccounts;
    }catch(ClassNotFoundException | SQLException e){
     e.printStackTrace();
     throw e;
 }finally{
     if(rs!=null) try{rs.close();}catch(SQLException e){e.printStackTrace();}
     if (ps != null) try { ps.close(); } catch (SQLException e) { e.printStackTrace(); }
     if (objConect != null) try { objConect.close(); } catch (SQLException e) { e.printStackTrace(); }
    }   
    
}
    //Metodo para agregar una cuenta a la base de datos(Incompleto)//
    public void addAccount(Account account) throws ClassNotFoundException, SQLException, IOException{
        String sql="INSERT INTO Cuenta(nombrecuenta,codigo,tipo,estado,saldocuenta,recibesaldo)\n" +
"	VALUES(?,?,?,?,?,?) ON CONFLICT(codigo) DO NOTHING;";
        ResultSet rs=null;//Variable para almacenar el resultado de la consulta//
        PreparedStatement ps=null;//Variable para preparar y ejecutar la consulta //
        ConnectionsBD.ConnectionBD objConect = new ConnectionsBD.ConnectionBD();//Crea una instancia de ConnectionBD//
        try{
            ps=objConect.conect().prepareStatement(sql);//Establece la conexion con la base de datos//
            ps.setString(1,account.getAccountName());
            ps.setInt(2,account.getAccountCode());
            ps.setString(3, account.getAccountType());
            ps.setString(4, "alta");
            ps.setFloat(5, account.getAccountBalance());
            ps.setFloat(6, account.getReceiveBalance());
            int affectedRows=ps.executeUpdate();//Ejecuta la secuencia e inserta la cuenta en la tabla de la base de datos//
            if(affectedRows==0){
                    JOptionPane.showMessageDialog(null, "La cuenta con código " + account.getAccountCode() + " ya existe.", "Advertencia", JOptionPane.WARNING_MESSAGE);
             }else{
            JOptionPane.showMessageDialog(null, "La cuenta ha sido agregada correctamente");}
        }catch(ClassNotFoundException | SQLException e){
            e.printStackTrace();
            throw e;
        }finally{
             if(rs!=null) try{rs.close();}catch(SQLException e){e.printStackTrace();}
             if (ps != null) try { ps.close(); } catch (SQLException e) { e.printStackTrace(); }
            if (objConect != null) try { objConect.close(); } catch (SQLException e) { e.printStackTrace(); }
            }
    }
}