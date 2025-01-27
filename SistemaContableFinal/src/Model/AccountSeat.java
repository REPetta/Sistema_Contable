//Clase encargada del modelo del asiento contable//
package Model;

import java.util.Date;


public class AccountSeat {
    
    private int idSeat;
    private String destiny;
    private String account;
    private double amount;
    private int idCuenta;
    private double saldoPostOperacion;

    public AccountSeat(int idSeat, String destiny, String account, double amount, int idCuenta, double saldoPostOperacion) {
        this.idSeat=idSeat;
        this.destiny = destiny;
        this.account = account;
        this.amount = amount;
        this.idCuenta = idCuenta;
        this.saldoPostOperacion=saldoPostOperacion;
    }

    public AccountSeat() {
    }

    public AccountSeat(int idSeat, String destiny, int idCuenta,double amount, double saldoPostOperacion) {
        this.idSeat = idSeat;
        this.destiny = destiny;
        this.idCuenta = idCuenta;
        this.amount=amount;
        this.saldoPostOperacion = saldoPostOperacion;
    }
   
    public int getIdSeat() {
        return idSeat;
    }

    public void setIdSeat(int idSeat) {
        this.idSeat = idSeat;
    }

    public double getSaldoPostOperacion() {
        return saldoPostOperacion;
    }

    public void setSaldoPostOperacion(double saldoPostOperacion) {
        this.saldoPostOperacion = saldoPostOperacion;
    }

   

    public String getDestiny() {
        return destiny;
    }

    public void setDestiny(String destiny) {
        this.destiny = destiny;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getIdCuenta() {
        return idCuenta;
    }

    public void setIdCuenta(int idCuenta) {
        this.idCuenta = idCuenta;
    }
    
    
    
}
