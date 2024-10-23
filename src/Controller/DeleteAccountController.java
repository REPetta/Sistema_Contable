//Clase encargada del comportamiento del boton eliminar cuenta//
package Controller;

import ConnectionsBD.ChartAccountsConnection;
import Model.Account;
import Model.AccountNode;
import Model.User;
import View.DeleteAccount;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import static java.lang.String.valueOf;
import javax.swing.JOptionPane;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;



public class DeleteAccountController implements ActionListener {
//Atributos//
    private User currentUser=User.getInstancia();
    private DeleteAccount deleteAccountView=new DeleteAccount();
    private Account selectedAccount; // Cuenta seleccionada
    private ChartAccountsConnection chartAccountsConnection = new ChartAccountsConnection();
    private ShowAccountsController showAccountsView;
//Constructor//
    public DeleteAccountController(Account selectedAccount){
        this.selectedAccount=selectedAccount;
        this.deleteAccountView.setTitle("Borrar Cuenta"+" - "+currentUser.getUserName()+" ( "+currentUser.getRol().substring(0, 1).toUpperCase()+currentUser.getRol().substring(1).toLowerCase()+ " ) " );
        this.deleteAccountView.btnNot.addActionListener(this);
        this.deleteAccountView.btnYes.addActionListener(this);
           // Mostrar la información de la cuenta seleccionada
        if (this.selectedAccount != null) {
            this.deleteAccountView.txtName.setText(this.selectedAccount.getAccountName());
            this.deleteAccountView.txtCode.setText(String.valueOf(this.selectedAccount.getAccountCode()));
            this.deleteAccountView.txtAmount.setText(String.valueOf(this.selectedAccount.getAccountBalance()));
            this.deleteAccountView.txtType.setText(this.selectedAccount.getAccountType());
        } else {
            // Manejo de error si la cuenta es nula
            JOptionPane.showMessageDialog(null, "La cuenta seleccionada es nula.", "Error", JOptionPane.ERROR_MESSAGE);
        }   
    }
    

//Metodos//
    public void openDeleteAccountView(){
        deleteAccountView.setVisible(true);
    }
    //Metodo para el boton yes//
    public void buttonYes(ActionEvent e) {
         if (e.getSource() == deleteAccountView.btnYes) {
        try {
            // Intento de eliminar la cuenta usando la conexión a la base de datos
            boolean success = chartAccountsConnection.deleteAccount(selectedAccount.getAccountCode());
            
            if (success=true) {
                // Si se elimina correctamente, mostrar mensaje de éxito
                JOptionPane.showMessageDialog(null, "La cuenta ha sido eliminada correctamente.");
                deleteAccountView.dispose(); // Cerrar la ventana de eliminación
                showAccountsView= new ShowAccountsController();
                showAccountsView.openShowAccountsView();
            } else {
                // Si no se elimina, mostrar un mensaje de error al usuario
                JOptionPane.showMessageDialog(null, "No se pudo eliminar la cuenta.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            // Manejo de errores relacionados con la base de datos
            ex.printStackTrace(); // Imprimir la traza de error en la consola
            JOptionPane.showMessageDialog(null, "Error al intentar eliminar la cuenta: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            // Manejo de cualquier otro tipo de excepción no relacionada con SQL
            ex.printStackTrace(); // Imprimir la traza de error en la consola
            JOptionPane.showMessageDialog(null, "Ocurrió un error inesperado: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }    }
    //Metodo para el boton no//
    public void buttonNot(ActionEvent e) throws SQLException, ClassNotFoundException, IOException{
        if(e.getSource()==deleteAccountView.btnNot){
            this.deleteAccountView.dispose();
            showAccountsView= new ShowAccountsController();
            showAccountsView.openShowAccountsView();
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            buttonNot(e);
        } catch (SQLException ex) {
            Logger.getLogger(DeleteAccountController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DeleteAccountController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DeleteAccountController.class.getName()).log(Level.SEVERE, null, ex);
        }
        buttonYes(e);
    }
    
}
