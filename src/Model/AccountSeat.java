//Modelo del asiento cuenta//
package Model;

public class AccountSeat {
    //Atributos//
    private int idSeat;
    private int idAccount;
    private float amount;
    private String type;
    //Contructores//

    public AccountSeat() {
    }

    public AccountSeat(int idAccount, float amount, String type) {
        this.idAccount = idAccount;
        this.amount = amount;
        this.type = type;
    }
    

    public AccountSeat( int idSeat ,int idAccount, float amount, String type) {
        this.idSeat=idSeat;
        this.idAccount = idAccount;
        this.amount = amount;
        this.type = type;
    }
    
    public int getIdSeat() {
        return idSeat;
    }

    //Setters and Getters//
    public void setIdSeat(int idSeat) {    
        this.idSeat = idSeat;
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
