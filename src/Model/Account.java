//Clase que se encarga de los datos de las cuentas//
package Model;

public class Account {
    //Atributos//
    private int idAccount;
    private String accountName;
    private int accountCode;
    private String estado;
    private String accountType;
    private float accountBalance;
    private int receiveBalance;
    //Contructor//
    public Account(){   
}
    public Account(int idAccount, String accountName,int accountCode, String accountType, float accountBalance, int receiveBalance){
        this.idAccount=idAccount;
        this.accountName=accountName.toUpperCase();
        this.accountCode=accountCode;
        this.accountType=accountType.toUpperCase();
        this.accountBalance=accountBalance;
        this.receiveBalance=receiveBalance;
    }

    public Account(int idAccount, String accountName, int accountCode, String estado, String accountType, float accountBalance, int receiveBalance) {
        this.idAccount = idAccount;
        this.accountName = accountName;
        this.accountCode = accountCode;
        this.estado = estado;
        this.accountType = accountType;
        this.accountBalance = accountBalance;
        this.receiveBalance = receiveBalance;
    }
    
    

    public Account(String accountName, int accountCode, String accountType, float accountBalance) {
        this.accountName = accountName;
        this.accountCode = accountCode;
        this.accountType = accountType;
        this.accountBalance = accountBalance;
    }

    public Account(String accountName, int accountCode, float accountBalance) {
        this.accountName = accountName;
        this.accountCode = accountCode;
        this.accountBalance = accountBalance;
    }
    
    
    
    public String getEstado() {
        return estado;
    }

    //Setters And Getters//
    public void setEstado(String estado) {    
        this.estado = estado;
    }

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

    public int getIdAccount() {
        return idAccount;
    }

    public void setIdAccount(int idAccount) {
        this.idAccount = idAccount;
    }
    
}
