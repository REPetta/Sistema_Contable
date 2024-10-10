//Clase que se encarga de los datos de las cuentas//
package Model;

public class Account {
    //Atributos//
    private String accountName;
    private int accountCode;
    private String accountType;
    private float accountBalance;
    private int receiveBalance;
    //Contructor//
    public Account(){
}
    public Account(String accountName,int accountCode, String accountType, float accountBalance, int receiveBalance){
        this.accountName=accountName.toUpperCase();
        this.accountCode=accountCode;
        this.accountType=accountType.toUpperCase();
        this.accountBalance=accountBalance;
        this.receiveBalance=receiveBalance;
    }
    //Setters And Getters//

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public int getAccountCode() {
        return accountCode;
    }

    public void setAccountCode(int accountCode) {
        this.accountCode = accountCode;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public float getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(float accountBalance) {
        this.accountBalance = accountBalance;
    }

    public int getReceiveBalance() {
        return receiveBalance;
    }

    public void setReceiveBalance(int receiveBalance) {
        this.receiveBalance = receiveBalance;
    }
    
}
