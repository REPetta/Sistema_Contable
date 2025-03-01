
package Controller;

import Connection.CustomerConnection;
import Connection.ItemConnection;
import Connection.SalesConnection;
import Model.BillNode;
import Model.Customer;
import Model.Item;
import Model.SingletonUser;
import View.BillManagementView;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

public class BillManagement implements ActionListener {
    
    private SingletonUser currentUser=SingletonUser.getInstance();
    DefaultTableModel modelo = new DefaultTableModel();
    private SalesConnection salesCon;
    private BillManagementView view;
    private SalesSystem salesSystem;
    private ItemConnection itemsCon;
    private CustomerConnection clientesCon;
    
    public BillManagement() {
        this.view=new BillManagementView();
        this.salesCon=new SalesConnection();
        setClientesBox();
        setItemBox();
        initializeListeners();
        iniciarTabla();
    }
 public void initializeListeners(){
        this.view.btnBuscar.addActionListener(this);
        this.view.btnSalir.addActionListener(this);
    }
    public void openView(){
        this.view.setVisible(true);
    }
    public void closeView(){
        this.view.dispose();
        }
     //inicializacion para combobox de cuentas//
    public List<Customer> clientes () throws IOException,  ClassNotFoundException,   SQLException{
        clientesCon =new CustomerConnection();
        return clientesCon.getCustomers();
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
            view.comboCustomer.setModel(model); 
    // Asegúrate de que addSeatView tenga cbbCuentas
        } catch (IOException | SQLException | ClassNotFoundException e) {
            e.printStackTrace(); // Manejar la excepción según sea necesario
        }
    }
//Metodo para obtener el dni del cliente//
public int getDniCustomerSelected(){
    String selectedItem = (String) view.comboCustomer.getSelectedItem();
    int dniCliente=-1;
if (selectedItem != null && !selectedItem.isEmpty()) {
    String[] parts = selectedItem.split("-");
    
    if (parts.length == 2) { // Nos aseguramos de que el formato es correcto
        String dni = parts[1].trim();  // Obtener el DNI
        dniCliente = Integer.parseInt(dni);
    }
    return dniCliente;
}
       return dniCliente;
}
//Metodo para inicializar el comboBox//
   public final void setItemBox(){
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
            view.comboItem.setModel(model); 
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
    //Metodo para obtener el dni del cliente//
public int getItemCodeSelected(){
    String selectedItem = (String) view.comboItem.getSelectedItem();
    int itemCode=-1;
if (selectedItem != null && !selectedItem.isEmpty()) {
    String[] parts = selectedItem.split("-");
    
    if (parts.length == 2) { // Nos aseguramos de que el formato es correcto
        String dni = parts[1].trim();  // Obtener el DNI
        itemCode = Integer.parseInt(dni); 
    }
    return itemCode;
}
        return itemCode;
}
    //Metodo para el boton buscar//
      public void buttonSearch(ActionEvent e) throws ClassNotFoundException, SQLException, IOException{
        if(e.getSource()==view.btnBuscar){
        try{
            // Obtener las fechas seleccionadas de los DateChooser
            java.util.Date fecha = view.jDateChooserDate.getDate();
            if (fecha != null) {
                            // Convierte java.util.Date a java.sql.Date
                             java.sql.Date sqlDate = new java.sql.Date(fecha.getTime());
                                  int selectedCode = getItemCodeSelected();
                                 int selectedDni=getDniCustomerSelected();
                                 // Obtener la lista de asientos contables entre las fechas seleccionadas
                                 List<BillNode> listaFacturas= salesCon.getBills(sqlDate,selectedCode,selectedDni);
                                  // Actualizar la tabla con los resultados
                                  actulizarTabla(listaFacturas);
                                  limpiarTabla();
                } else {
                JOptionPane.showMessageDialog(null, "La fecha seleccionada es nula.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            }
 
        }catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Ocurrió un error al buscar los asientos.");
        }
    }
    }
      //Metodo para limpiar la tabla//
      public void limpiarTabla(){
             view.jDateChooserDate.setDate(null);
             view.comboItem.setSelectedIndex(0);
             view.comboCustomer.setSelectedIndex(0);
      }
      //Metodo para actualizarTabla//
    public void actulizarTabla(List<BillNode> listaFacturas) throws IOException, SQLException, ClassNotFoundException{
          iniciarTabla();
        
        // Recorrer la lista de asientos contables
        String[] filaSeparadora = {"", "", "", "",""};
        modelo.addRow(filaSeparadora);
        
        for(BillNode factura : listaFacturas){
            String[] datos= new String[5];
            datos[0]=String.valueOf(factura.getBill().getBillNumber());
            datos[1]="$"+String.valueOf(factura.getBill().getBillTotal());
            datos[2]=String.valueOf(factura.getBill().getBillType());
            datos[3]=String.valueOf(factura.getCustomerName());
            for(String item : factura.getItems()){
                        if(datos[4].isEmpty()){
                                datos[4]=item;
                         }else{
                            datos[4]=","+item;
                        }
                        }
            modelo.addRow(datos);
                    }
        modelo.addRow(filaSeparadora);
     }
    //Metodo para iniciar tabla//
    public void iniciarTabla(){
         modelo = new DefaultTableModel() {
            public boolean isCellEditable(int fila, int columna) {
                if (columna == 1 && columna == 2 && columna == 3) {
                    return true;
                } else {
                    return false;
                }
            }
        };
        modelo.addColumn("Numero de Factura");
        modelo.addColumn("Total Factura");
        modelo.addColumn("Tipo de Factura");
        modelo.addColumn("Nombre de Cliente");
        modelo.addColumn("Productos");
        
        view.jTableMayor.setRowHeight(15);
        view.jTableMayor.setModel(modelo);
        view.jTableMayor.setRowHeight(25);
        
        view.jTableMayor.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            // Verificar si la fila es una fila separadora (todos los valores son vacíos)
            boolean esFilaSeparadora = 
                                       (modelo.getValueAt(row, 0) == null || modelo.getValueAt(row, 0).toString().isEmpty()) &&
                                       (modelo.getValueAt(row, 1) == null || modelo.getValueAt(row, 1).toString().isEmpty()) &&
                                       (modelo.getValueAt(row, 2) == null || modelo.getValueAt(row, 2).toString().isEmpty()) &&
                                       (modelo.getValueAt(row, 3) == null || modelo.getValueAt(row, 3).toString().isEmpty()) &&
                                       (modelo.getValueAt(row, 4) == null || modelo.getValueAt(row, 4).toString().isEmpty());
 

            if (esFilaSeparadora) {
                cell.setBackground(Color.BLACK);
                cell.setForeground(Color.WHITE); // Texto blanco para contraste
            } else {
                // Restaurar color normal para las demás filas
                cell.setBackground(Color.WHITE);
                cell.setForeground(Color.BLACK);
            }

            return cell;
        }
    });
     }
     

    public void buttonBack(ActionEvent e){//Metodo que le da al boton volver la accion de salir de la ventana Agregar Asiento y volver al Menu Principal//
       if(e.getSource()==view.btnSalir){
           closeView();
           salesSystem=new SalesSystem();
           salesSystem.openSalesSystemView();
       }
   }
    @Override
    public void actionPerformed(ActionEvent e) {
        buttonBack(e);
        try {
            buttonSearch(e);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(BillManagement.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(BillManagement.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(BillManagement.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
