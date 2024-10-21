//Clase encargada del comportamiento de la vista mostrar cuentas//
package Controller;

import Controller.EditAccountController;
import Model.User;
import View.ShowChartAccounts;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ShowAccountsController implements ActionListener{
    //Atributos//
    private ShowChartAccounts showAccountsView= new ShowChartAccounts();
    private MainMenuController mainMenuController;
    private User currentUser=User.getInstancia();
    private AddAccountController addAccountView;
    private DeleteAccountController delAccountView;
    private EditAccountController editAccountView;
    //Constructor//
    public ShowAccountsController(){
          this.showAccountsView.setTitle("Mostrar Cuentas"+" - "+currentUser.getUserName()+" ( "+currentUser.getRol().substring(0, 1).toUpperCase()+currentUser.getRol().substring(1).toLowerCase()+ " ) " );
          this.showAccountsView.btnAddAccount.addActionListener(this);
          this.showAccountsView.btnDelAccount.addActionListener(this);
          this.showAccountsView.btnEditAccount.addActionListener(this);
          this.showAccountsView.btnExit.addActionListener(this);
          if(currentUser.getTasks().contains("agregar_usuario")==false){
            this.showAccountsView.btnAddAccount.setVisible(false);
            this.showAccountsView.btnDelAccount.setVisible(false);
            this.showAccountsView.btnEditAccount.setVisible(false);
          }
    }
    //Metodos//
    public void openShowAccountsView(){//Metodo para hacer visible la ventana del MainMenu//
        showAccountsView.setVisible(true);
    }
    public void closeShowAccountsView(){//Metodo para cerrar el menu principal
        showAccountsView.dispose();
    }
    public void buttonAddAccount(ActionEvent e){//Metodo que le da al boton agregar cuenta funcionalidad//
        if(e.getSource()==showAccountsView.btnAddAccount){
            closeShowAccountsView();
            addAccountView=new AddAccountController();
            addAccountView.openAddAccountView();
        }
    }
    public void buttonDeleteAccount(ActionEvent e){//Metodo que le da al boton eliminar cuenta funcionalidad//
        if(e.getSource()==showAccountsView.btnDelAccount){
            delAccountView=new DeleteAccountController();
            delAccountView.openDeleteAccountView();
        }
    }
    public void buttonEditAccount(ActionEvent e){ //Metodo que le da al boton modificar cuenta la funcionalidad//
        if(e.getSource()==showAccountsView.btnEditAccount){
            editAccountView= new EditAccountController();
            editAccountView.openEditAccountView();
        }
    }
    public void buttonExit(ActionEvent e){//Metodo que le da al boton Salir la funcion de cerrar el Menu Principal y volver al Login//
        if(e.getSource()==showAccountsView.btnExit){
            closeShowAccountsView();
            mainMenuController=new MainMenuController();
            mainMenuController.openMainMenuView();
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        buttonExit(e);
        buttonAddAccount(e);
        buttonDeleteAccount(e);
        buttonEditAccount(e);
        
    }
}
