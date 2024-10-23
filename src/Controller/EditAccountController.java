//Clase encargada del comportamiento del boton modificar cuenta//
package Controller;

import ConnectionsBD.ChartAccountsConnection;
import Model.Account;
import Model.User;
import View.EditAccount;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class EditAccountController implements ActionListener{
    //Atributos//
    private User currentUser=User.getInstancia();
    private EditAccount editAccountView=new EditAccount();
    private Account selectedAccount; // Cuenta seleccionada
    private ShowAccountsController showAccountsView;
    private ChartAccountsConnection con=new ChartAccountsConnection();
    //Contructor//
    public EditAccountController(Account selectedAccount){
        this.editAccountView=new EditAccount();
        this.selectedAccount=selectedAccount;
        this.editAccountView.setTitle("Editar Cuenta"+" - "+currentUser.getUserName()+" ( "+currentUser.getRol().substring(0, 1).toUpperCase()+currentUser.getRol().substring(1).toLowerCase()+ " ) " );
        this.editAccountView.btnUpdate.addActionListener(this);
        this.editAccountView.btnExit.addActionListener(this);
         if (this.selectedAccount != null) {
                this.editAccountView.txtName.setText(this.selectedAccount.getAccountName());
                this.editAccountView.txtAmount.setText(this.selectedAccount.getEstado().toUpperCase());
            } else {
                // Manejo de error si la cuenta es nula
                JOptionPane.showMessageDialog(null, "La cuenta seleccionada es nula.", "Error", JOptionPane.ERROR_MESSAGE);
           }   
    }
    //Metodos//
    //Metodos//
    public void openEditAccountView(){
        editAccountView.setVisible(true);
    }
    //Metodo para el boton actualizar//
   private void buttonUpdate(ActionEvent e) throws ClassNotFoundException, IOException {
       if(e.getSource()==editAccountView.btnUpdate){
    try {
        // Recoger los datos de la vista de edición
        String accountName = editAccountView.txtName.getText();
        int accountCode = selectedAccount.getAccountCode();
        float accountBalance = Float.parseFloat(editAccountView.txtAmount.getText());
        //String accountType = editAccountView.cBoxType.getSelectedItem().toString();

        // Validar los datos (puedes agregar más validaciones según sea necesario)
        if (accountName.isEmpty()  || accountBalance < 0) {
            JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos correctamente.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Crear un objeto Account con los nuevos valores
        Account updatedAccount = new Account(accountName, accountCode, accountBalance);

        // Llamar al método para actualizar la cuenta en la base de datos
        boolean success = con.updateAccount(updatedAccount);
        if (success=true) {
            JOptionPane.showMessageDialog(null, "La cuenta se ha actualizado correctamente.");
            editAccountView.dispose(); // Cerrar la vista después de actualizar
            showAccountsView= new ShowAccountsController();
            showAccountsView.openShowAccountsView();
        } else {
            JOptionPane.showMessageDialog(null, "No se pudo actualizar la cuenta.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(null, "Error en los datos ingresados. Por favor, verifica los campos numéricos.", "Error", JOptionPane.ERROR_MESSAGE);
    } catch (SQLException ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(null, "Error al intentar actualizar la cuenta.", "Error", JOptionPane.ERROR_MESSAGE);
    }
       }
}

        
        

    //Metodo para el boton no, que cierra la ventana//
    public void buttonCancel(ActionEvent e) throws SQLException, ClassNotFoundException, IOException{
        if(e.getSource()==editAccountView.btnExit){
            this.editAccountView.dispose();
            showAccountsView= new ShowAccountsController();
            showAccountsView.openShowAccountsView();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            buttonCancel(e);
        } catch (SQLException ex) {
            Logger.getLogger(EditAccountController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(EditAccountController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(EditAccountController.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            buttonUpdate(e);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(EditAccountController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(EditAccountController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
