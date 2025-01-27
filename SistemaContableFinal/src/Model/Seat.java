/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.util.Date;

/**
 *
 * @author Rodrigo
 */
public class Seat {
    
    private int idUsuario;
    private int idSeat;
    private Date seatDate;
    private String seatDescrip;

    public Seat(int idUsuario, int idSeat, Date seatDate, String seatDescrip) {
        this.idUsuario = idUsuario;
        this.idSeat = idSeat;
        this.seatDate = seatDate;
        this.seatDescrip = seatDescrip;
    }

    public Seat() {
    }

    public Seat(int idUsuario, Date seatDate, String seatDescrip) {
        this.idUsuario = idUsuario;
        this.seatDate = seatDate;
        this.seatDescrip = seatDescrip;
    }
    
    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdSeat() {
        return idSeat;
    }

    public void setIdSeat(int idSeat) {
        this.idSeat = idSeat;
    }

    public Date getSeatDate() {
        return seatDate;
    }

    public void setSeatDate(Date seatDate) {
        this.seatDate = seatDate;
    }

    public String getSeatDescrip() {
        return seatDescrip;
    }

    public void setSeatDescrip(String seatDescrip) {
        this.seatDescrip = seatDescrip;
    }
    
}
