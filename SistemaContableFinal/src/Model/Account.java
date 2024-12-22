//Clase encargada del contener la informacion de una cuenta//
package Model;

/**
 *
 * @author Rodrigo
 */
public class Account {
    
    private String accountName;
    private int code;
    private String type;
    private double balance;
    private  int receiveBalance;
    private  String state;

   //Contructor//
    public Account( String accountName, int code, String type, double balance, int receiveBalance, String state) {
        this.accountName = accountName;
        this.code = code;
        this.type = type;
        this.balance = balance;
        this.receiveBalance = receiveBalance;
        this.state=state;
    }

    public Account( String accountName, int code, String type, double balance, int receiveBalance) {
        this.accountName = accountName;
        this.code = code;
        this.type = type;
        this.balance = balance;
        this.receiveBalance = receiveBalance;
    }
    
    

    //Setters and Getters//
    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public int getReceiveBalance() {
        return receiveBalance;
    }

    public void setReceiveBalance(int receiveBalance) {
        this.receiveBalance = receiveBalance;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    
    
    
}
