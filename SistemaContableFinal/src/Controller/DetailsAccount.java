
package Controller;

import Connection.AccountConnection;
import Model.Account;
import Model.SingletonUser;

import View.DetailsAccountView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;


public class DetailsAccount implements ActionListener{

   private final SingletonUser currentUser= SingletonUser.getInstance();
   private final DetailsAccountView view;
   private ShowAccounts chartAccounts;
   
   public DetailsAccount (ShowAccounts chartAccount) throws SQLException{
       this.chartAccounts=chartAccount;
       view=new DetailsAccountView();
       this.view.setTitle("Detalles de la Cuenta"+"-"+currentUser.getUserName().toUpperCase()+"("+currentUser.getRol()+")");
       initializeListeners();
   }
  
   //Metodo  para inicializar los listener con los botones//
   public final void initializeListeners(){
       this.view.btnBack.addActionListener(this);
       this.view.btnDelete.addActionListener(this);
       this.view.btnEdit.addActionListener(this);
   }
   //Metodo para abri la ventana//
   public void openDetailsAccountView(){
       this.view.setVisible(true);
   }
   //Metodo para cerrar la ventana//
   public void closeDetailsAccountView(){
       this.view.dispose();
   }
   //Metodo mostrar los datos de la cuenta//
   public final void loadDetails(Account account) {
       this.view.txtName.setText(account.getAccountName());
       this.view.txtCode.setText(String.valueOf(account.getCode()));
       this.view.txtType.setText(account.getType());
       this.view.txtAmount.setText(String.valueOf(account.getBalance()));
   }
   //Metodo para el boton Dar de Baja //
    public void buttonDelete(ActionEvent e) throws SQLException, ClassNotFoundException, IOException{
        if(e.getSource()==view.btnDelete){
         chartAccounts.closeShowAccountsView();
         int confirm = JOptionPane.showConfirmDialog(
                        null,
                        "¿Estás seguro de que deseas dar de baja esta cunta?",
                        "Confirmación",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE
            );
                  if(confirm == JOptionPane.YES_OPTION) { // Si el usuario confirma, intenta agregarlo//
                       AccountConnection con=new AccountConnection();
                       int code=Integer.parseInt(view.txtCode.getText());
                       boolean success=con.cancelAccount(code);
                       if(success){
                                JOptionPane.showMessageDialog(
                                null,
                                 "La cuenta  ha sido dado de baja exitosamente  \n",
                                 "Confirmacion",
                                 JOptionPane.INFORMATION_MESSAGE
                                );
                                 closeDetailsAccountView();
                                 chartAccounts=new ShowAccounts();
                                 chartAccounts.openShowAccountsView();
                                 
                       }else{
                                JOptionPane.showMessageDialog(
                                null,
                                "La cuenta no ha podido ser dado de baja, ya que sea han realizado operaciones sobre ella \n",
                                 "Error",
                                 JOptionPane.ERROR_MESSAGE
                                );
                                closeDetailsAccountView();
                                chartAccounts=new ShowAccounts();
                                chartAccounts.openShowAccountsView();
                       }
                  }
                  
        }
    }
   
   //Metodo para el boton  editar//
    public void buttonEdit(ActionEvent e) throws SQLException, ClassNotFoundException, IOException{
        if(e.getSource()==view.btnEdit){
            String nuevoNombre = JOptionPane.showInputDialog(
             view,
            "Ingrese el nuevo nombre para la cuenta:",
            "Editar Nombre de Cuenta",
            JOptionPane.QUESTION_MESSAGE
        );
            // Verifica si el usuario presionó "Cancelar"
             if (nuevoNombre == null) {
                // El usuario canceló, no hace nada
                return; // Sale del método
            }
            // Verifica si el usuario ingresó un valor
            if (nuevoNombre != null && !nuevoNombre.trim().isEmpty()) {
                // Llama al método de edición con el nuevo nombre
                AccountConnection con=new AccountConnection();
                int code=Integer.parseInt(view.txtCode.getText());
                boolean success = con.editAccount(code,nuevoNombre);
    
                    if (success) {
                        JOptionPane.showMessageDialog(
                        view,
                        "El nombre de la cuenta se actualizó correctamente.",
                        "Edición Exitosa",
                        JOptionPane.INFORMATION_MESSAGE
                        );
                        closeDetailsAccountView();
                        chartAccounts=new ShowAccounts();
                        chartAccounts.openShowAccountsView();
                        
                    } else {
                        JOptionPane.showMessageDialog(
                        view,
                        "No se pudo actualizar el nombre de la cuenta, ya que ya existe una con ese nombre",
                        "Error en la Edición",
                        JOptionPane.ERROR_MESSAGE
                        );
                        closeDetailsAccountView();
                        chartAccounts=new ShowAccounts();
                        chartAccounts.openShowAccountsView();
                    }
                 } else {
                        JOptionPane.showMessageDialog(
                        view,
                        "No se ingresó un nombre válido.",
                        "Edición Cancelada",
                        JOptionPane.WARNING_MESSAGE
                );
            }
        }
    }
   //Metodo para el boton back//
    public void buttonExit(ActionEvent e) throws SQLException, ClassNotFoundException, IOException{
       if(e.getSource()==view.btnBack){
           closeDetailsAccountView();
           chartAccounts.openShowAccountsView();
           
       }
   }
    @Override
    public void actionPerformed(ActionEvent e) {
       try {
           buttonExit(e);
       } catch (SQLException ex) {
           Logger.getLogger(DetailsAccount.class.getName()).log(Level.SEVERE, null, ex);
       } catch (ClassNotFoundException ex) {
           Logger.getLogger(DetailsAccount.class.getName()).log(Level.SEVERE, null, ex);
       } catch (IOException ex) {
           Logger.getLogger(DetailsAccount.class.getName()).log(Level.SEVERE, null, ex);
       }
       try {
           buttonDelete(e);
       } catch (SQLException ex) {
           Logger.getLogger(DetailsAccount.class.getName()).log(Level.SEVERE, null, ex);
       } catch (ClassNotFoundException ex) {
           Logger.getLogger(DetailsAccount.class.getName()).log(Level.SEVERE, null, ex);
       } catch (IOException ex) {
           Logger.getLogger(DetailsAccount.class.getName()).log(Level.SEVERE, null, ex);
       }
       try {
           buttonEdit(e);
       } catch (SQLException ex) {
           Logger.getLogger(DetailsAccount.class.getName()).log(Level.SEVERE, null, ex);
       } catch (ClassNotFoundException ex) {
           Logger.getLogger(DetailsAccount.class.getName()).log(Level.SEVERE, null, ex);
       } catch (IOException ex) {
           Logger.getLogger(DetailsAccount.class.getName()).log(Level.SEVERE, null, ex);
       }
    }
    
    
}
