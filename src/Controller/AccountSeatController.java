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

    public Seat getSeat() {
        return seat;
    }

    public void setSeat(Seat seat) {
        this.seat = seat;
    }

    public ArrayList<AccountSeat> getAccountSeats() {
        return accountSeats;
    }

    public void setAccountSeats(ArrayList<AccountSeat> accountSeats) {
        this.accountSeats = accountSeats;
    }
    
    public void agregarAsientoContable(AccountSeat accountSeat){
            accountSeats.add(accountSeat);
    }
    public void eliminarAsientoContable(AccountSeat accountSeat){
        accountSeats.remove(accountSeat);
    }
    
}
