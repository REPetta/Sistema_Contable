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


public class DeleteAccountController implements ActionListener {
//Atributos//
    private User currentUser=User.getInstancia();
    private DeleteAccount deleteAccountView=new DeleteAccount();
    private Account selectedAccount; // Cuenta seleccionada
    private ChartAccountsConnection chartAccountsConnection = new ChartAccountsConnection();
//Constructor//
    public DeleteAccountController(Account node){
        this.deleteAccountView.setTitle("Borrar Cuenta"+" - "+currentUser.getUserName()+" ( "+currentUser.getRol().substring(0, 1).toUpperCase()+currentUser.getRol().substring(1).toLowerCase()+ " ) " );
        this.deleteAccountView.btnNot.addActionListener(this);
        this.deleteAccountView.btnYes.addActionListener(this);
           // Mostrar la información de la cuenta seleccionada
        this.deleteAccountView.txtName.setText(selectedAccount.getAccountName());
        this.deleteAccountView.txtCode.setText(String.valueOf(selectedAccount.getAccountCode()));
        this.deleteAccountView.txtAmount.setText(String.valueOf(selectedAccount.getAccountBalance()));
        this.deleteAccountView.txtType.setText(selectedAccount.getAccountType());
    }
    
     public DeleteAccountController(){
        this.deleteAccountView.setTitle("Borrar Cuenta"+" - "+currentUser.getUserName()+" ( "+currentUser.getRol().substring(0, 1).toUpperCase()+currentUser.getRol().substring(1).toLowerCase()+ " ) " );
        this.deleteAccountView.btnNot.addActionListener(this);
        this.deleteAccountView.btnYes.addActionListener(this);
           // Mostrar la información de la cuenta seleccionada
        this.deleteAccountView.txtName.setText(selectedAccount.getAccountName());
        this.deleteAccountView.txtCode.setText(String.valueOf(selectedAccount.getAccountCode()));
        this.deleteAccountView.txtAmount.setText(String.valueOf(selectedAccount.getAccountBalance()));
        this.deleteAccountView.txtType.setText(selectedAccount.getAccountType());
    }
//Metodos//
    public void openDeleteAccountView(){
        deleteAccountView.setVisible(true);
    }
    //Metodo para el boton no, que cierra la ventana//
    public void buttonYes(ActionEvent e){
         /*if (e.getSource() == deleteAccountView.btnYes) {
            try {
                boolean success = chartAccountsConnection.deleteAccount(selectedAccount.getAccountCode());
                if (success) {
                    JOptionPane.showMessageDialog(null, "La cuenta ha sido eliminada correctamente.");
                    deleteAccountView.dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "No se pudo eliminar la cuenta.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error al intentar eliminar la cuenta.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }*/
    }
    public void buttonNot(ActionEvent e){
        if(e.getSource()==deleteAccountView.btnNot){
            this.deleteAccountView.dispose();
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        buttonNot(e);
        buttonYes(e);
    }
    
}
