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
       this.addAccountSeatView.btnCancelar.addActionListener(this);
       this.addAccountSeatView.btnSaveOperation.addActionListener(this);
       this.addAccountSeatView.btnGuardarAsiento.addActionListener(this);
       this.addAccountSeatView.btnBack.addActionListener(this);
 
    }
    public void openAddAccountSeatView(){//Muestra la ventana//
        addAccountSeatView.setVisible(true);
    }
    public void closeAddAccountSeatView(){//Cierra la ventana//
        addAccountSeatView.dispose();
    }
    
    private void cancelSeat(ActionEvent e) {
    if (e.getSource() == addAccountSeatView.btnCancelar) {
        // Limpiar campos
        limpiarVista();
    }
}

    private void limpiarVista() {
        addAccountSeatView.txtDescripcion.setText("");
        addAccountSeatView.txtImporte.setText("");
        addAccountSeatView.comboCuenta.setSelectedIndex(0);
        //addAccountSeatView.tableModel.setRowCount(0); // Vaciar la tabla
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
        cancelSeat(e);
    }
    
    
}
