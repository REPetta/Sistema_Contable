//Clase Main//
package SistemaContable;

import ConnectionsBD.ChartAccountsConnection;
import Controller.ChartAccountsController;
import Controller.LoginController;
import Controller.SeatController;
import Model.Account;
import Model.AccountNode;
import Model.AccountSeat;
import Model.Seat;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;


public class Sistema_Contable {

    public static void main(String[] args) throws SQLException, ClassNotFoundException, IOException {
        
        /*//Agregar asiento//
        SeatController seatController = new SeatController();
        ChartAccountsController chartsAccounts= new ChartAccountsController();
        ChartAccountsConnection chartAccountConnection= new ChartAccountsConnection();
        chartsAccounts=chartAccountConnection.createChartAccounts();
        Date fechaActual=new Date();
        Seat seat= new Seat(2,2,fechaActual,"Pago de Deudas",1);
        ArrayList<AccountSeat> accounts= new ArrayList<AccountSeat>();
        AccountSeat account1=new AccountSeat(2,2,50000,"DEBE",50000);
        AccountSeat account2=new AccountSeat(2,6,50000,"HABER",50000);
        accounts.add(account1);
        accounts.add(account2);
        seatController.addSeat(seat, accounts);
        */
      //Imprimir los asientos contable//
       /* SeatController seatController = new SeatController();
        ChartAccountsController chartsAccounts= new ChartAccountsController();
        ChartAccountsConnection chartAccountConnection= new ChartAccountsConnection();
        chartsAccounts=chartAccountConnection.createChartAccounts();
        seatController.printSeats(chartsAccounts);
       */
        /*//Esta parte para probar el sistema contable
        LoginController loginController=new LoginController();
        loginController.openViewLogin();//Hace visible la interfaz grafica del login
        */
       /* //Esta parte para probar si el plan de cuenta se crea correctamente con las cuentas de la base de datos
        ChartAccountsController chartAccounts=new ChartAccountsController();
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
