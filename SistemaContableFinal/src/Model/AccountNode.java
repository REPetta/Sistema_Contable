//Clase dedicada a los Nodos del arbol//
package Model;

import java.util.ArrayList;

public class AccountNode {
    //Atributos//
    private Account account;
    private ArrayList<AccountNode> subAccounts;
    //Constructor//
    public AccountNode(Account account){
        this.account=account;
        this.subAccounts=new ArrayList<>();
    }
    public AccountNode(){
         this.subAccounts=new ArrayList<>();
    }
    //Setters And Getters//
    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public ArrayList<AccountNode> getSubAccounts() {
        return subAccounts;
    }

    public void setSubAccounts(ArrayList<AccountNode> subAccounts) {
        this.subAccounts = subAccounts;
    }
    
    public void addSubAccount(AccountNode subAccount){
        this.subAccounts.add(subAccount);
    }
    @Override
    public String toString(){
        return this.getAccount().getAccountName();
    }
    
}
