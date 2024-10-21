//Clase encargada del comportamiento del boton eliminar cuenta//
package Controller;

import Model.User;
import View.DeleteAccount;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DeleteAccountController implements ActionListener {
//Atributos//
    private User currentUser=User.getInstancia();
    private DeleteAccount deleteAccountView=new DeleteAccount();
//Constructor//
    public DeleteAccountController(){
        this.deleteAccountView.setTitle("Borrar Cuenta"+" - "+currentUser.getUserName()+" ( "+currentUser.getRol().substring(0, 1).toUpperCase()+currentUser.getRol().substring(1).toLowerCase()+ " ) " );
        this.deleteAccountView.btnNot.addActionListener(this);
    }
//Metodos//
    public void openDeleteAccountView(){
        deleteAccountView.setVisible(true);
    }
    //Metodo para el boton no, que cierra la ventana//
    public void buttonNot(ActionEvent e){
        if(e.getSource()==deleteAccountView.btnNot){
            this.deleteAccountView.dispose();
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        buttonNot(e);
    }
    
}
