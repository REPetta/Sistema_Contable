
package Controller;

import Connection.ItemConnection;
import Model.Item;
import Model.SingletonUser;
import View.ManagementStockView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

public class ManagementStock implements ActionListener {
    
    private final SingletonUser currentUser= SingletonUser.getInstance();
    private ItemsManagement itemsManagement;
    private final ManagementStockView view;
    private ItemConnection itemsCon;

    public ManagementStock() {
        view=new ManagementStockView();
        this.view.setTitle("Gestionar Articulos"+"-"+currentUser.getUserName().toUpperCase()+"("+currentUser.getRol()+")");
        setCombosBox();
        initializeListeners();
    }
    //Metodo para los listeners//
    public final void initializeListeners(){
            this.view.btnBack.addActionListener(this);
            this.view.btnDel.addActionListener(this);
            this.view.btnAddStock.addActionListener(this);
    }
      //Metodo para abri la ventana//
   public void openManagementStockView(){
       this.view.setVisible(true);
   }
   //Metodo para cerrar la ventana//
   public void closeManagementStockView(){
       this.view.dispose();
   }
//Metodo para inicializar el comboBox//
   public final void setCombosBox(){
       try {
      
            List<Item> items= items(); 

            // Crear un modelo para el JComboBox
            DefaultComboBoxModel<String> model = 

                new DefaultComboBoxModel<>();

            // Llenar el modelo con los nombres de las cuentas
            model.addElement("");
            for (Item item : items) {
                model.addElement(item.getItemName()+"-"+item.getItemCode()); // Agregar el nombre de la cuenta
            }

            // Setear el modelo en el JComboBox
            view.jComboItems.setModel(model); 
    // Asegúrate de que addSeatView tenga cbbCuentas
        } catch (IOException | SQLException | ClassNotFoundException e) {
            e.printStackTrace(); // Manejar la excepción según sea necesario
        }
   }
       //inicializacion para combobox de cuentas//
    public List<Item> items () throws IOException,  ClassNotFoundException,   SQLException{
        itemsCon =new ItemConnection();
        return itemsCon.getItems();
     }
    //Metodo para los mensajes del dar de baja cliente//
    public void delSuccessful(boolean successful){
        if(successful){
              JOptionPane.showMessageDialog(
                                null,
                                "El Articulo ha sido dado de baja exitosamente  \n",
                                 "Confirmacion",
                                 JOptionPane.INFORMATION_MESSAGE
                                );
              setCombosBox();
                            }else{
                                JOptionPane.showMessageDialog(
                                null,
                                "El Articulo no ha podido ser dado de baja exitosamente \n",
                                 "Error",
                                 JOptionPane.ERROR_MESSAGE
                                );
                  setCombosBox();
          }
    }
     //Metodo para los mensajes del dar de baja cliente//
    public void increaseSuccessful(boolean successful){
        if(successful){
              JOptionPane.showMessageDialog(
                                null,
                                "El Stock ha sido dado aumentado correctamente  \n",
                                 "Confirmacion",
                                 JOptionPane.INFORMATION_MESSAGE
                                );
              setCombosBox();
                            }else{
                                JOptionPane.showMessageDialog(
                                null,
                                "El Stock  no ha podido ser aumentado \n",
                                 "Error",
                                 JOptionPane.ERROR_MESSAGE
                                );
                  setCombosBox();
          }
    }
     //Metodo para obtener el dni del cliente//
public int getItemCodeSelected(){
    String selectedItem = (String) view.jComboItems.getSelectedItem();
    int itemCode=0;
if (selectedItem != null && !selectedItem.isEmpty()) {
    String[] parts = selectedItem.split("-");
    
    if (parts.length == 2) { // Nos aseguramos de que el formato es correcto
        String dni = parts[1].trim();  // Obtener el DNI
        itemCode = Integer.parseInt(dni);
        
    } else {
        JOptionPane.showMessageDialog(null, "Formato inválido en la selección del cliente.", "Error", JOptionPane.ERROR_MESSAGE);
    }
}else{
    JOptionPane.showMessageDialog(null, "No se puede seleccionar una valor vacio", "Error", JOptionPane.ERROR_MESSAGE);
    }
        return itemCode;
}
//Metodo para aumentar el stock//
public int newNumberStock(){
    // Pedir al usuario que ingrese un número
        String input = JOptionPane.showInputDialog("Ingrese un número:");
        if (input == null || input.trim().isEmpty()) {
            return 0; // Puedes devolver un valor especial (-1) para indicar que se canceló
        }
        // Convertir el String a un número entero
        int numero;
        try {
             numero = Integer.parseInt(input);
            if(numero<0 || numero>10000){
                JOptionPane.showMessageDialog(null, "Por favor, ingrese un número válido.");
                return 0;
            }
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Por favor, ingrese un número válido.");
            return 0;
        }
        return numero;
    }

    //Metodo para salir//
    public void buttonExit(ActionEvent e){
        if(e.getSource()==view.btnBack){
            closeManagementStockView();
            itemsManagement= new ItemsManagement();
            itemsManagement.openItemsManagementView();
        }
    }
    ////Metodo para dar de baja a un cliente//
    public void buttonDeleteItem(ActionEvent e) throws SQLException{
        if(e.getSource()==view.btnDel){
          int itemCode= getItemCodeSelected();
            if(itemCode==0){
                setCombosBox();
                return;
            }
           int confirm = JOptionPane.showConfirmDialog(
                        null,
                        "¿Estás seguro de que deseas dar de baja a este Articulo?",
                        "Confirmación",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE
            );
                  if(confirm == JOptionPane.YES_OPTION) { // Si el usuario confirma, intenta agregarlo//
                        itemsCon =new ItemConnection();
                        boolean update=itemsCon.desactivateItem(itemCode);
                        delSuccessful(update);
                    }
        }
    }
    //Metodo para aumentas el Stock//
    public void buttonIncreeseStock(ActionEvent e) throws SQLException{
        if(e.getSource()==view.btnAddStock){
            int itemCode= getItemCodeSelected();
            if(itemCode==0){
                setCombosBox();
                return;
            }
            int newStock=newNumberStock();
            if(newStock==0){
                return;
            }
                itemsCon =new ItemConnection();
                boolean update=itemsCon.increaseStock(itemCode,newStock);
                increaseSuccessful(update);
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        buttonExit(e);
        try {
            buttonDeleteItem(e);
        } catch (SQLException ex) {
            Logger.getLogger(ManagementStock.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            buttonIncreeseStock(e);
        } catch (SQLException ex) {
            Logger.getLogger(ManagementStock.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
}
