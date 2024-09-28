//Clase encargada del comportamiento
package Controller;

import View.DeleteUser;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DeleteUserController implements ActionListener{
    //Atributos//
    private DeleteUser deleteUserView=new DeleteUser();
    private MainMenuController mainMenuController;
    private SingletonController singletonController;
    //Metodos//
    public DeleteUserController(SingletonController singletonController){//Conecta el boton Volver con la Clase//
        this.singletonController=singletonController;
        this.deleteUserView.btnBack.addActionListener(this);
    }
    public void openDeleteUserView(){//Muestra la ventana//
        deleteUserView.setVisible(true);
    }
    public void closeDeleteUserView(){//Cierra la ventana//
        deleteUserView.dispose();
    }
    public void buttonBack(ActionEvent e){//Metodo que le da al boton volver la accion de salir de la ventana Dar de Baja Usuario y volver al Menu Principal//
       if(e.getSource()==deleteUserView.btnBack){
           closeDeleteUserView();
           mainMenuController=new MainMenuController(singletonController);
           mainMenuController.openMainMenuView();
           
       }
   } 

    @Override
    public void actionPerformed(ActionEvent e) {
        buttonBack(e);
    }
    
}
