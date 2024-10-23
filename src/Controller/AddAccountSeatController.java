//Clase encargada del comportamiento de la vista de agregar asiento//
package Controller;

import ConnectionsBD.AccountSeatConnection;
import Model.Account;
import Model.User;
import View.AddAccountSeat;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;

public class AddAccountSeatController implements ActionListener{
    //Atributos//
    private AddAccountSeat addAccountSeatView=new AddAccountSeat();
    private MainMenuController mainMenuController;
    private User currentUser=User.getInstancia();
    //Metodos//
    public AddAccountSeatController(){//Conecta el boton Volver con la Clase
        setCuentasComboBox(); //inicializo el combox//
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
    
    //inicializacion para combobox de cuentas//
    public ArrayList<Account> cuentas () throws IOException,  ClassNotFoundException,   SQLException{
        AccountSeatConnection conexionCuentas =new AccountSeatConnection();
        return conexionCuentas.getAccount();
     }
   
    public void setCuentasComboBox() {
        try {
            // Obtener la lista de cuentas
            ArrayList<Account> cuentas = cuentas(); // Asegúrate de que este método esté disponible en el contexto

            // Crear un modelo para el JComboBox
            DefaultComboBoxModel<String> model = 

                new DefaultComboBoxModel<>();

            // Llenar el modelo con los nombres de las cuentas
            for (Account cuenta : cuentas) {
                model.addElement(cuenta.getAccountName()); // Agregar el nombre de la cuenta
            }

            // Setear el modelo en el JComboBox
            addAccountSeatView.comboCuenta.setModel(model); 
    // Asegúrate de que addSeatView tenga cbbCuentas
        } catch (IOException | SQLException | ClassNotFoundException e) {
            e.printStackTrace(); // Manejar la excepción según sea necesario
        }
    }

}
