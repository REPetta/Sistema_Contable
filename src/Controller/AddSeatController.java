
package Controller;

import View.AddSeat;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddSeatController implements ActionListener {
    //Atributos//
    private AddSeat addSeatView=new AddSeat();
    private MainMenuController mainMenuController;
    //Metodos//
    public AddSeatController(){//Conecta el boton Volver con la Clase
        this.addSeatView.btnBack.addActionListener(this);
    }
    public void openAddSeatView(){
        addSeatView.setVisible(true);
    }
    public void closeAddSeatView(){
        addSeatView.dispose();
    }
    public void buttonBack(ActionEvent e){//Metodo que le da al boton volver la accion de salir de la ventana Agregar Asiento y volver al Menu Principal//
       if(e.getSource()==addSeatView.btnBack){
           closeAddSeatView();
           SingletonController singletonController=SingletonController.getInstancia();
           mainMenuController=new MainMenuController(singletonController);
           mainMenuController.openMainMenuView();
       }
   }
   
    @Override
    public void actionPerformed(ActionEvent e) {
        buttonBack(e);
    }
    
}
