//Clase Main//
package SistemaContable;

import ConnectionsBD.ChartAccountsConnection;
import Controller.ChartAccountsController;
import Controller.LoginController;
import Model.Account;
import Model.AccountNode;
import java.io.IOException;
import java.sql.SQLException;

public class Sistema_Contable {

    public static void main(String[] args) throws SQLException, ClassNotFoundException, IOException {
        //Esta parte para probar el sistema contable
        LoginController loginController=new LoginController();
        loginController.openViewLogin();//Hace visible la interfaz grafica del login
        
        //Esta parte para probar si el plan de cuenta se crea correctamente con las cuentas de la base de datos
        /*ChartAccountsController chartAccounts=new ChartAccountsController();
        ChartAccountsConnection chartAccountConnection= new ChartAccountsConnection();
        chartAccounts=chartAccountConnection.createChartAccounts();
        chartAccounts.printChartAccounts();
        */
        
         /*//Esta parte para probar el plan de cuentas con datos locales
        ChartAccountsController chartAccounts=new ChartAccountsController();
        AccountNode caja=new AccountNode(new Account("CAJA",210,"Caja",0,0));
        chartAccounts.addAccount(chartAccounts.getRoot(),caja);
        AccountNode cajaChica= new AccountNode(new Account("CAJA CHICA",211,"Caja chica",0,1));
        chartAccounts.addAccount(chartAccounts.getRoot(),cajaChica);//Error aqui no es capaz de guardar correctamente en la subcuenta de la cuenta
        chartAccounts.printChartAccounts();
        */
         /* //Esta parte para probar si agregar correctamente la cuenta a la base de datos
        Account account=new Account("Patrinomio_Neto2", 320 ,"PATRIMONIO NETO", 3000,1);
        ChartAccountsConnection chartAccountConnection= new ChartAccountsConnection();
        chartAccountConnection.addAccount(account);
        */
    }
    
}
