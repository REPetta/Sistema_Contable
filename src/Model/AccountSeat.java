//Modelo del asiento cuenta//
package Model;

public class AccountSeat {
    //Atributos//
    private int idSeat;
    private int idAccount;
    private float amount;
    private String type;
    private float balance;
    //Contructores//

    public AccountSeat() {
    }

    public AccountSeat(int idSeat, int idAccount, float amount, String type, float balance) {
        this.idSeat = idSeat;
        this.idAccount = idAccount;
        this.amount = amount;
        this.type = type;
        this.balance = balance;
    }
    
    //Setters and Getters//

    public int getIdSeat() {
        return idSeat;
    }

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

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

}
