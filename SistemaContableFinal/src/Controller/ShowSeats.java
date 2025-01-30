/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Connection.AccountConnection;
import Connection.BooksConnection;
import Connection.UserConnection;
import Controller.MainMenu;
import Model.Account;
import Model.AccountSeat;
import Model.AccountSeatBook;
import Model.SingletonUser;
import Model.User;
import View.DiaryBookView;
import View.LedgerView;
import View.SeatsView;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Rodrigo
 */
public class ShowSeats implements ActionListener{
    private SeatsView seatViews;
    private BooksConnection librosCon;
    private AccountConnection cuentasCon;
    private UserConnection userCon;
    DefaultTableModel modelo = new DefaultTableModel();
    private MainMenu mainMenuController;
    private SingletonUser currentUser=SingletonUser.getInstance();
    
    
    public ShowSeats(){
        
        this.seatViews=new SeatsView();
        this.librosCon=new BooksConnection();
        this.cuentasCon=new AccountConnection();
        this.seatViews.setTitle("Ver Libro Diario"+" - "+currentUser.getUserName()+" ( "+currentUser.getRol().substring(0, 1).toUpperCase()+currentUser.getRol().substring(1).toLowerCase()+ " ) " );
        initializeListeners();
        setUsuarioComboBox();
        iniciarTabla();
    }
    
    public void initializeListeners(){
         this.seatViews.btnBuscar.addActionListener(this);
         this.seatViews.btnSalir.addActionListener(this);
         
    }
    
    public void openSeatsView(){
        this.seatViews.setVisible(true);
    }
    
    public void closeSeatsView(){
        this.seatViews.dispose();
    }
    
    public void btnBuscar(ActionEvent e) throws ClassNotFoundException, SQLException, IOException{
        if(e.getSource()==seatViews.btnBuscar){
        try{
            // Obtener las fechas seleccionadas de los DateChooser
            Date fechaDesde = seatViews.jDateChooserDesde.getDate();
            Date fechaHasta = seatViews.jDateChooserHasta.getDate();
            
             // Validar que ambas fechas estén seleccionadas
            if (fechaDesde == null || fechaHasta == null) {
                JOptionPane.showMessageDialog(null, "Por favor, selecciona ambas fechas.");
                seatViews.jDateChooserDesde.setDate(null);
                seatViews.jDateChooserHasta.setDate(null);
                return;
            }
            // Validar que la fecha hasta sea mayor o igual que la fecha desde
            if (fechaHasta.before(fechaDesde)) {
                JOptionPane.showMessageDialog(null, "Error: La fecha hasta debe ser mayor o igual a la fecha desde.");
                seatViews.jDateChooserDesde.setDate(null);
                seatViews.jDateChooserHasta.setDate(null); 
            return;
        }
           // Obtener la lista de asientos contables entre las fechas seleccionadas
            ArrayList<AccountSeatBook> listaAsientos= librosCon.obtenerListaAsientos(fechaDesde, fechaHasta);
            // Actualizar la tabla con los resultados
            actualizarTabla(listaAsientos);
             seatViews.jDateChooserDesde.setDate(null);
             seatViews.jDateChooserHasta.setDate(null);
            
        }catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Ocurrió un error al buscar los asientos.");
        }
    }
    }
    
    public void actualizarTabla(ArrayList<AccountSeatBook> listaAsientos) throws IOException, SQLException, ClassNotFoundException{
         iniciarTabla();
          // Variable para rastrear la última fecha añadida
        Date ultimaFecha = null;
        String ultimoUserName=null;
        int idAsiento=0;
        // Recorrer la lista de asientos contables
        String[] filaSeparadora = {"", "", "", "",""};
        modelo.addRow(filaSeparadora);
        if(this.seatViews.jComboBoxUser.getSelectedItem().equals("")){
            for(AccountSeatBook asiento : listaAsientos){
                // Obtener los detalles de cada Asiento_Cuenta
                for(AccountSeat asientoCuenta : asiento.getAccountSeats()){
                    // Obtener los datos necesarios
                    String[] datos= new String[5];
                    // Verificar si la fecha es diferente a la última fecha añadida
                    if (ultimaFecha == null || !asiento.getSeat().getSeatDate().equals(ultimaFecha) || asiento.getSeat().getIdSeat()!=idAsiento) {
                        datos[0] = asiento.getSeat().getSeatDate().toString(); // Solo se añade si es la primera vez
                        ultimaFecha = asiento.getSeat().getSeatDate(); // Actualizar la última fecha
                    } else {
                        datos[0] = ""; // Dejar vacía si es la misma fecha
                         }
                    datos[1]=obtenerNombreCuenta(asientoCuenta.getIdCuenta());
                    if(asientoCuenta.getDestiny().toUpperCase().equals("HABER")){
                        datos[3]="$"+String.valueOf(asientoCuenta.getAmount());
                    }else{
                        datos[2]="$"+String.valueOf(asientoCuenta.getAmount());
                    }
                    if(ultimoUserName==null || !asiento.getSeat().getUserName().equalsIgnoreCase(ultimoUserName) || asiento.getSeat().getIdSeat()!=idAsiento){
                        datos[4]=asiento.getSeat().getUserName();
                        }
                    else{
                        datos[4]="";
                    }
                    modelo.addRow(datos);
                    ultimoUserName=asiento.getSeat().getUserName();
                    idAsiento=asiento.getSeat().getIdSeat();
                }
            
            // Agregar fila separadora después de cada AsientoContable
            modelo.addRow(filaSeparadora);
            }
            this.seatViews.jComboBoxUser.setSelectedIndex(0);
        }
        else{
             for(AccountSeatBook asiento : listaAsientos){
                  // Obtener los detalles de cada Asiento_Cuenta
                  if(this.seatViews.jComboBoxUser.getSelectedItem().equals(asiento.getSeat().getUserName())){
                    for(AccountSeat asientoCuenta : asiento.getAccountSeats()){
                         // Obtener los datos necesarios
                        String[] datos= new String[5];
                        // Verificar si la fecha es diferente a la última fecha añadida
                         if (ultimaFecha == null || !asiento.getSeat().getSeatDate().equals(ultimaFecha) || asiento.getSeat().getIdSeat()!=idAsiento) {
                            datos[0] = asiento.getSeat().getSeatDate().toString(); // Solo se añade si es la primera vez
                            ultimaFecha = asiento.getSeat().getSeatDate(); // Actualizar la última fecha
                        } else {
                            datos[0] = ""; // Dejar vacía si es la misma fecha
                            }
                        datos[1]=obtenerNombreCuenta(asientoCuenta.getIdCuenta());
                        if(asientoCuenta.getDestiny().toUpperCase().equals("HABER")){
                            datos[3]="$"+String.valueOf(asientoCuenta.getAmount());
                        }else{
                            datos[2]="$"+String.valueOf(asientoCuenta.getAmount());
                         }
                        if(ultimoUserName==null || !asiento.getSeat().getUserName().equalsIgnoreCase(ultimoUserName) || asiento.getSeat().getIdSeat()!=idAsiento){
                            datos[4]=asiento.getSeat().getUserName();
                            }
                        else{
                         datos[4]="";
                        }
                        modelo.addRow(datos);
                        ultimoUserName=asiento.getSeat().getUserName();
                        idAsiento=asiento.getSeat().getIdSeat();
                    }
                }
                  // Agregar fila separadora después de cada AsientoContable
                  if(asiento.getSeat().getUserName().equals(this.seatViews.jComboBoxUser.getSelectedItem())){
                      modelo.addRow(filaSeparadora);
                  }
                  
             }
                         this.seatViews.jComboBoxUser.setSelectedIndex(0);

        }
    }
    
    public String obtenerNombreCuenta(int idCuenta) throws IOException, SQLException, ClassNotFoundException{
         cuentasCon =new AccountConnection();
         List<Account> arrayCuentas=cuentasCon.getAccounts();
         for(Account cuenta : arrayCuentas  ){
             // Si la cuenta es nula, salta a la siguiente iteración             
             if(cuenta==null){
                 continue;
             }
             if(cuenta.getIdAccount()==idCuenta){
                 return cuenta.getAccountName();
             }
         
         }
        return null;
    }
    
    public void iniciarTabla() {
        
        modelo = new DefaultTableModel() {
            public boolean isCellEditable(int fila, int columna) {
                if (columna == 1 && columna == 2 && columna == 3 && columna==4) {
                    return true;
                } else {
                    return false;
                }
            }
        };
        
        
        modelo.addColumn("Fecha");
        modelo.addColumn("Cuenta");
        modelo.addColumn("Debe");
        modelo.addColumn("Haber");
        modelo.addColumn("Creador");
       
        seatViews.jTableSeats.setRowHeight(15);
        seatViews.jTableSeats.setModel(modelo);
        seatViews.jTableSeats.setRowHeight(25);
        
        seatViews.jTableSeats.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            // Verificar si la fila es una fila separadora (todos los valores son vacíos)
            boolean esFilaSeparadora = modelo.getValueAt(row, 0).equals("") && 
                                       modelo.getValueAt(row, 1).equals("") && 
                                       modelo.getValueAt(row, 2).equals("") &&
                                       modelo.getValueAt(row, 3).equals("") && 
                                       modelo.getValueAt(row, 4).equals("");

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
    
       public void limpiarVista() {
         seatViews.jDateChooserDesde.setDate(null);
         seatViews.jDateChooserHasta.setDate(null);
         iniciarTabla();
        }
       
       public void buttonBack(ActionEvent e){
           if(e.getSource()==seatViews.btnSalir){
               this.seatViews.dispose();
               mainMenuController=new MainMenu();
               mainMenuController.openMainMenuView();
           }
       }

      //inicializacion para combobox de cuentas//
    public List<User> usuarios () throws IOException,  ClassNotFoundException,   SQLException{
        userCon =new UserConnection();
        return userCon.getUsers();
     }
   
    public void setUsuarioComboBox() {
        try {
            // Obtener la lista de cuentas
            List<User> usuarios = usuarios(); // Asegúrate de que este método esté disponible en el contexto

            // Crear un modelo para el JComboBox
            DefaultComboBoxModel<String> model = 

                new DefaultComboBoxModel<>();

            // Llenar el modelo con los nombres de las cuentas
            model.addElement("");
            for (User usuario : usuarios) {
                model.addElement(usuario.getUserName()); // Agregar el nombre de la cuenta
            }

            // Setear el modelo en el JComboBox
            seatViews.jComboBoxUser.setModel(model); 
    // Asegúrate de que addSeatView tenga cbbCuentas
        } catch (IOException | SQLException | ClassNotFoundException e) {
            e.printStackTrace(); // Manejar la excepción según sea necesario
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
         try{   
            buttonBack(e);
            }catch(Exception ex){
                ex.printStackTrace();
            }
        try {
            btnBuscar(e);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DiaryBook.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DiaryBook.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DiaryBook.class.getName()).log(Level.SEVERE, null, ex);
        }
        
           
    }
    
}
    