//Clase encargada del comportamiento de la vista de agregar asiento//
package Controller;

import ConnectionsBD.AccountSeatConnection;
import ConnectionsBD.UserManagementConnection;
import Model.Account;
import Model.AccountSeat;
import Model.AsientoTabla;
import Model.Seat;
import Model.User;
import View.AddAccountSeat;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
public class AddAccountSeatController implements ActionListener{
    //Atributos//
    private AccountSeatConnection conAsiento=new AccountSeatConnection();
    private AddAccountSeat addAccountSeatView=new AddAccountSeat();
    private MainMenuController mainMenuController;
    private User currentUser=User.getInstancia();
    private UserManagementConnection conUsuario=new UserManagementConnection();
    DefaultTableModel modelo = new DefaultTableModel();
    ArrayList<AsientoTabla> asientoContable=new ArrayList<AsientoTabla>();
    //Metodos//
    public AddAccountSeatController(){//Conecta el boton Volver con la Clase
       setCuentasComboBox(); //inicializo el combox//
       this.addAccountSeatView.setTitle("Agregar Asiento"+" - "+currentUser.getUserName()+" ( "+currentUser.getRol().substring(0, 1).toUpperCase()+currentUser.getRol().substring(1).toLowerCase()+ " ) " );
       this.addAccountSeatView.btnCancelar.addActionListener(this);
       this.addAccountSeatView.btnSaveOperation.addActionListener(this);
       this.addAccountSeatView.btnGuardarAsiento.addActionListener(this);
       this.addAccountSeatView.btnBack.addActionListener(this);
       this.addAccountSeatView.cBoxDestiny.addActionListener(this);
       iniciarTabla();
 
    }
    public void openAddAccountSeatView(){//Muestra la ventana//
        addAccountSeatView.setVisible(true);
    }
    public void closeAddAccountSeatView(){//Cierra la ventana//
        addAccountSeatView.dispose();
    }
    
    public void cancelSeat(ActionEvent e) {
    if (e.getSource() == addAccountSeatView.btnCancelar) {
        // Limpiar campos
        limpiarVista();
        limpiarTabla();
    }
}

    public void limpiarTabla() {
        iniciarTabla();
        }
     public void limpiarVista(){
        addAccountSeatView.txtDescripcion.setText("");
        addAccountSeatView.txtImporte.setText("");
        addAccountSeatView.cBoxDestiny.setSelectedIndex(0);
        addAccountSeatView.comboCuenta.setSelectedIndex(0);
        addAccountSeatView.dateFecha.setDate(null);
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
        modelo.addColumn("Descripcion");
        modelo.addColumn("Cuenta");
        modelo.addColumn("Debe");
        modelo.addColumn("Haber");

        addAccountSeatView.tableModel.setRowHeight(15);
        addAccountSeatView.tableModel.setModel(modelo);
        addAccountSeatView.tableModel.setRowHeight(25);
    }
    //Metodo para cargar Jtabla
    public void cargarTabla(AsientoTabla asiento){
    String[] datos =new String[5];
    SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
    String fechaFormateada = formato.format(asiento.getFecha());
        datos[0]=fechaFormateada;
        datos[1]=asiento.getDescripcion();
        datos[2]=asiento.getCuenta();
        if (asiento.getDestino().equalsIgnoreCase("HABER")) {
            datos[4]="$"+String.valueOf(asiento.getImporte());
            datos[3]="$"+"0.0";
            
        } else {
             datos[3]="$"+String.valueOf(asiento.getImporte());
             datos[4]="$"+"0.0";
        }
        
       
        modelo.addRow(datos);   
    
    }
    //Metodo para cerrar la vista
    public void buttonBack(ActionEvent e){//Metodo que le da al boton volver la accion de salir de la ventana Agregar Asiento y volver al Menu Principal//
       if(e.getSource()==addAccountSeatView.btnBack){
           closeAddAccountSeatView();
           mainMenuController=new MainMenuController();
           mainMenuController.openMainMenuView();
       }
   }
    
    //Inicializacion para combobox de cuentas//
    public ArrayList<Account> cuentas () throws IOException,  ClassNotFoundException,   SQLException{
        AccountSeatConnection conexionCuentas =new AccountSeatConnection();
        return conexionCuentas.getAccount();
     }
   //Setear el jComboBox
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
            addAccountSeatView.comboCuenta.setModel(model); 
    // Asegúrate de que addSeatView tenga cbbCuentas
        } catch (IOException | SQLException | ClassNotFoundException e) {
            e.printStackTrace(); // Manejar la excepción según sea necesario
        }
    }

    //Metodo para obtener fila del asiento//
    public void getAsientoTabla(AsientoTabla asientoTabla) throws ParseException{
         
         LocalDate fechaActual= LocalDate.now();
         Date fecha=addAccountSeatView.dateFecha.getDate();
         String descripcion=addAccountSeatView.txtDescripcion.getText().trim();
         String destino=addAccountSeatView.cBoxDestiny.getSelectedItem().toString();
         String importText=addAccountSeatView.txtImporte.getText().toString().trim();
         Float importe;
         boolean success=esFlotante(importText);
         Object selectedCuenta=addAccountSeatView.comboCuenta.getSelectedItem();
         String cuenta= (selectedCuenta!=null) ? selectedCuenta.toString() : "";
         //Verifica que el valor  ingresado es valido sea valido
         if(fecha==null){
             JOptionPane.showMessageDialog(null,"No puede haber ninguna opcion en blanco");
             limpiarVista();
             return;
         }
         LocalDate fechaConvertida = fecha.toInstant()
                                         .atZone(ZoneId.systemDefault())
                                         .toLocalDate();
         if(fechaConvertida.isBefore(fechaActual)){
             JOptionPane.showMessageDialog(null,"La fecha no puede ser anterior a la de hoy");
             limpiarVista();
             return;
         }
         if(descripcion.isEmpty()){
             JOptionPane.showMessageDialog(null,"No puede haber ninguna opcion en blanco");
             limpiarVista();
             return;
         }
         if(destino.isEmpty()){
             JOptionPane.showMessageDialog(null,"No puede haber ninguna opcion en blanco");
             limpiarVista();
             return;
         }
         if(!success){
             JOptionPane.showMessageDialog(null,"El campo monto debe tener valores validos");
             limpiarVista();
             return;
         }else{
             importe=Float.parseFloat(importText);
         }
         if(importe==0){
                JOptionPane.showMessageDialog(null,"El campo monto no puede ser 0");
                limpiarVista();
                return;
         } 
         if(cuenta==null){
             JOptionPane.showMessageDialog(null,"No puede haber ninguna opcion en blanco");
             limpiarVista();
             return;
         }
         asientoTabla.setFecha(fecha);
         asientoTabla.setDescripcion(descripcion);
         asientoTabla.setDestino(destino);
         asientoTabla.setImporte(importe);
         asientoTabla.setCuenta(cuenta);
         asientoTabla.setIdCuenta(conAsiento.obtenerIdCuentaPorNombre(addAccountSeatView.comboCuenta.getSelectedItem().toString()));
         limpiarVista();
    }
    //Metodo para validar si se ingresa un flotante y no otro tipo de datos//
    public boolean esFlotante(String importe){
        try{
                Float.parseFloat(importe);
                return true;
        }catch(NumberFormatException e){

            return false;
      }
}
    
    
    public void botonGuardarOperacion(ActionEvent e) throws ParseException{
        if(e.getSource()== addAccountSeatView.btnSaveOperation){       
              AsientoTabla asientoTabla= new AsientoTabla();
              getAsientoTabla(asientoTabla);
              if(asientoTabla.getCuenta()==null){
                    limpiarVista();
              }
              LocalDate fechaConvertida = asientoTabla.getFecha().toInstant()
                                         .atZone(ZoneId.systemDefault())
                                         .toLocalDate();
              LocalDate fechaAnterior=obtenerFechaAnterior();
              if(fechaAnterior!=null){
                    if(fechaConvertida.isBefore(fechaAnterior)  && !fechaConvertida.isEqual(fechaAnterior)){
                        limpiarVista();
                        JOptionPane.showMessageDialog(null,"La fecha de la nueva operacion no puede ser menor a la de la operacion anterior");
                        return;
                    }
                    cargarTabla(asientoTabla);
                    asientoContable.add(asientoTabla);
                    limpiarVista();
                        return;
              }
                    cargarTabla(asientoTabla);
                    asientoContable.add(asientoTabla);
                    limpiarVista();
              }
        }
    public LocalDate obtenerFechaAnterior(){
            LocalDate fechaAnterior=null;
            DefaultTableModel tableModel = (DefaultTableModel) addAccountSeatView.tableModel.getModel();
            DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                // Obtiene la fecha en formato String y la convierte a LocalDate
                String fechaStr = (String) tableModel.getValueAt(i, 0);  
            if (fechaStr != null && !fechaStr.isEmpty()) {
                LocalDate fechaActual = LocalDate.parse(fechaStr, formatter1);
                fechaAnterior = fechaActual;
        }
        }
        return fechaAnterior;
    }
    public void botonGuardarAsientoContable(ActionEvent e) throws ClassNotFoundException, SQLException, IOException{
        if(e.getSource()==addAccountSeatView.btnGuardarAsiento){
           Seat seat =new Seat(
                   asientoContable.getFirst().getFecha(),
                   conUsuario.getIdUser(currentUser.getUserName())
                   );
           int idSeat=conAsiento.addSeat(seat);
           for( AsientoTabla asientoCuenta : asientoContable){
               AccountSeat accountSeat= new AccountSeat(
                   idSeat,
                   asientoCuenta.getIdCuenta(),
                   asientoCuenta.getImporte(),    
                   asientoCuenta.getDestino(),         
                   asientoCuenta.getDescripcion()
               );
               conAsiento.addAccountSeat(accountSeat);    
            }
            JOptionPane.showMessageDialog(null,"Se ha registrado correctamente el asiento");
            limpiarTabla();
            asientoContable=new ArrayList<AsientoTabla>();
        }
   }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        buttonBack(e);
        cancelSeat(e);
        try {
            botonGuardarAsientoContable(e);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AddAccountSeatController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(AddAccountSeatController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(AddAccountSeatController.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            botonGuardarOperacion(e);
        } catch (ParseException ex) {
            Logger.getLogger(AddAccountSeatController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
/*
  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String fechaSql = sdf.format(getAsientoTabla().getFecha()); 
            System.out.println(fechaSql);
             System.out.println(getAsientoTabla().getDescripcion());
             System.out.println(getAsientoTabla().getCuenta());
             System.out.println(getAsientoTabla().getDestino());
             System.out.println(getAsientoTabla().getImporte());
             System.out.println(getAsientoTabla().getIdCuenta());*/