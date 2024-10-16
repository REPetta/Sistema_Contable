//Clase encargada del comportamiento de los asientos//
//No funciona//
package Controller;

import ConnectionsBD.SeatConnection;
import Model.AccountSeat;
import Model.Seat;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class SeatController {
    //Atributos//
    private SeatConnection seatCon;
    private SeatConnection accountSeatCon;
    //Constructor//
    public  SeatController(){
        this.seatCon=new SeatConnection();
        this.accountSeatCon=new SeatConnection();
    }
   //Metodo para agregar un asiento//
    public void addSeat(Seat seat, ArrayList<AccountSeat> accounts ) throws ClassNotFoundException, SQLException, IOException{
        try{
            //Primero insertamos el asiento//
            seatCon.addSeat(seat);
            //Luego insertamos las cuentas asociadas al asiento//
            for(AccountSeat account:accounts){
                accountSeatCon.addAccountSeat(account);
            }
        }catch(SQLException e){
            e.printStackTrace();
        } 
    }
    //Metodo para obtener todos los asientos//
    public ArrayList<Seat> getAllSeats() throws IOException, ClassNotFoundException{
        try{
            return seatCon.getSeats();
        }catch(SQLException e){
            e.printStackTrace();
            return null;
        }
    }
    //Metodo para obtener todas los asientos cuenta//
    public ArrayList<AccountSeat> getAllAccountSeats(int idSeat) throws IOException, ClassNotFoundException{
        try{
            return accountSeatCon.getAccountsSeats(idSeat);
        }catch(SQLException e){
            e.printStackTrace();
            return null;
        }
    }
    //Metodo para imprimir los asientos-cuenta//
    public void printSeats(ChartAccountsController chartAccounts) throws IOException, ClassNotFoundException, SQLException{
        try{
        //Primero obtenemos los asientos//
        seatCon=new SeatConnection();
        ArrayList<Seat> seats=seatCon.getSeats();
        for(Seat seat : seats){
            System.out.println("Numero de Operacion: " + seat.getOperationNumber());
            System.out.println("Fecha: " + seat.getDate());
            //Luego obtenemos las cuentas asociadas a cada asiento//
            ArrayList<AccountSeat> accounts=accountSeatCon.getAccountsSeats(seat.getIdSeat());
            for (AccountSeat account:accounts){
                System.out.println("\tCuenta: "+ chartAccounts.getAccountName(account));
                System.out.println("\tTipo: "+ account.getType());
                System.out.println("\tMonto: "+account.getAmount());
                System.out.println("\tSaldo: "+account.getBalance());
            }
            System.out.println("------------------------------------------------------------------");
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    
 }
