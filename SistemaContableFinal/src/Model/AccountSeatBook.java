
package Model;

import java.util.ArrayList;


public class AccountSeatBook {
    
      //Atributos//
    private Seat seat;
    private ArrayList<AccountSeat> accountSeats;
    //Constructor//

    public AccountSeatBook(Seat seat, ArrayList<AccountSeat> accountSeats) {
        this.seat = seat;
        this.accountSeats = accountSeats;
    }

    public AccountSeatBook() {
    }
   //Getters and Setters//
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
    
}
