//Clase encargada del comportamiento de los asientos//
//No funciona//
package Controller;

import ConnectionsBD.AccountSeatConnection;
import Model.AccountSeat;
import Model.Seat;
import Model.User;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class SeatController {
    //Atributos//
    private AccountSeatConnection seatCon;
    private AccountSeatConnection accountSeatCon;
    private static SeatController  seats;
    //Constructor//
    public  SeatController(){
        this.seatCon=new AccountSeatConnection();
        this.accountSeatCon=new AccountSeatConnection();
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
        seatCon=new AccountSeatConnection();
        ArrayList<Seat> seats=seatCon.getSeats();
        for(Seat seat : seats){
            System.out.println("Fecha: " + seat.getDate());
            //Luego obtenemos las cuentas asociadas a cada asiento//
            ArrayList<AccountSeat> accounts=accountSeatCon.getAccountsSeats(seat.getIdSeat());
            for (AccountSeat account:accounts){
                System.out.println("\tCuenta: "+ chartAccounts.getAccountName(account));
                System.out.println("\tTipo: "+ account.getType());
                System.out.println("\tMonto: "+account.getAmount());
                
            }
            System.out.println("------------------------------------------------------------------");
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    
     //Metodo para obtener la Instancia Unica//
    public static SeatController getInstancia(){
        if(seats==null){
            seats=new SeatController();
        }
        return seats;
    }
    //
    //Metodo para establecer el usuario
    public void setSeatsController(SeatController seats){
        this.seats=seats;
    }
    //
    //Metodo para obtener el usuario
    public SeatController getSeatController(){
        return this.seats;
    }
    
 }
