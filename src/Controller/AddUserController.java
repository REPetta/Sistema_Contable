//Clase encargada del comportamiento de la vista de "Agregar Usuario"//

package Controller;

import View.AddUser;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddUserController implements ActionListener {
    //Atributos//
    private AddUser addUserView=new AddUser();
    private MainMenuController mainMenuController;
    private SingletonController singletonController;
    //Metodos//
    public AddUserController(SingletonController singletonController){//Conecta el boton Volver con la Clase
        this.singletonController=singletonController;
        this.addUserView.btnBack.addActionListener(this);
    }
    public void openAddUserView(){//Muestra la ventana//
        addUserView.setVisible(true);
    }
    public void closeAddUserView(){//Cierra la ventana//
        addUserView.dispose();
    }
   public void buttonBack(ActionEvent e){//Metodo que le da al boton volver la accion de salir de la ventana Agregar Usuario y volver al Menu Principal//
       if(e.getSource()==addUserView.btnBack){
           closeAddUserView();
           mainMenuController=new MainMenuController(singletonController);
           mainMenuController.openMainMenuView();
       }
   
   }
   
    @Override
    public void actionPerformed(ActionEvent e) {
      buttonBack(e);
    }
    
}
