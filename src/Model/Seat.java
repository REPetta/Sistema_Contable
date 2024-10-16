//Clase del Modelo de Asiento//
package Model;

import java.util.Date;

public class Seat {
    //Atributos//
    private int idSeat;
    private int operationNumber;
    private Date date;
    private String descriptionSeat;
    private int idUser;
    //Contructores//
    public Seat(int idSeat, int operationNumber, Date date, String descriptionSeat, int idUser) {
        this.idSeat = idSeat;
        this.operationNumber = operationNumber;
        this.date = date;
        this.descriptionSeat = descriptionSeat;
        this.idUser = idUser;
    }

    public Seat(int operationNumber, Date date, String descriptionSeat, int idUser) {
        this.operationNumber = operationNumber;
        this.date = date;
        this.descriptionSeat = descriptionSeat;
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

    public int getOperationNumber() {
        return operationNumber;
    }

    public void setOperationNumber(int operationNumber) {
        this.operationNumber = operationNumber;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDescriptionSeat() {
        return descriptionSeat;
    }

    public void setDescriptionSeat(String descriptionSeat) {
        this.descriptionSeat = descriptionSeat;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }
    
    
    
}
