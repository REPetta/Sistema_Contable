

package Controller;

import View.AddUser;
import View.MainMenu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddUserController implements ActionListener {
    //Atributos//
    private AddUser addUserView=new AddUser();
    private MainMenu mainMenuView=new MainMenu();
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
     /*Todos los metodos buttonBack despliegan la vista pero no traen sus controladores
   public void buttonBack(ActionEvent e){//Metodo que le da al boton volver la accion de salir de la ventana Agregar Usuario y volver al Menu Principal//
       if(e.getSource()==addUserView.btnBack){
           closeAddUserView();
           mainMenuView.setVisible(true);
       }
   
   }
*/
   

    @Override
    public void actionPerformed(ActionEvent e) {
      //  buttonBack(e);
    }
    
}
