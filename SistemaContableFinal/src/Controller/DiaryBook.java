//Comportamiento de la vista LibroDiario
package Controller;

import Connection.AccountConnection;
import Connection.BooksConnection;

import Model.Account;
import Model.AccountSeat;
import Model.AccountSeatBook;
import Model.SingletonUser;
import View.DiaryBookView;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class DiaryBook implements ActionListener{
    private DiaryBookView libroDiarioView;
    private BooksConnection librosCon;
    private AccountConnection conexionCuentas;
    DefaultTableModel modelo = new DefaultTableModel();
    private MainMenu mainMenu;
    private SingletonUser currentUser=SingletonUser.getInstance();
    
    public DiaryBook(){
        this.libroDiarioView=new DiaryBookView();
        this.librosCon=new BooksConnection();
        this.conexionCuentas=new AccountConnection();
        iniciarTabla();
        this.libroDiarioView.setTitle("Ver Libro Diario"+" - "+currentUser.getUserName()+" ( "+currentUser.getRol().substring(0, 1).toUpperCase()+currentUser.getRol().substring(1).toLowerCase()+ " ) " );
        initializeListeners();
    }
    
    public void initializeListeners(){
         this.libroDiarioView.btnBuscar.addActionListener(this);
         this.libroDiarioView.btnSalir.addActionListener(this);
         this.libroDiarioView.btnPdfExport.addActionListener(this);

    }
    
    public void openDiaryBook(){
        this.libroDiarioView.setVisible(true);
    }
    
    public void closeDiaryBook(){
        this.libroDiarioView.dispose();
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
            ArrayList<AccountSeatBook> listaAsientos= librosCon.obtenerListaAsientos(fechaDesde, fechaHasta);
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
    
    public void actualizarTabla(ArrayList<AccountSeatBook> listaAsientos) throws IOException, SQLException, ClassNotFoundException{
         iniciarTabla();
          // Variable para rastrear la última fecha añadida
        Date ultimaFecha = null;
        int idAsiento=0;
        // Recorrer la lista de asientos contables
        String[] filaSeparadora = {"", "", "", ""};
        modelo.addRow(filaSeparadora);
        for(AccountSeatBook asiento : listaAsientos){
            // Obtener los detalles de cada Asiento_Cuenta
            for(AccountSeat asientoCuenta : asiento.getAccountSeats()){
                // Obtener los datos necesarios
                String[] datos= new String[4];
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
                modelo.addRow(datos);
                idAsiento=+1;
            }
            
        // Agregar fila separadora después de cada AsientoContable
        modelo.addRow(filaSeparadora);
        
        }
    }
    
    public String obtenerNombreCuenta(int idCuenta) throws IOException, SQLException, ClassNotFoundException{
         conexionCuentas =new AccountConnection();
         List<Account> arrayCuentas=conexionCuentas.getAccounts();
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
               mainMenu=new MainMenu();
               mainMenu.openMainMenuView();
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
