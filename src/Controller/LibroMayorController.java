//Comportamiento de la vista LibroMayor//
package Controller;

import ConnectionsBD.AccountSeatConnection;
import ConnectionsBD.LibrosConnection;
import Model.Account;
import Model.AccountSeat;
import Model.User;
import View.LibroDiario;
import View.LibroMayor;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class LibroMayorController implements ActionListener {
    private LibroMayor libroMayorView;
    private LibrosConnection librosCon;
    private AccountSeatConnection cuentasCon;
    DefaultTableModel modelo = new DefaultTableModel();
    private MainMenuController mainMenuController;
    private User currentUser=User.getInstancia();
    
    public LibroMayorController(){
        this.libroMayorView=new LibroMayor();
        this.librosCon=new LibrosConnection();
        this.cuentasCon=new AccountSeatConnection();
        setCuentasComboBox();
        this.libroMayorView.setTitle("Ver Libro Mayor"+" - "+currentUser.getUserName()+" ( "+currentUser.getRol().substring(0, 1).toUpperCase()+currentUser.getRol().substring(1).toLowerCase()+ " ) " );
        this.libroMayorView.btnBuscar.addActionListener(this);
        this.libroMayorView.btnSalir.addActionListener(this);
        iniciarTabla();
    }
    
    public void openLibroMayor(){
        this.libroMayorView.setVisible(true);
    }
    
     public void btnBuscar(ActionEvent e) throws ClassNotFoundException, SQLException, IOException{
        if(e.getSource()==libroMayorView.btnBuscar){
        try{
            // Obtener las fechas seleccionadas de los DateChooser
            
            Date fechaDesde = libroMayorView.jDateChooserDesde.getDate();
            Date fechaHasta = libroMayorView.jDateChooserHasta.getDate();
            Object selectedItem = libroMayorView.comboCuenta.getSelectedItem();
            
            
             // Validar que ambas fechas estén seleccionadas
            if (fechaDesde == null || fechaHasta == null) {
                JOptionPane.showMessageDialog(null, "Por favor, selecciona ambas fechas.");
                libroMayorView.jDateChooserDesde.setDate(null);
                libroMayorView.jDateChooserHasta.setDate(null);
                libroMayorView.comboCuenta.setSelectedIndex(0);
                limpiarVista();
                return;
            }
            // Validar que la fecha hasta sea mayor o igual que la fecha desde
            if (fechaHasta.before(fechaDesde)) {
                JOptionPane.showMessageDialog(null, "Error: La fecha hasta debe ser mayor o igual a la fecha desde.");
                libroMayorView.jDateChooserDesde.setDate(null);
                libroMayorView.jDateChooserHasta.setDate(null); 
                libroMayorView.comboCuenta.setSelectedIndex(0);
                limpiarVista();
            return;
        }
            // Validar que se haya seleccionado un elemento en el comboBox
            
            if (selectedItem == null) {
                JOptionPane.showMessageDialog(null, "Por favor, selecciona una cuenta del comboBox.");
                libroMayorView.jDateChooserDesde.setDate(null);
                libroMayorView.jDateChooserHasta.setDate(null);
                libroMayorView.comboCuenta.setSelectedIndex(0);
                limpiarVista();
                return;
            }
           // Obtener la lista de asientos contables entre las fechas seleccionadas
            ArrayList<AccountSeatController> listaAsientos= librosCon.obtenerListaAsientos(fechaDesde, fechaHasta);
            String nombreCuenta=this.libroMayorView.comboCuenta.getSelectedItem().toString();
            Account cuenta= obtenerCuentaPorNombre(nombreCuenta);
            // Actualizar la tabla con los resultados
             actulizarTabla(listaAsientos,cuenta);
             libroMayorView.jDateChooserDesde.setDate(null);
             libroMayorView.jDateChooserHasta.setDate(null);
             libroMayorView.comboCuenta.setSelectedIndex(0);
            
        }catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Ocurrió un error al buscar los asientos.");
        }
    }
    }
     
     public void actulizarTabla(ArrayList<AccountSeatController> listaAsientos, Account cuenta) throws IOException, SQLException, ClassNotFoundException{
          iniciarTabla();
          // Variable para rastrear la última fecha añadida
          String nombreCuenta=cuenta.getAccountName();
          float saldo=cuenta.getSaldoInicial();
        // Recorrer la lista de asientos contables
        String[] filaSeparadora = {"", "", "", ""};
        modelo.addRow(filaSeparadora);
        String[] filaInicial=new String[6];
        filaInicial[0] = nombreCuenta; // Nombre de la cuenta
        filaInicial[1] = listaAsientos.isEmpty() ? "" : listaAsientos.get(0).getSeat().getDate().toString(); // Fecha inicial del primer asiento
        filaInicial[2] = "Inicial"; // Texto "Inicial" en la descripción
        filaInicial[3] = ""; // Columna "Debe" vacía
        filaInicial[4] = ""; // Columna "Haber" vacía
        filaInicial[5] ="$"+ String.valueOf(saldo); // Saldo inicial
        modelo.addRow(filaInicial);
        
        for(AccountSeatController asiento : listaAsientos){
            // Obtener los detalles de cada Asiento_Cuenta
            for(AccountSeat asientoCuenta : asiento.getAccountSeats()){
                // Obtener los datos necesarios
                if(cuenta.getIdAccount()==asientoCuenta.getIdAccount()){
                    String[] datos= new String[6];
                    datos[0]="";
                    datos[1]=asiento.getSeat().getDate().toString();
                    datos[2]=asientoCuenta.getDecripcionOperacion();
                    if(asientoCuenta.getType().toUpperCase().equals("HABER")){
                        datos[4]="$"+String.valueOf(asientoCuenta.getAmount());
                         saldo-=asientoCuenta.getAmount();
                    }else{
                        datos[3]="$"+String.valueOf(asientoCuenta.getAmount());
                        saldo+=asientoCuenta.getAmount();
                    }
                        datos[5]="$"+String.valueOf(saldo);
                    modelo.addRow(datos);
                    }
            }      
        }
        String[] filaFinal=new String[6];
        filaFinal[0] = ""; // Nombre de la cuenta
        filaFinal[1] = listaAsientos.isEmpty() ? "" : listaAsientos.get(0).getSeat().getDate().toString(); // Fecha inicial del primer asiento
        filaFinal[2] = "Final"; // Texto "Inicial" en la descripción
        filaFinal[3] = ""; // Columna "Debe" vacía
        filaFinal[4] = ""; // Columna "Haber" vacía
        filaFinal[5] ="$"+ String.valueOf(saldo); // Saldo inicial
        modelo.addRow(filaFinal);
        
        // Agregar fila separadora después de cada AsientoContable
        modelo.addRow(filaSeparadora);
     }
     

     
     public Account obtenerCuentaPorNombre(String nombreCuenta) throws IOException, SQLException, ClassNotFoundException{
         AccountSeatConnection conexionCuentas =new AccountSeatConnection();
         ArrayList<Account> arrayCuentas=conexionCuentas.getAccount();
         for(Account cuenta : arrayCuentas  ){
             // Verificar si la cuenta es nula o si el nombre de la cuenta es nulo
                if (cuenta == null || cuenta.getAccountName() == null) {
                    continue; // Saltar a la siguiente iteración si es nulo
            }
        
                // Usar equalsIgnoreCase para comparar cadenas sin importar mayúsculas o minúsculas
                if (cuenta.getAccountName().equalsIgnoreCase(nombreCuenta)) {
                    return cuenta;
                 }   
             }
    
         return null; // Retorna null si no se encontró ninguna cuenta con ese nombre
     }
     
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
        
        modelo.addColumn("Cuenta");
        modelo.addColumn("Fecha");
        modelo.addColumn("Operacion");
        modelo.addColumn("Debe");
        modelo.addColumn("Haber");
        modelo.addColumn("Saldo");
        
        libroMayorView.jTableMayor.setRowHeight(15);
        libroMayorView.jTableMayor.setModel(modelo);
        libroMayorView.jTableMayor.setRowHeight(25);
        
        libroMayorView.jTableMayor.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            // Verificar si la fila es una fila separadora (todos los valores son vacíos)
            boolean esFilaSeparadora = 
                                       (modelo.getValueAt(row, 0) == null || modelo.getValueAt(row, 0).toString().isEmpty()) &&
                                       (modelo.getValueAt(row, 1) == null || modelo.getValueAt(row, 1).toString().isEmpty()) &&
                                       (modelo.getValueAt(row, 2) == null || modelo.getValueAt(row, 2).toString().isEmpty()) &&
                                       (modelo.getValueAt(row, 3) == null || modelo.getValueAt(row, 3).toString().isEmpty()) &&
                                       (modelo.getValueAt(row, 4) == null || modelo.getValueAt(row, 4).toString().isEmpty()) &&
                                        (modelo.getValueAt(row, 5) == null || modelo.getValueAt(row, 5).toString().isEmpty());

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
     
     public void limpiarVista(){
         libroMayorView.jDateChooserDesde.setDate(null);
         libroMayorView.jDateChooserHasta.setDate(null);
         libroMayorView.comboCuenta.setSelectedIndex(0);
         iniciarTabla();
     }
     //inicializacion para combobox de cuentas//
    public ArrayList<Account> cuentas () throws IOException,  ClassNotFoundException,   SQLException{
        AccountSeatConnection conexionCuentas =new AccountSeatConnection();
        return conexionCuentas.getAccount();
     }
   
    public void setCuentasComboBox() {
        try {
            // Obtener la lista de cuentas
            ArrayList<Account> cuentas = cuentas(); // Asegúrate de que este método esté disponible en el contexto

            // Crear un modelo para el JComboBox
            DefaultComboBoxModel<String> model = 

                new DefaultComboBoxModel<>();

            // Llenar el modelo con los nombres de las cuentas
            for (Account cuenta : cuentas) {
                model.addElement(cuenta.getAccountName()); // Agregar el nombre de la cuenta
            }

            // Setear el modelo en el JComboBox
            libroMayorView.comboCuenta.setModel(model); 
    // Asegúrate de que addSeatView tenga cbbCuentas
        } catch (IOException | SQLException | ClassNotFoundException e) {
            e.printStackTrace(); // Manejar la excepción según sea necesario
        }
    }
     
     public void buttonBack(ActionEvent e){//Metodo que le da al boton volver la accion de salir de la ventana Agregar Asiento y volver al Menu Principal//
       if(e.getSource()==libroMayorView.btnSalir){
           this.libroMayorView.dispose();
           mainMenuController=new MainMenuController();
           mainMenuController.openMainMenuView();
       }
   }

    @Override
    public void actionPerformed(ActionEvent e) {
        buttonBack(e);
        try {
            btnBuscar(e);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(LibroMayorController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(LibroMayorController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(LibroMayorController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
     
    
    }
    
    

