//Clase del Modelo de Asiento//
package Model;

import java.util.Date;

public class Seat {
    //Atributos//
    private int idSeat;
    private Date date;
    private int idUser;
    //Contructores//
    public Seat(int idSeat, Date date,  int idUser) {
        this.idSeat = idSeat;
        this.date = date;
        this.idUser = idUser;
    }

    public Seat(Date date) {
        this.date = date;
    }
    

    public Seat(Date date,  int idUser) {
        this.date = date;
        this.idUser = idUser;
    }
    

    public Seat() {
    }
    //Getters and Setters//
    public int getIdSeat() {
        return idSeat;
    }

    public void setIdSeat(int idSeat) {
        this.idSeat = idSeat;
    }


    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }



    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }
    
    
    
}
