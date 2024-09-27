

package Controller;

import View.AddUser;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddUserController implements ActionListener {
    //Atributos//
    private AddUser addUserView=new AddUser();
    private MainMenuController mainMenuController;
    //Metodos//
    public AddUserController(){//Conecta el boton Volver con la Clase
        this.addUserView.btnBack.addActionListener(this);
    }
    public void openAddUserView(){
        addUserView.setVisible(true);
    }
    public void closeAddUserView(){
        addUserView.dispose();
    }
   public void buttonBack(ActionEvent e){//Metodo que le da al boton volver la accion de salir de la ventana Agregar Usuario y volver al Menu Principal//
       if(e.getSource()==addUserView.btnBack){
           closeAddUserView();
           mainMenuController=new MainMenuController();
           mainMenuController.openMainMenuView();
       }
   
   }

   

    @Override
    public void actionPerformed(ActionEvent e) {
      buttonBack(e);
    }
    
}
