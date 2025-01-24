/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Connection.AccountConnection;
import Model.Account;
import Model.SingletonUser;
import View.AddAccountView;
import static View.LoginView.blinkingFields;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 *
 * @author Rodrigo
 */
public class AddAccount implements ActionListener {
    
   private final SingletonUser currentUser= SingletonUser.getInstance();
   private final AddAccountView view;
   private ShowAccounts chartAccounts;
   private  AccountConnection con;
   
   public AddAccount(){
       view=new AddAccountView();
       this.view.setTitle("Agregar Cuental"+"-"+currentUser.getUserName().toUpperCase()+"("+currentUser.getRol()+")");
       initializeListeners();
   }
     //Metodo  para inicializar los listener con los botones//
   public final void initializeListeners(){
       this.view.btnAddAccount.addActionListener(this);
       this.view.btnExit.addActionListener(this);
   }
   //Metodo para abri la ventana//
   public void openAddAccountView(){
       this.view.setVisible(true);
   }
   //Metodo para cerrar la ventana//
   public void closeAddAccountView(){
       this.view.dispose();
   }
  
   public void buttonAddAccount(ActionEvent e) throws SQLException{
         if (e.getSource() == view.btnAddAccount) {
        Object[] fields = {
            view.txtAccountName,
            view.txtCode,
            view.cBoxType,
            view.comboRSaldo
        };
        String[] fieldNames = {
            "Nombre de Cuenta",
            "Código",
            "Tipo",
            "Recibe Saldo"
        };

        // Validar los campos
        if (validateFields(fields, fieldNames)) {
                // Extrae los valores de los componentes
                String accountName = ((JTextField) fields[0]).getText().trim();
                int code = Integer.parseInt(((JTextField) fields[1]).getText().trim());
                String type = ((JComboBox<?>) fields[2]).getSelectedItem().toString().trim();
                int receivesBalance = ((JComboBox<?>) fields[3]).getSelectedItem().toString().equalsIgnoreCase("Sí") ? 0 : 1;

                // Crea el objeto Account
                Account account = new Account(accountName, code, type, 0.0, receivesBalance);
                
                con=new AccountConnection();
                con.loadAccount(account);
                fieldsClear();
            } 
            
        }
    }

  //Metodo para limpiar los campos//
   public void fieldsClear(){
       view.txtAccountName.setText("");
       view.txtCode.setText("");
       view.cBoxType.setSelectedIndex(0);
       view.comboRSaldo.setSelectedIndex(0);
   }
   
   //Metodo para validar el codigo//
  public boolean isValidCode(String code, String type, String receivesSaldo) {
    try {
        // Verifica que sea un número entero
        int intCode = Integer.parseInt(code);

        // Condición general: debe estar entre 100 y 600
        if (intCode < 100 || intCode > 600) {
            return false;
        }

        // Condición específica para el tipo "Activo"
        if (type.equalsIgnoreCase("Activo") && (intCode < 100 || intCode > 199)) {
            return false;
        }
        //Condicion especificada para el tipo "Pasivo"
        if(type.equalsIgnoreCase("Pasivo") && (intCode<200 || intCode>299)){
            return false;
        }
        //Condicion para para el tipo "Patrimonio"
        if(type.equalsIgnoreCase("Patriominio Neto") && (intCode<300 || intCode>399)){
                return false;
            }
        //Condicion para el tipo Resultado Positivo
          if(type.equalsIgnoreCase("Resultado Positivo") && (intCode<400 || intCode>499)){
                return false;
            }
         //Condicion para el tipo Resultado Negativo
           if(type.equalsIgnoreCase("Resultado Negativo") && (intCode<500 || intCode>599)){
                return false;
            }
         // Condición si no recibe saldo ("No")
        if (receivesSaldo.equalsIgnoreCase("No") && intCode % 10 != 0) {
            return false;
        }

        // Condición si recibe saldo ("Si")
        if (receivesSaldo.equalsIgnoreCase("Si") && intCode % 10 == 0) {
            return false;
        }

        // Si pasa todas las validaciones, el código es válido
        return true;
    } catch (NumberFormatException e) {
        // Si no es un número entero, no es válido
        return false;
    }
}

   // Método para validar si los campos están vacíos o tienen un formato incorrecto
public boolean validateFields(Object[] fields, String[] fieldNames) {
    boolean hasBugs = false;
    StringBuilder bugs = new StringBuilder(); // Mensaje de error acumulado
    
    // Recorre los campos para verificar si están vacíos
    for (int i = 0; i < fields.length; i++) {
        if (fields[i] instanceof JTextField) {
            JTextField textField = (JTextField) fields[i];
            if (textField.getText().trim().isEmpty()) {
                bugs.append("- El campo ").append(fieldNames[i]).append(" no puede estar vacío.\n");
                blinkingFields(textField); // Método para hacer titilar el campo vacío
                hasBugs = true;
                fieldsClear();
            }
        } else if (fields[i] instanceof JComboBox) {
            JComboBox<?> comboBox = (JComboBox<?>) fields[i];
            if (comboBox.getSelectedItem() == null || comboBox.getSelectedItem().toString().trim().isEmpty()) {
                bugs.append("- El campo ").append(fieldNames[i]).append(" no puede estar vacío.\n");
                hasBugs = true;
                fieldsClear();
            }
        }
    }
    
    // Si hay campos vacíos, muestra un mensaje de error
    if (hasBugs) {
        JOptionPane.showMessageDialog(
            null,
            "Se han encontrado los siguientes problemas:\n" + bugs,
            "Error",
            JOptionPane.ERROR_MESSAGE
        );
        return false;
    } else {
        // Verifica los formatos específicos
StringBuilder invalidFields = new StringBuilder();
for (int i = 0; i < fields.length; i++) {
    if (fieldNames[i].equalsIgnoreCase("Código")) {
        JTextField textField = (JTextField) fields[i];
        String code = textField.getText();

        // Obtiene los valores necesarios para validar el código
        String type = ((JComboBox<?>) fields[2]).getSelectedItem().toString(); // Tipo
        String receivesSaldo = ((JComboBox<?>) fields[3]).getSelectedItem().toString(); // Recibe Saldo

        // Valida el código con el nuevo método
        if (!isValidCode(code, type, receivesSaldo)) {
            invalidFields.append("- El campo ").append(fieldNames[i]).append(" no tiene un formato válido.\n");
            blinkingFields(textField);
            fieldsClear();
        }
    
    }
}
        
        // Si hay errores de formato, muestra el mensaje
        if (!invalidFields.isEmpty()) {
            JOptionPane.showMessageDialog(
                null,
                "Se han encontrado los siguientes problemas:\n" + invalidFields,
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
            return false;
        }
    }
    return true;
}
  //Metodo para cerrar la ventana//
   public void buttonExit(ActionEvent e) throws SQLException, ClassNotFoundException, IOException{
       if(e.getSource()==view.btnExit){
           closeAddAccountView();
           chartAccounts=new ShowAccounts();
           chartAccounts.openShowAccountsView();
       }
   }
    @Override
    public void actionPerformed(ActionEvent e) {
       try {
           buttonExit(e);
       } catch (SQLException ex) {
           Logger.getLogger(AddAccount.class.getName()).log(Level.SEVERE, null, ex);
       } catch (ClassNotFoundException ex) {
           Logger.getLogger(AddAccount.class.getName()).log(Level.SEVERE, null, ex);
       } catch (IOException ex) {
           Logger.getLogger(AddAccount.class.getName()).log(Level.SEVERE, null, ex);
       }
       try {
           buttonAddAccount(e);
       } catch (SQLException ex) {
           Logger.getLogger(AddAccount.class.getName()).log(Level.SEVERE, null, ex);
       }
    }
}
