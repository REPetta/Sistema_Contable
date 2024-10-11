//Clase dedicada al comportamiento del plan de cuentas//
package Controller;

import Model.Account;
import Model.AccountNode;

public class ChartAccountsController {
    //Atributos//
    private AccountNode root;
    private Account chartAccounts=new Account("PLAN DE CUENTAS",0,"PLAN DE CUENTAS",0,0);
    private Account assets=new Account("ACTIVOS",100,"ACTIVOS",0,0);
    private Account passive=new Account("PASIVOS",200,"PASIVO",0,0);
    private Account netEquity=new Account("PATRIMONIOS NETOS",300,"PATRIMONIO NETO",0,0);
    private Account resultsPositives= new Account("RESULTADOS POSITIVOS",400,"RESULTADO POSITIVO",0,0);
    private Account resultsNegatives=new Account("RESULTADOS NEGATIVOS",500,"RESULTADO NEGATIVO",0,0);
    
    
    //Contructor//
    public ChartAccountsController(){
        this.root=new AccountNode(chartAccounts);
        this.root.addSubAccount(new AccountNode(assets));
        this.root.addSubAccount(new AccountNode(passive));
        this.root.addSubAccount(new AccountNode(netEquity));
        this.root.addSubAccount(new AccountNode(resultsPositives));
        this.root.addSubAccount(new AccountNode(resultsNegatives));
    }
    //Metodos//
    //Obtener la raiz//
    public AccountNode getRoot(){
        return this.root;
    }
    //Metodo para agregar cuenta//
    public void addAccount(AccountNode root ,AccountNode newNode){
        int fatherCode= getFatherCode(newNode.getAccount().getAccountCode());
        //Buscar el nodo padre en el arbol por el codigo padre
        AccountNode fatherNode=searchNodeForCode(root,fatherCode);
        if(fatherNode!=null){
            //Si se encuentra el nodo padre, agrega la nueva cuenta como subcuenta//
            fatherNode.addSubAccount(newNode);
        }else{
            System.out.println("No se encontró la cuenta con el código: " + fatherNode.getAccount().getAccountCode());
        }
    }
    //Metodo para obtener el codigo del nodo padre//
    private int getFatherCode(int code){
        //Si el codigo es multiplo de 10
        if(code%10==0){
            return code-code%100;//Ejemplo: Si el codigo es 210, el codigo padre es 200//
        }else{
            //Si el codigo no es multiplo de 10, simplemente quita el ultimo digito
            return code-(code%10);//Ejemplo: Si el codigo es 211, el codigo padre es 210//
        }
    }
    //Metodo para buscar nodo por codigo//
    private AccountNode searchNodeForCode(AccountNode currentNode, int code){
     //Si el nodo actual tiene el codigo buscado, devolverlo//
        if(currentNode.getAccount().getAccountCode()==code){
            return currentNode;
         }
        //Recorre las subcuentas de forma recursiva//
        if(currentNode.getSubAccounts()!=null){
            for(AccountNode subAccount: currentNode.getSubAccounts()){
                AccountNode result=searchNodeForCode(subAccount,code);
                if(result!=null){
                    return result;
             }
            }
        }
        return null;//Si no se encuentra el nodo//
    }
    //Metodo para imprimir el plan de cuentas//
    public void printChartAccounts(){
        printNode(root,0);
    }
    public  void printNode(AccountNode node, int level){
        String identer=" ".repeat(level);
        System.out.println(identer + node.getAccount().getAccountCode() + "--" + node.getAccount().getAccountName() );
        if (node.getSubAccounts()!=null){
            for (AccountNode subAccount : node.getSubAccounts()){
                printNode(subAccount, level+1);
            }
        }    
    }
}