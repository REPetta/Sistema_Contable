//Modelo del asiento cuenta//
package Model;

public class AccountSeat {
    //Atributos//
    private int idSeat;
    private int idAccount;
    private float amount;
    private String type;
    private String decripcionOperacion;
    //Contructores//

    public AccountSeat() {
    }

    public AccountSeat(int idAccount, float amount, String type, String decripcionOperacion) {
        this.idAccount = idAccount;
        this.amount = amount;
        this.type = type;
        this.decripcionOperacion = decripcionOperacion;
    }
    

    public AccountSeat( int idSeat ,int idAccount, float amount, String type,String descripcionOperacion) {
        this.idSeat=idSeat;
        this.idAccount = idAccount;
        this.amount = amount;
        this.type = type;
        this.decripcionOperacion=descripcionOperacion;
    }
    
    public int getIdSeat() {
        return idSeat;
    }

    //Setters and Getters//
    public void setIdSeat(int idSeat) {    
        this.idSeat = idSeat;
    }

    public String getDecripcionOperacion() {
        return decripcionOperacion;
    }

    public void setDecripcionOperacion(String decripcionOperacion) {
        this.decripcionOperacion = decripcionOperacion;
    }

    

    public int getIdAccount() {
        return idAccount;
    }

    public void setIdAccount(int idAccount) {
        this.idAccount = idAccount;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


}
