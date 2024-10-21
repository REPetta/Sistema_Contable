//Clase encargada del comportamiento de agregar cuenta//
package Controller;

import Model.User;
import View.AddAccount;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddAccountController implements ActionListener {
//Atributos//
    private User currentUser=User.getInstancia();
    private ShowAccountsController showAccountsView;
    private AddAccount addAccountView=new AddAccount();
//Controstructor//
    public AddAccountController(){
         this.addAccountView.setTitle("Agregar Cuenta"+" - "+currentUser.getUserName()+" ( "+currentUser.getRol().substring(0, 1).toUpperCase()+currentUser.getRol().substring(1).toLowerCase()+ " ) " );
         this.addAccountView.btnExit.addActionListener(this);
         this.addAccountView.btnAddAccount.addActionListener(this);
    }
//Metodos//
    public void openAddAccountView(){//Muestra la ventana//
        addAccountView.setVisible(true);
    }
    public void closeAddAccountView(){//Cierra la ventana//
        addAccountView.dispose();
    }
    public void buttonBack(ActionEvent e){//Metodo que le da al boton salir la accion de salir funcionalidad//
       if(e.getSource()==addAccountView.btnExit){
           closeAddAccountView();
           showAccountsView= new ShowAccountsController();
           showAccountsView.openShowAccountsView();
       }
    }
   @Override
    public void actionPerformed(ActionEvent e) {
        buttonBack(e);
    }
}
