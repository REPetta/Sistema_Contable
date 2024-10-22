//Clase encargada del comportamiento de agregar cuenta//
package Controller;

import ConnectionsBD.ChartAccountsConnection;
import Model.Account;
import Model.User;
import View.AddAccount;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class AddAccountController implements ActionListener {
//Atributos//
    private User currentUser=User.getInstancia();
    private ShowAccountsController showAccountsView;
    private AddAccount addAccountView=new AddAccount();
    private ChartAccountsConnection con;
//Controstructor//
    public AddAccountController(){
         this.addAccountView.setTitle("Agregar Cuenta"+" - "+currentUser.getUserName()+" ( "+currentUser.getRol().substring(0, 1).toUpperCase()+currentUser.getRol().substring(1).toLowerCase()+ " ) " );
         this.addAccountView.btnExit.addActionListener(this);
         this.addAccountView.btnAddAccount.addActionListener(this);
         this.addAccountView.cBoxType.addActionListener(this);
    }
//Metodos//
    public void openAddAccountView(){//Muestra la ventana//
        addAccountView.setVisible(true);
    }
    public void closeAddAccountView(){//Cierra la ventana//
        addAccountView.dispose();
    }
    //Metodo para agregar la cuenta//
    public void buttonAddAccount(ActionEvent e) throws ClassNotFoundException, SQLException, IOException{
        if(e.getSource()==addAccountView.btnAddAccount){       
            if (addAccountView.txtAccountName.getText().trim().isEmpty() ||
            addAccountView.txtCode.getText().trim().isEmpty() ||
            addAccountView.txtAmount.getText().trim().isEmpty() ||
            addAccountView.cBoxType.getSelectedItem() == null || 
            addAccountView.cBoxType.getSelectedItem().toString().trim().isEmpty()) {
            
            // Mostrar mensaje de error
            JOptionPane.showMessageDialog(null, "Error: No puede haber campos vacíos.");
            addAccountView.txtAccountName.setText("");
            addAccountView.txtCode.setText("");
            addAccountView.txtAmount.setText("");
            addAccountView.cBoxType.setSelectedIndex(0);
            return; // Detener el proceso si hay campos vacíos
        }
                    Account newAccount= new Account();
                    newAccount.setAccountName(addAccountView.txtAccountName.getText().trim());
                    newAccount.setAccountCode(Integer.parseInt(addAccountView.txtCode.getText().trim()));
                    newAccount.setAccountType(addAccountView.cBoxType.getSelectedItem().toString().trim());
                    newAccount.setAccountBalance(Float.parseFloat(addAccountView.txtAmount.getText().trim()));
                    if(validateAccount(newAccount)==true){
                           con= new ChartAccountsConnection();
                           con.addAccount(newAccount);
                           addAccountView.txtAccountName.setText("");
                           addAccountView.txtCode.setText("");
                           addAccountView.txtAmount.setText("");
                           addAccountView.cBoxType.setSelectedIndex(0);         
                        }else{
                            addAccountView.txtAccountName.setText("");
                            addAccountView.txtCode.setText("");
                            addAccountView.txtAmount.setText("");
                            addAccountView.cBoxType.setSelectedIndex(0);
                        }
                    }
    }
    //Metodo para validar la cuenta//
    public boolean validateAccount(Account account){
     
        if(account.getAccountCode()< 100 || account.getAccountCode()>599){
            JOptionPane.showMessageDialog(null, "Error ingrese un codigo de cuenta valido");
            return false;
        }
        if(!account.getAccountType().toUpperCase().equals("PASIVO") && !account.getAccountType().toUpperCase().equals("ACTIVO") && !account.getAccountType().toUpperCase().equals("PATRIMONIO NETO") && !account.getAccountType().toUpperCase().equals("RESULTADO POSITIVO") && !account.getAccountType().toUpperCase().equals("RESULTADO NEGATIVO")){
            JOptionPane.showMessageDialog(null, "Error ingrese un tipo de cuenta valido");
            return false;
        }
        if(account.getAccountBalance()<0){
                JOptionPane.showMessageDialog(null, "Error el saldo debe ter valor positivo");
                return false;
        }
        return true;
    }
    //Metodo para cerrar la vista//
    public void buttonBack(ActionEvent e) throws SQLException, ClassNotFoundException, IOException{//Metodo que le da al boton salir la accion de salir funcionalidad//
       if(e.getSource()==addAccountView.btnExit){
           closeAddAccountView();
           showAccountsView= new ShowAccountsController();
           showAccountsView.openShowAccountsView();
       }
    }
   @Override
    public void actionPerformed(ActionEvent e) {
        try {
            buttonBack(e);
        } catch (SQLException ex) {
            Logger.getLogger(AddAccountController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AddAccountController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(AddAccountController.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            buttonAddAccount(e);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AddAccountController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(AddAccountController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(AddAccountController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
