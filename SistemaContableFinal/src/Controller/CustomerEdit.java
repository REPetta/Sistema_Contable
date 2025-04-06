
package Controller;

import Connection.CustomerConnection;
import Model.Customer;
import Model.SingletonUser;
import View.CustomerEditView;
import static View.LoginView.blinkingFields;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

public class CustomerEdit implements ActionListener{
    private final SingletonUser currentUser= SingletonUser.getInstance();
    private final CustomerEditView view;
    private CustomerConnection con=new CustomerConnection();
    private ClientManagement clientManagement;
    private final int numberDNI;
    
    public CustomerEdit(int dni) throws SQLException{
        numberDNI=dni;
        view= new CustomerEditView();
        this.view.setTitle("Modificar Cliente"+"-"+currentUser.getUserName().toUpperCase()+"("+currentUser.getRol()+")");
        setRazonSocial();
        setCondicionIVA();
        setTipoCliente();
        initializeListeners();
        setEstado();
        loadClient(dni);
    }
    //Metodo para cargar los datos del cliente a editar//
    public final void loadClient(int dni) throws SQLException{
        Customer customer= con.getCustomer(dni);
        this.view.txtName.setText(customer.getClientName());
        this.view.txtLastName.setText(customer.getClientSurname());
        this.view.txtEmail.setText(customer.getEmail());
        this.view.jComboRS.setSelectedItem(customer.getSocialReason());
        this.view.jComboIVA.setSelectedItem(customer.getIvaCondition());
        this.view.jComboTCustomer1.setSelectedItem(customer.getClientType());
        this.view.jComboState.setSelectedItem(customer.getEstado());
    }
    //Metodo  para inicializar los listener con los botones//
    public final void initializeListeners(){
        this.view.btnEdit.addActionListener(this);
        this.view.btnExit.addActionListener(this);
    }
     //Metodo para abri la ventana//
   public void openCustomerEditView(){
       this.view.setVisible(true);
   }
   //Metodo para cerrar la ventana//
   public void closeCustomerEditView(){
       this.view.dispose();
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

            }
        } else if (fields[i] instanceof JComboBox) {
            JComboBox<?> comboBox = (JComboBox<?>) fields[i];
            if (comboBox.getSelectedItem() == null || comboBox.getSelectedItem().toString().trim().isEmpty()) {
                bugs.append("- El campo ").append(fieldNames[i]).append(" no puede estar vacío.\n");
                hasBugs = true;

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
     public final void setEstado() {
        
            List<String> condiciones = new ArrayList<>(Arrays.asList("alta","baja"));
            
            // Crear un modelo para el JComboBox
            DefaultComboBoxModel<String> model = 

                new DefaultComboBoxModel<>();

            for (String condicion : condiciones) {
                model.addElement(condicion); // Agregar el nombre de la cuenta
            }

            // Setear el modelo en el JComboBox
            view.jComboState.setModel(model); 
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
            view.jComboTCustomer1.setModel(model); 
    // Asegúrate de que addSeatView tenga cbbCuentas

    }
     //Metodo para agregar el cliente//
     public void buttonEditCustomer(ActionEvent e) throws SQLException{
         if(e.getSource()==view.btnEdit){
               Object[] fields = {
            view.txtName,
            view.txtLastName,
            view.txtEmail,
            view.jComboRS,
            view.jComboIVA,
            view.jComboTCustomer1,
            view.jComboState
        };
        String[] fieldNames = {
            "Nombre",
            "Apellido",
            "Email",
            "Razon Social",
            "Condicion IVA",
            "Tipo de Cliente",
            "Estado"
        };
          // Validar los campos
        if (validateFields(fields, fieldNames)) {
            int confirm = JOptionPane.showConfirmDialog(
                        null,
                        "¿Estás seguro de que deseas editar este Cliente?",
                        "Confirmación",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE
            );
             if(confirm == JOptionPane.YES_OPTION) { // Si el usuario confirma, intenta agregarlo//
                  con=new CustomerConnection();  
                  Customer customer= new Customer();
                    
                    String name = ((JTextField) fields[0]).getText().trim();
                    String surName= ((JTextField) fields[1]).getText().trim();
                    String email=((JTextField) fields[2]).getText().trim();
                    String razonSocial  = ((JComboBox<?>) fields[3]).getSelectedItem().toString().trim();
                    String  ivaCondition = ((JComboBox<?>) fields[4]).getSelectedItem().toString().trim();
                    String clientType=((JComboBox<?>) fields[5]).getSelectedItem().toString().trim();
                    String state= ((JComboBox<?>) fields[6]).getSelectedItem().toString().trim();
                    
                    customer.setClientName(name);
                    customer.setClientSurname(surName);
                    customer.setEmail(email);
                    customer.setDni(numberDNI);
                    customer.setSocialReason(razonSocial);
                    customer.setIvaCondition(ivaCondition);
                    customer.setClientType(clientType);
                    customer.setEstado(state);
                    
                   
                        boolean load=con.updateCustomer(customer);
           
                            if(load){
                                JOptionPane.showMessageDialog(
                                null,
                                "El cliente ha sido modificado exitosamente  \n",
                                 "Confirmacion",
                                 JOptionPane.INFORMATION_MESSAGE
                                );

                            }else{
                                JOptionPane.showMessageDialog(
                                null,
                                "El Cliente no ha podido ser modificado \n",
                                 "Error",
                                 JOptionPane.ERROR_MESSAGE
                                );

                            }
                    
                  }
            
            }
 
    }
            }

     public void buttonExit(ActionEvent e){
         if(e.getSource()==view.btnExit){
            closeCustomerEditView();
            clientManagement = new ClientManagement();
            clientManagement.openClientManagementView();
         }
     }
    @Override
    public void actionPerformed(ActionEvent e) {
        buttonExit(e);
        try {
            buttonEditCustomer(e);
        } catch (SQLException ex) {
            Logger.getLogger(CustomerEdit.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
