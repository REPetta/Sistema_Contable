//Clase encargada del comportamiento de asiento contable
package Controller;

import Model.AccountSeat;
import Model.Seat;
import java.util.ArrayList;

public class AccountSeatController {
    //Atributos//
    private Seat seat;
    private ArrayList<AccountSeat> accountSeats;
    //Constructor//
    public AccountSeatController(){
        this.seat=new Seat();
        this.accountSeats= new ArrayList<AccountSeat>();
    }
    //Metodos//
    public void agregarAsientoContable(AccountSeat accountSeat){
            accountSeats.add(accountSeat);
    }
    public void eliminarAsientoContable(AccountSeat accountSeat){
        accountSeats.remove(accountSeat);
    }
    
}
