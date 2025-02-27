
package Controller;

import Connection.CustomerConnection;
import Model.Customer;
import Model.SingletonUser;
import View.ClientManagementView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class ClientManagement implements ActionListener {
    
    private final SingletonUser currentUser= SingletonUser.getInstance();
    private final ClientManagementView view;
    private CustomerManagement customerManagement;
    private CustomerConnection clientesCon;
    private CustomerEdit customerEdit;
    
    public ClientManagement(){
        view=new ClientManagementView();
        this.view.setTitle("Gestionar Clientes"+"-"+currentUser.getUserName().toUpperCase()+"("+currentUser.getRol()+")");
        setClientesBox();
        initializeListeners();
    }
    
    public final void initializeListeners(){
        this.view.btnEdit.addActionListener(this);
        this.view.btnDel.addActionListener(this);
        this.view.btnBack.addActionListener(this);
    }
       //Metodo para abri la ventana//
   public void openClientManagementView(){
       this.view.setVisible(true);
   }
   //Metodo para cerrar la ventana//
   public void closeClientManagementView(){
       this.view.dispose();
   }
    
   //Metodo para cargar los clientes//
public final void setClientesBox() {
        try {
      
            List<Customer> clientes= clientes(); 

            // Crear un modelo para el JComboBox
            DefaultComboBoxModel<String> model = 

                new DefaultComboBoxModel<>();

            // Llenar el modelo con los nombres de las cuentas
            model.addElement("");
            for (Customer cliente : clientes) {
                model.addElement(cliente.getClientName()+"-"+cliente.getDni()); // Agregar el nombre de la cuenta
            }

            // Setear el modelo en el JComboBox
            view.jComboCustomer.setModel(model); 
    // Asegúrate de que addSeatView tenga cbbCuentas
        } catch (IOException | SQLException | ClassNotFoundException e) {
            e.printStackTrace(); // Manejar la excepción según sea necesario
        }
    }

 //Metodo para obtener el dni del cliente//
public int getDniCustomerSelected(){
    String selectedItem = (String) view.jComboCustomer.getSelectedItem();
    int dniCliente=0;
if (selectedItem != null && !selectedItem.isEmpty()) {
    String[] parts = selectedItem.split("-");
    
    if (parts.length == 2) { // Nos aseguramos de que el formato es correcto
        String dni = parts[1].trim();  // Obtener el DNI
        dniCliente = Integer.parseInt(dni);
        
    } else {
        JOptionPane.showMessageDialog(null, "Formato inválido en la selección del cliente.", "Error", JOptionPane.ERROR_MESSAGE);
    }
}else{
    JOptionPane.showMessageDialog(null, "No se puede seleccionar una valor vacio", "Error", JOptionPane.ERROR_MESSAGE);
    }
        return dniCliente;
}
      //inicializacion para combobox de cuentas//
    public List<Customer> clientes () throws IOException,  ClassNotFoundException,   SQLException{
        clientesCon =new CustomerConnection();
        return clientesCon.getCustomers();
     }
    //Metodo para los mensajes del dar de baja cliente//
    public void delSuccessful(boolean successful){
        if(successful){
              JOptionPane.showMessageDialog(
                                null,
                                "El cliente ha sido dado de baja exitosamente  \n",
                                 "Confirmacion",
                                 JOptionPane.INFORMATION_MESSAGE
                                );
              setClientesBox();
                            }else{
                                JOptionPane.showMessageDialog(
                                null,
                                "El Cliente no ha podido ser dado de baja exitosamente \n",
                                 "Error",
                                 JOptionPane.ERROR_MESSAGE
                                );
                  setClientesBox();
          }
    }
    public void buttonEditCustomer(ActionEvent e) throws SQLException, SQLException, SQLException, SQLException{
        if(e.getSource()==view.btnEdit){
            
            int dni= getDniCustomerSelected();
            if(dni==0){
                setClientesBox();
                return;
            }
            closeClientManagementView();
            customerEdit=new CustomerEdit(dni);
            customerEdit.openCustomerEditView();
        }
    }
    //Metodo para dar de baja a un cliente//
    public void buttonDeleteCustomer(ActionEvent e) throws SQLException{
        if(e.getSource()==view.btnDel){
          int dni= getDniCustomerSelected();
            if(dni==0){
                setClientesBox();
                return;
            }
          clientesCon =new CustomerConnection();
          boolean update=clientesCon.desactivateCustomer(dni);
          delSuccessful(update);
        }
    }
    //Metodo para salir//
    public void buttonExit(ActionEvent e){
        if(e.getSource()==view.btnBack){
            closeClientManagementView();
            customerManagement= new CustomerManagement();
            customerManagement.openCustomerManagementView();
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        buttonExit(e);
        try {
            buttonEditCustomer(e);
        } catch (SQLException ex) {
            Logger.getLogger(ClientManagement.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            buttonDeleteCustomer(e);
        } catch (SQLException ex) {
            Logger.getLogger(ClientManagement.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
