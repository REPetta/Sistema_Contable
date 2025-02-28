
package Controller;

import Connection.SalesConnection;
import Model.SaleType;
import Model.SingletonUser;
import View.ManagementPaymentView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;


public class ManagementPayment implements ActionListener{
    private final SingletonUser currentUser= SingletonUser.getInstance();
    private PaymentManagement payMen;
    private final ManagementPaymentView view;
    private EditPaymentMethod edit;
    private SalesConnection salesCon;
    
    public ManagementPayment() {
        view= new ManagementPaymentView();
        this.view.setTitle("Gestion de Metodos"+"-"+currentUser.getUserName().toUpperCase()+"("+currentUser.getRol()+")");
        setMethodBox();
        initializeListeners();
    }
    //Metodo  para inicializar los listener con los botones//
    public final void initializeListeners(){
        this.view.btnBack.addActionListener(this);
        this.view.btnEdit.addActionListener(this);

    }
     //Metodo para abri la ventana//
   public void openView(){
       this.view.setVisible(true);
   }
   //Metodo para cerrar la ventana//
   public void closeView(){
       this.view.dispose();
   }
   //inicializacion para combobox de cuentas//
    public List<SaleType> methods () throws IOException,  ClassNotFoundException,   SQLException{
        salesCon =new SalesConnection();
        return salesCon.getSalesTypes();
     }
    //Metodo para cargar los metodos de pago/
public final void setMethodBox() {
        try {
      
            List<SaleType> metodos= methods(); 

            // Crear un modelo para el JComboBox
            DefaultComboBoxModel<String> model = 

                new DefaultComboBoxModel<>();

            // Llenar el modelo con los nombres de las cuentas
            model.addElement("");
            for (SaleType method : metodos) {
                model.addElement(method.getSaleDescription()+"-"+method.getCode()); // Agregar el nombre de la cuenta
            }

            // Setear el modelo en el JComboBox
            view.jComboItems.setModel(model); 
    // Asegúrate de que addSeatView tenga cbbCuentas
        } catch (IOException | SQLException | ClassNotFoundException e) {
            e.printStackTrace(); // Manejar la excepción según sea necesario
        }
    }
   //Metodo para obtener el dni del cliente//
public int getCodeSelected(){
    String selectedItem = (String) view.jComboItems.getSelectedItem();
    int methodCode=0;
if (selectedItem != null && !selectedItem.isEmpty()) {
    String[] parts = selectedItem.split("-");
    
    if (parts.length == 2) { // Nos aseguramos de que el formato es correcto
        String code = parts[1].trim();  // Obtener el DNI
        methodCode = Integer.parseInt(code);
        
    } else {
        JOptionPane.showMessageDialog(null, "Formato inválido en la selección del metodo.", "Error", JOptionPane.ERROR_MESSAGE);
    }
}else{
    JOptionPane.showMessageDialog(null, "No se puede seleccionar una valor vacio", "Error", JOptionPane.ERROR_MESSAGE);
    }
        return methodCode;
}
//Metodo para el modificar//
   public void buttonEditMethod(ActionEvent e) throws SQLException, SQLException, SQLException, SQLException{
        if(e.getSource()==view.btnEdit){
            
            int code= getCodeSelected();
            if(code==0){
                setMethodBox();
                return;
            }
            closeView();
            edit=new EditPaymentMethod(code);
            edit.openView();
        }
    }

   //Metodo para salir
    public void buttonExit(ActionEvent e){
        if(e.getSource()==view.btnBack){
           closeView();
           payMen=new PaymentManagement();
           payMen.openView();
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            buttonEditMethod(e);
        } catch (SQLException ex) {
            Logger.getLogger(ManagementPayment.class.getName()).log(Level.SEVERE, null, ex);
        }
        buttonExit(e);
    }
}
