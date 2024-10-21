//Clase encargada del comportamiento del boton modificar cuenta//
package Controller;

import Model.User;
import View.EditAccount;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditAccountController implements ActionListener{
    //Atributos//
    private User currentUser=User.getInstancia();
    private EditAccount editAccountView=new EditAccount();
    //Contructor//
    public EditAccountController(){
        this.editAccountView.setTitle("Editar Cuenta"+" - "+currentUser.getUserName()+" ( "+currentUser.getRol().substring(0, 1).toUpperCase()+currentUser.getRol().substring(1).toLowerCase()+ " ) " );
        this.editAccountView.btnExit.addActionListener(this);
    }
    //Metodos//
    //Metodos//
    public void openEditAccountView(){
        editAccountView.setVisible(true);
    }
    //Metodo para el boton no, que cierra la ventana//
    public void buttonCancel(ActionEvent e){
        if(e.getSource()==editAccountView.btnExit){
            this.editAccountView.dispose();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        buttonCancel(e);
    }
}
