
package Controller;

import View.DeleteUser;
import View.MainMenu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DeleteUserController implements ActionListener{
    //Atributos//
    private DeleteUser deleteUserView=new DeleteUser();
    private MainMenu mainMenuView=new MainMenu();
    //Metodos//
    public DeleteUserController(){//Conecta el boton Volver con la Clase
        this.deleteUserView.btnBack.addActionListener(this);
    }
    public void openDeleteUserView(){
        deleteUserView.setVisible(true);
    }
    public void closeDeleteUserView(){
        deleteUserView.dispose();
    }
     /* Todos los button back no solo despligan la vista pero no  traen sus controladores
     public void buttonBack(ActionEvent e){//Metodo que le da al boton volver la accion de salir de la ventana Dar de Baja Usuario y volver al Menu Principal//
       if(e.getSource()==deleteUserView.btnBack){
           closeDeleteUserView();
           mainMenuView.setVisible(true);
       }
   } */

    @Override
    public void actionPerformed(ActionEvent e) {
        //buttonBack(e);
    }
    
}
