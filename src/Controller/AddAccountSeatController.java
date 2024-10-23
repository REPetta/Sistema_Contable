//Clase encargada del comportamiento de la vista de agregar asiento//
package Controller;

import Model.User;
import View.AddAccountSeat;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.table.DefaultTableModel;

public class AddAccountSeatController implements ActionListener{
    //Atributos//
    private AddAccountSeat addAccountSeatView=new AddAccountSeat();
    private MainMenuController mainMenuController;
    private User currentUser=User.getInstancia();
    DefaultTableModel modelo = new DefaultTableModel();
    //Metodos//
    public AddAccountSeatController(){//Conecta el boton Volver con la Clase
       this.addAccountSeatView.setTitle("Agregar Asiento"+" - "+currentUser.getUserName()+" ( "+currentUser.getRol().substring(0, 1).toUpperCase()+currentUser.getRol().substring(1).toLowerCase()+ " ) " );
       this.addAccountSeatView.btnCancelar.addActionListener(this);
       this.addAccountSeatView.btnSaveOperation.addActionListener(this);
       this.addAccountSeatView.btnGuardarAsiento.addActionListener(this);
       this.addAccountSeatView.btnBack.addActionListener(this);
       this.addAccountSeatView.cBoxDestiny.addActionListener(this);
       iniciarTabla();
 
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
        iniciarTabla();
        
        }
     
    public void iniciarTabla() {
        
        modelo = new DefaultTableModel() {
            public boolean isCellEditable(int fila, int columna) {
                if (columna == 1 && columna == 2 && columna == 3) {
                    return true;
                } else {
                    return false;
                }
            }
        };

        modelo.addColumn("Fecha");
        modelo.addColumn("Descripcion");
        modelo.addColumn("Cuenta");
        modelo.addColumn("Debe");
        modelo.addColumn("Haber");

        addAccountSeatView.tableModel.setRowHeight(15);
        addAccountSeatView.tableModel.setModel(modelo);
        addAccountSeatView.tableModel.setRowHeight(25);

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
