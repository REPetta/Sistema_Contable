//Clase encargada del comportamiento de la vista de agregar asiento//
package Controller;

import Model.User;
import View.AddAccountSeat;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddAccountSeatController implements ActionListener{
    //Atributos//
    private AddAccountSeat addAccountSeatView=new AddAccountSeat();
    private MainMenuController mainMenuController;
    private User currentUser=User.getInstancia();
    //Metodos//
    public AddAccountSeatController(){//Conecta el boton Volver con la Clase
       this.addAccountSeatView.setTitle("Agregar Asiento"+" - "+currentUser.getUserName()+" ( "+currentUser.getRol().substring(0, 1).toUpperCase()+currentUser.getRol().substring(1).toLowerCase()+ " ) " );
       this.addAccountSeatView.btnBuscar.addActionListener(this);
       this.addAccountSeatView.btnEditar.addActionListener(this);
       this.addAccountSeatView.btnEliminar.addActionListener(this);
       this.addAccountSeatView.btnImprimir.addActionListener(this);
       this.addAccountSeatView.jBtnGuardar.addActionListener(this);
       this.addAccountSeatView.jButnNuevo.addActionListener(this);
       this.addAccountSeatView.jRadioDebe.addActionListener(this);
       this.addAccountSeatView.jRadioHaber.addActionListener(this);
       this.addAccountSeatView.btnBack.addActionListener(this);
    }
    public void openAddAccountSeatView(){//Muestra la ventana//
        addAccountSeatView.setVisible(true);
    }
    public void closeAddAccountSeatView(){//Cierra la ventana//
        addAccountSeatView.dispose();
    }
    public void buttonBack(ActionEvent e){//Metodo que le da al boton volver la accion de salir de la ventana Agregar Asiento y volver al Menu Principal//
       if(e.getSource()==addAccountSeatView.btnBack){
           closeAddAccountSeatView();
           mainMenuController=new MainMenuController();
           mainMenuController.openMainMenuView();
       }
   }
   
    @Override
    public void actionPerformed(ActionEvent e) {
        buttonBack(e);
    }
    
    
}
