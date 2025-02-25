
package Controller;

import Connection.AccountConnection;
import Connection.CustomerConnection;
import Model.Account;
import Model.Customer;
import Model.SingletonUser;
import View.AddCustomerView;
import static View.LoginView.blinkingFields;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;


public class AddCustomer implements ActionListener {
    
    private final SingletonUser currentUser= SingletonUser.getInstance();
    private final AddCustomerView view;
    private CustomerConnection con;
    private SalesSystem salesSystem;
    
    public AddCustomer (){
        view=new AddCustomerView();
        this.view.setTitle("Agregar Cliente"+"-"+currentUser.getUserName().toUpperCase()+"("+currentUser.getRol()+")");
        setRazonSocial();
        setCondicionIVA();
        setTipoCliente();
        initializeListeners();
    }
    //Metodo  para inicializar los listener con los botones//
    public final void initializeListeners(){
        this.view.btnAdd.addActionListener(this);
        this.view.btnBack.addActionListener(this);
    }
     //Metodo para abri la ventana//
   public void openAddCustomerView(){
       this.view.setVisible(true);
   }
   //Metodo para cerrar la ventana//
   public void closeAddCustomerView(){
       this.view.dispose();
   }
   //Metodo para limpiar los campos//
   public void fieldsClear(){
       view.txtName.setText("");
       view.txtLastName.setText("");
       view.txtDni.setText("");
       view.txtEmail.setText("");
       view.jComboIVA.setSelectedIndex(0);
       view.jComboRS.setSelectedIndex(0);
       view.jComboTCustomer.setSelectedIndex(0);
   }
   //Metodo para validar el dni//
  public boolean isValidDni(String dni) {
    try {
        // Verifica que sea un número entero
        int dniNumber = Integer.parseInt(dni);
        // Verifica que sea un mayor a 0
        if (dniNumber<0){
            return false;
        }
         // Verifica que sea un menor a 100 millones
        if(dniNumber>99999999){
            return false;
        }
    } catch (NumberFormatException e) {
        // Si no es un número entero, no es válido
        return false;
    }
        //Si pasa todas las validaciones es un dni valido
        return true;
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
    if (fieldNames[i].equalsIgnoreCase("DNI")) {
        JTextField textField = (JTextField) fields[i];
        String dni = textField.getText();
        // Valida el código con el nuevo método
        if (!isValidDni(dni)) {
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
   //Metodo para setear la razon social//
    public final void setRazonSocial() {
        
            List<String> razones = new ArrayList<>(Arrays.asList("","Persona Humana (Unipersonal)", "Sociedad de Responsabilidad Limitada (SRL)", "Sociedad Anonima (SA)", "Sociedad por Acciones Simplificada (SAS)","Sociedad Colectiva","Cooperativas"));
            
            // Crear un modelo para el JComboBox
            DefaultComboBoxModel<String> model = 

                new DefaultComboBoxModel<>();

            for (String razon : razones) {
                model.addElement(razon); // Agregar el nombre de la cuenta
            }

            // Setear el modelo en el JComboBox
            view.jComboRS.setModel(model); 
    // Asegúrate de que addSeatView tenga cbbCuentas

    }
    //Metodo para setear la condicion de IVA//
     public final void setCondicionIVA() {
        
            List<String> condiciones = new ArrayList<>(Arrays.asList("","Responsable Inscripto", "Monotributista","Excento de IVA ", "Consumidor Final","No Responsable de IVA","Sujeto No Categorizado"));
            
            // Crear un modelo para el JComboBox
            DefaultComboBoxModel<String> model = 

                new DefaultComboBoxModel<>();

            for (String condicion : condiciones) {
                model.addElement(condicion); // Agregar el nombre de la cuenta
            }

            // Setear el modelo en el JComboBox
            view.jComboIVA.setModel(model); 
    // Asegúrate de que addSeatView tenga cbbCuentas

    }
     //Metodo para setear la condicion de IVA//
     public final void setTipoCliente() {
        
            List<String> tipos = new ArrayList<>(Arrays.asList("","Consumidor Final", "Responsable Inscripto", "Monotributista","Exento de IVA","Sujeto No Categorizado"));
            
            // Crear un modelo para el JComboBox
            DefaultComboBoxModel<String> model = 

                new DefaultComboBoxModel<>();

            for (String tipo : tipos) {
                model.addElement(tipo); // Agregar el nombre de la cuenta
            }

            // Setear el modelo en el JComboBox
            view.jComboTCustomer.setModel(model); 
    // Asegúrate de que addSeatView tenga cbbCuentas

    }
     //Metodo para agregar el cliente//
     public void buttonAddCustomer(ActionEvent e) throws SQLException{
         if(e.getSource()==view.btnAdd){
               Object[] fields = {
            view.txtName,
            view.txtLastName,
            view.txtDni,
            view.txtEmail,
            view.jComboRS,
            view.jComboIVA,
            view.jComboTCustomer
        };
        String[] fieldNames = {
            "Nombre",
            "Apellido",
            "DNI",
            "Email",
            "Razon Social",
            "Condicion IVA",
            "Tipo de Cliente"
        };
          // Validar los campos
        if (validateFields(fields, fieldNames)) {
            int confirm = JOptionPane.showConfirmDialog(
                        null,
                        "¿Estás seguro de que deseas agregar este Cliente?",
                        "Confirmación",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE
            );
             if(confirm == JOptionPane.YES_OPTION) { // Si el usuario confirma, intenta agregarlo//
                  con=new CustomerConnection();  
                  Customer customer= new Customer();
                    
                    String name = ((JTextField) fields[0]).getText().trim();
                    int dni = Integer.parseInt(((JTextField) fields[2]).getText().trim());
                    String surName= ((JTextField) fields[1]).getText().trim();
                    String email=((JTextField) fields[3]).getText().trim();
                    String razonSocial  = ((JComboBox<?>) fields[4]).getSelectedItem().toString().trim();
                    String  ivaCondition = ((JComboBox<?>) fields[5]).getSelectedItem().toString().trim();
                    String clientType=((JComboBox<?>) fields[6]).getSelectedItem().toString().trim();
                    
                    customer.setClientName(name);
                    customer.setClientSurname(surName);
                    customer.setDni(dni);
                    customer.setEmail(email);
                    customer.setSocialReason(razonSocial);
                    customer.setIvaCondition(ivaCondition);
                    customer.setClientType(clientType);
                    
                    if(!con.isClientExist(customer.getDni())){    //Si el usuario no existe en la base lo carga , en caso contrario retorna un mensaje de error//
                        boolean load=con.addCustomer(customer);
                            if(load){
                                JOptionPane.showMessageDialog(
                                null,
                                "El cliente ha sido cargado exitosamente  \n",
                                 "Confirmacion",
                                 JOptionPane.INFORMATION_MESSAGE
                                );
                                fieldsClear();
                            }else{
                                JOptionPane.showMessageDialog(
                                null,
                                "El Cliente no ha podido ser cargado \n",
                                 "Error",
                                 JOptionPane.ERROR_MESSAGE
                                );
                                fieldsClear();
                            }
                    }else{
                    JOptionPane.showMessageDialog(
                                null,
                                "El Cliente  "+ customer.getClientName()+" ya existe \n",
                                 "Error",
                                 JOptionPane.ERROR_MESSAGE
                                );
                    fieldsClear();
                     }
                  }
            
            }
            fieldsClear();
    }
    
                    // Extrae los valores de los componentes
//                     String name = ((JTextField) fields[0]).getText().trim();
//                     String lastName=((JTextField) fields[1]).getText().trim();
//                    int dni = Integer.parseInt(((JTextField) fields[2]).getText().trim());
//                    String email=((JTextField) fields[3]).getText().trim();
//                     String razonSocial = ((JComboBox<?>) fields[4]).getSelectedItem().toString().trim();
//                    String condicionIva = ((JComboBox<?>) fields[5]).getSelectedItem().toString().trim();
//                    String typeCustomer = ((JComboBox<?>) fields[6]).getSelectedItem().toString().trim();
//
//
//                // Crea el objeto Account
//                customer = new Customer(name, lastName,razonSocial, dni, condicionIva, typeCustomer, email);
//                
//                con=new CustomerConnection();
 
            } 
     
     
    //Metodo para salir
    public void buttonExit(ActionEvent e){
        if(e.getSource()==view.btnBack){
           closeAddCustomerView();
           salesSystem=new SalesSystem();
           salesSystem.openSalesSystemView();
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            buttonAddCustomer(e);
        } catch (SQLException ex) {
            Logger.getLogger(AddCustomer.class.getName()).log(Level.SEVERE, null, ex);
        }
            buttonExit(e);
    }
}
