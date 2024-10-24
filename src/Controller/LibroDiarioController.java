//Comportamiento de la vista LibroDiario
package Controller;

import ConnectionsBD.AccountSeatConnection;
import ConnectionsBD.LibrosConnection;
import Model.Account;
import Model.AccountSeat;
import Model.User;
import View.LibroDiario;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class LibroDiarioController implements ActionListener{
    private LibroDiario libroDiarioView;
    private LibrosConnection librosCon;
    private AccountSeatConnection cuentasCon;
    DefaultTableModel modelo = new DefaultTableModel();
    private MainMenuController mainMenuController;
    private User currentUser=User.getInstancia();
    
    public LibroDiarioController(){
        this.libroDiarioView=new LibroDiario();
        this.librosCon=new LibrosConnection();
        this.cuentasCon=new AccountSeatConnection();
        this.libroDiarioView.setTitle("Ver Libro Diario"+" - "+currentUser.getUserName()+" ( "+currentUser.getRol().substring(0, 1).toUpperCase()+currentUser.getRol().substring(1).toLowerCase()+ " ) " );
        this.libroDiarioView.btnBuscar.addActionListener(this);
        this.libroDiarioView.btnSalir.addActionListener(this);
        iniciarTabla();
    }
    
    public void openLibroDiario(){
        this.libroDiarioView.setVisible(true);
    }
    
    public void btnBuscar(ActionEvent e) throws ClassNotFoundException, SQLException, IOException{
        if(e.getSource()==libroDiarioView.btnBuscar){
        try{
            // Obtener las fechas seleccionadas de los DateChooser
            Date fechaDesde = libroDiarioView.jDateChooserDesde.getDate();
            Date fechaHasta = libroDiarioView.jDateChooserHasta.getDate();
            
             // Validar que ambas fechas estén seleccionadas
            if (fechaDesde == null || fechaHasta == null) {
                JOptionPane.showMessageDialog(null, "Por favor, selecciona ambas fechas.");
                libroDiarioView.jDateChooserDesde.setDate(null);
                libroDiarioView.jDateChooserHasta.setDate(null);
                return;
            }
            // Validar que la fecha hasta sea mayor o igual que la fecha desde
            if (fechaHasta.before(fechaDesde)) {
                JOptionPane.showMessageDialog(null, "Error: La fecha hasta debe ser mayor o igual a la fecha desde.");
                libroDiarioView.jDateChooserDesde.setDate(null);
                libroDiarioView.jDateChooserHasta.setDate(null); 
            return;
        }
           // Obtener la lista de asientos contables entre las fechas seleccionadas
            ArrayList<AccountSeatController> listaAsientos= librosCon.obtenerListaAsientos(fechaDesde, fechaHasta);
            // Actualizar la tabla con los resultados
            actualizarTabla(listaAsientos);
             libroDiarioView.jDateChooserDesde.setDate(null);
             libroDiarioView.jDateChooserHasta.setDate(null);
            
        }catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Ocurrió un error al buscar los asientos.");
        }
    }
    }
    
    public void actualizarTabla(ArrayList<AccountSeatController> listaAsientos) throws IOException, SQLException, ClassNotFoundException{
         iniciarTabla();
          // Variable para rastrear la última fecha añadida
        Date ultimaFecha = null;
        // Recorrer la lista de asientos contables
        String[] filaSeparadora = {"", "", "", ""};
        modelo.addRow(filaSeparadora);
        for(AccountSeatController asiento : listaAsientos){
            // Obtener los detalles de cada Asiento_Cuenta
            for(AccountSeat asientoCuenta : asiento.getAccountSeats()){
                // Obtener los datos necesarios
                String[] datos= new String[4];
                 // Verificar si la fecha es diferente a la última fecha añadida
                 if (ultimaFecha == null || !asiento.getSeat().getDate().equals(ultimaFecha)) {
                        datos[0] = asiento.getSeat().getDate().toString(); // Solo se añade si es la primera vez
                        ultimaFecha = asiento.getSeat().getDate(); // Actualizar la última fecha
                   } else {
                        datos[0] = ""; // Dejar vacía si es la misma fecha
                     }
                datos[1]=obtenerNombreCuenta(asientoCuenta.getIdAccount());
                if(asientoCuenta.getType().toUpperCase().equals("HABER")){
                    datos[3]=String.valueOf(asientoCuenta.getAmount());
                }else{
                    datos[2]=String.valueOf(asientoCuenta.getAmount());
                }
                modelo.addRow(datos);
            }
        // Agregar fila separadora después de cada AsientoContable
        modelo.addRow(filaSeparadora);
        }
    }
    
    public String obtenerNombreCuenta(int idCuenta) throws IOException, SQLException, ClassNotFoundException{
         AccountSeatConnection conexionCuentas =new AccountSeatConnection();
         ArrayList<Account> arrayCuentas=conexionCuentas.getAccount();
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
                if (columna == 1 && columna == 2 && columna == 3) {
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
        
        libroDiarioView.jTableDiario.setRowHeight(15);
        libroDiarioView.jTableDiario.setModel(modelo);
        libroDiarioView.jTableDiario.setRowHeight(25);
        
        libroDiarioView.jTableDiario.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            // Verificar si la fila es una fila separadora (todos los valores son vacíos)
            boolean esFilaSeparadora = modelo.getValueAt(row, 0).equals("") && 
                                       modelo.getValueAt(row, 1).equals("") && 
                                       modelo.getValueAt(row, 2).equals("") && 
                                       modelo.getValueAt(row, 3).equals("");

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
         libroDiarioView.jDateChooserDesde.setDate(null);
         libroDiarioView.jDateChooserHasta.setDate(null);
         iniciarTabla();
        }
       
       public void buttonBack(ActionEvent e){
           if(e.getSource()==libroDiarioView.btnSalir){
               this.libroDiarioView.dispose();
               mainMenuController=new MainMenuController();
               mainMenuController.openMainMenuView();
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
            Logger.getLogger(LibroDiarioController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(LibroDiarioController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(LibroDiarioController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
           
    }
    
}
