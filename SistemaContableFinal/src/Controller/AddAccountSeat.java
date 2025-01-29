//Metodo para controlar el asiento contable//

package Controller;

import Connection.AccountConnection;
import Connection.AccountSeatConnection;
import Connection.UserConnection;
import Model.Account;
import Model.AccountSeat;
import Model.Seat;
import Model.SingletonUser;
import View.AddAccountSeatView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Rodrigo
 */
public class AddAccountSeat implements ActionListener{
    
    private MainMenu mainMenu;
    private final SingletonUser currentUser= SingletonUser.getInstance();
    private AddAccountSeatView view;
    DefaultTableModel modelo = new DefaultTableModel();
    private List<Account> cuentasActualizar;
    private AccountSeatConnection seatCon;
    private UserConnection userCon;
    private ArrayList<AccountSeat> asientoContable=new ArrayList<AccountSeat>();
    private AccountConnection conAccount;
    
    public AddAccountSeat() throws IOException, ClassNotFoundException, SQLException{
        this.view=new AddAccountSeatView();
        setCuentasComboBox(); //inicializo el combox//
        this.cuentasActualizar=cuentas();
        cuentasActualizar.remove(0);
        this.view.setTitle("Agregar Asiento"+"-"+currentUser.getUserName().toUpperCase()+"("+currentUser.getRol()+")");
        initializeListeners();
        iniciarTabla();
    }
    
    //Metodo para inicializar los botones//
    public final void initializeListeners(){
    
        this.view.btnCancelar.addActionListener(this);
        this.view.btnSaveOperation.addActionListener(this);
        this.view.btnGuardarAsiento.addActionListener(this);
        this.view.btnBack.addActionListener(this);
        this.view.cBoxDestiny.addActionListener(this);
        
    }
    public void openAddAccountSeatView(){//Muestra la ventana//
        view.setVisible(true);
    }
    public void closeAddAccountSeatView(){//Cierra la ventana//
        view.dispose();
    }
    
    //Inicializacion para combobox de cuentas//
    public final List<Account> cuentas () throws IOException,  ClassNotFoundException,   SQLException{
        
        AccountConnection conexionCuentas =new AccountConnection();
        return conexionCuentas.getAccounts();
     }
   //Setear el jComboBox
    public final void setCuentasComboBox() {
        try {
            // Obtener la lista de cuentas
            List<Account> cuentas = cuentas(); // Asegúrate de que este método esté disponible en el contexto
            
            // Crear un modelo para el JComboBox
            DefaultComboBoxModel<String> model = 

                new DefaultComboBoxModel<>();
            
            model.addElement("");
            // Llenar el modelo con los nombres de las cuentas
            for (Account cuenta : cuentas) {
               
                if(cuenta.getReceiveBalance()==1){
                     model.addElement(cuenta.getAccountName()); // Agregar el nombre de la cuenta
                }
            }
            // Setear el modelo en el JComboBox
            view.comboCuenta.setModel(model); 
    // Asegúrate de que addSeatView tenga cbbCuentas
        } catch (IOException | SQLException | ClassNotFoundException e) {
            e.printStackTrace(); // Manejar la excepción según sea necesario
        }
    }
    //Metodo para iniciar la tabla//
     public final void iniciarTabla() {
 
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

        view.tableModel.setRowHeight(15);
        view.tableModel.setModel(modelo);
        view.tableModel.setRowHeight(25);
    }
     //Metodo para limpiar la tabla//
      public void limpiarTabla() {
        iniciarTabla();
        }
      //Metodo para limpiar la vista//
     public void limpiarVista(){

        view.txtImporte.setText("");
        view.cBoxDestiny.setSelectedIndex(0);
        view.comboCuenta.setSelectedIndex(0);

     }
      //Metodo para cargar Jtabla
    public void cargarTabla(AccountSeat asiento , Date fecha){
    String[] datos =new String[5];
    SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
    String fechaFormateada = formato.format(fecha);
        datos[0]=fechaFormateada;
        datos[1]=view.txtDescripcion.getText();
        datos[2]=asiento.getAccount();
        if (asiento.getDestiny().equalsIgnoreCase("HABER")) {
            datos[4]="$"+String.valueOf(asiento.getAmount());
            datos[3]="$"+"0.0";
            
        } else {
             datos[3]="$"+String.valueOf(asiento.getAmount());
             datos[4]="$"+"0.0";
        }
        
       
        modelo.addRow(datos);   
    
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
     //Metodo para el boton cancelar//
      public void cancelSeat(ActionEvent e) {
    if (e.getSource() == view.btnCancelar) {
        // Limpiar campos
        limpiarVista();
        view.dateFecha.setEnabled(true);
        view.txtDescripcion.setEditable(true);
        asientoContable.clear();
    }
}
      //Metodo para obtener fila del asiento//
    public void getAsientoTabla(AccountSeat asientoTabla) throws ParseException{
         seatCon= new AccountSeatConnection();
         LocalDate fechaActual= LocalDate.now();
         Date fecha=view.dateFecha.getDate();
         String descripcion=view.txtDescripcion.getText().trim();
         String destino=view.cBoxDestiny.getSelectedItem().toString();
         String importText=view.txtImporte.getText().toString().trim();
         Float importe;
         boolean success=esFlotante(importText);
         Object selectedCuenta=view.comboCuenta.getSelectedItem();
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
         if(fechaConvertida.isAfter(fechaActual)){
             JOptionPane.showMessageDialog(null,"La fecha no puede ser posterior a la de hoy");
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
         if(importe<=0){
                JOptionPane.showMessageDialog(null,"El campo monto no puede ser negativo o 0");
                limpiarVista();
                return;
         }
         if(cuenta==null){
             JOptionPane.showMessageDialog(null,"No puede haber ninguna opcion en blanco");
             limpiarVista();
             return;
         }
         
         asientoTabla.setDestiny(destino);
         asientoTabla.setAmount(importe);
         asientoTabla.setAccount(cuenta);
         asientoTabla.setIdCuenta(seatCon.obtenerIdCuentaPorNombre(view.comboCuenta.getSelectedItem().toString()));
         
         limpiarVista();
    }
    //Metodo para seatear el asiento//
    public void getAsiento(Seat asiento) throws SQLException{
        userCon= new UserConnection();
        asiento.setIdUsuario(userCon.getUserId(currentUser.getUserName()));
        asiento.setSeatDate(view.dateFecha.getDate());
        asiento.setSeatDescrip(view.txtDescripcion.getText());
    };
    //Metodo para guardar el asiento//
    public void botonGuardarOperacion(ActionEvent e) throws ParseException, SQLException{
        if(e.getSource()== view.btnSaveOperation){       
              AccountSeat asientoTabla= new AccountSeat();
              Seat asiento=new Seat();
              getAsientoTabla(asientoTabla);
              getAsiento(asiento);
              if(asientoTabla.getAccount()==null){
                    limpiarVista();
                    return;
              }
              LocalDate fechaConvertida = asiento.getSeatDate().toInstant()
                                         .atZone(ZoneId.systemDefault())
                                         .toLocalDate();
              LocalDate fechaAnterior=obtenerFechaAnterior();
              if(fechaAnterior!=null){
                    if(fechaConvertida.isBefore(fechaAnterior)  && !fechaConvertida.isEqual(fechaAnterior)){
                        limpiarVista();
                        view.txtDescripcion.setText("");
                        view.dateFecha.setDate(null);
                        JOptionPane.showMessageDialog(null,"La fecha de la nueva operacion no puede ser menor a la de la operacion anterior");
                        return;
                    }
                    if(!fechaConvertida.isEqual(fechaAnterior)){
                        limpiarVista();
                        view.txtDescripcion.setText("");
                        view.dateFecha.setDate(null);
                        JOptionPane.showMessageDialog(null,"La fecha de la nueva operacion debe ser igual a la operacion anterior");
                        return;
                    }
                 }
                     if(asientoTabla.getDestiny().equalsIgnoreCase("haber") && asientoContable.isEmpty()){
                        limpiarVista();
                        view.txtDescripcion.setText("");
                        view.dateFecha.setDate(null);
                        JOptionPane.showMessageDialog(null,"El destino del la primera operacion debe ser un deber");
                        return;
                        }

                    if(!asientoContable.isEmpty()){
                        Double montoAux=0.0;
                        for( int i =0; i<asientoContable.size() ; i++){
                             
                            AccountSeat asientoContableAux=asientoContable.get(i);
//                        if(asientoTabla.getDestino().trim().equalsIgnoreCase("debe") && asientoContable.get(asientoContable.size()-1).getDestino().trim().equalsIgnoreCase("debe")){
//                            JOptionPane.showMessageDialog(null,"Error: El total de debitos debe ser igual al total de creditos");
//                            limpiarVista();
//                            return;
//                        }
                            if(asientoContableAux.getDestiny().equalsIgnoreCase("debe")){
                                montoAux=montoAux+asientoContableAux.getAmount();
                            }else{
                                montoAux=montoAux-asientoContableAux.getAmount();
                            }       
                         }
                        if(asientoTabla.getDestiny().equalsIgnoreCase("haber")){
                            if(montoAux-asientoTabla.getAmount()<0){
                                JOptionPane.showMessageDialog(null,"Error: El total de debitos debe ser igual al total de creditos");
                                limpiarVista();
                                view.txtDescripcion.setText("");
                                view.dateFecha.setDate(null);
                                return;
                          }
                        }
                        if(asientoTabla.getDestiny().equalsIgnoreCase("debe")){
                             for(Account cuenta: cuentasActualizar){
                                if(cuenta.getIdAccount()==asientoTabla.getIdCuenta()){
                                    if(cuenta.getType().equalsIgnoreCase("pasivo") || cuenta.getType().equalsIgnoreCase("resultado positivo")){        
                                        if(cuenta.getBalance()-asientoTabla.getAmount()<0){
                                            JOptionPane.showMessageDialog(null,"Error: El saldo de la cuenta es insuficiente para esta operacion");
                                            limpiarVista();
                                            view.txtDescripcion.setText("");
                                            view.dateFecha.setDate(null);
                                            return;
                                        }
                                        cuenta.setBalance(cuenta.getBalance()-asientoTabla.getAmount());
                                        asientoTabla.setSaldoPostOperacion(cuenta.getBalance());
                                        break;
                                }
                                cuenta.setBalance(cuenta.getBalance()+asientoTabla.getAmount());
                                asientoTabla.setSaldoPostOperacion(cuenta.getBalance());
                                break;
                            }
                            }
                        }
                        if(asientoTabla.getDestiny().equalsIgnoreCase("haber")){
                             for(Account cuenta : cuentasActualizar){
                                if(cuenta.getIdAccount()==asientoTabla.getIdCuenta()){
                                    if(cuenta.getType().equalsIgnoreCase("activo") || cuenta.getType().equalsIgnoreCase("resultado negativo")){        
                                        if(cuenta.getBalance()-asientoTabla.getAmount()<0){
                                            JOptionPane.showMessageDialog(null,"Error: El saldo de la cuenta es insuficiente para esta operacion");
                                            limpiarVista();
                                            view.txtDescripcion.setText("");
                                            view.dateFecha.setDate(null);
                                            return;
                                        }
                                        cuenta.setBalance(cuenta.getBalance()-asientoTabla.getAmount());
                                        asientoTabla.setSaldoPostOperacion(cuenta.getBalance());
                                        break;
                                }
                                cuenta.setBalance(cuenta.getBalance()+asientoTabla.getAmount());
                                asientoTabla.setSaldoPostOperacion(cuenta.getBalance());
                                break;
                            }
                                
                    }
                             
                        }
                        cargarTabla(asientoTabla,view.dateFecha.getDate());
                        asientoContable.add(asientoTabla);
                        limpiarVista();
                        view.dateFecha.setEnabled(false);
                        view.txtDescripcion.setEditable(false);
                        return;
                   }
                    for(Account cuenta: cuentasActualizar){
                            if(cuenta.getIdAccount()==asientoTabla.getIdCuenta()){
                                if(cuenta.getType().equalsIgnoreCase("pasivo") || cuenta.getType().equalsIgnoreCase("resultado positivo")){        
                                        if(cuenta.getBalance()-asientoTabla.getAmount()<0){
                                            JOptionPane.showMessageDialog(null,"Error: El saldo de la cuenta es insuficiente para esta operacion");
                                            limpiarVista();
                                            view.txtDescripcion.setText("");
                                            view.dateFecha.setDate(null);
                                            return;
                                        }
                                        cuenta.setBalance(cuenta.getBalance()-asientoTabla.getAmount());
                                        asientoTabla.setSaldoPostOperacion(cuenta.getBalance());
                                        break;
                                }
                                cuenta.setBalance(cuenta.getBalance()+asientoTabla.getAmount());
                                asientoTabla.setSaldoPostOperacion(cuenta.getBalance());
                                break;
                            }
                    }
                    cargarTabla(asientoTabla, view.dateFecha.getDate());
                    asientoContable.add(asientoTabla);
                    limpiarVista();
                    view.dateFecha.setEnabled(false);
                    view.txtDescripcion.setEditable(false);
                    
        }
   }
    //Metodo para validar fechas//
     public LocalDate obtenerFechaAnterior(){
            LocalDate fechaAnterior=null;
            DefaultTableModel tableModel = (DefaultTableModel) view.tableModel.getModel();
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
     //Metodo para validar el monto//
      public boolean montoFinalValido(){
         Double monto =0.0;
          for( int i =0; i<asientoContable.size() ; i++){
                AccountSeat asiento=asientoContable.get(i);
               if(asiento.getDestiny().equalsIgnoreCase("debe")){
                         monto=monto+asiento.getAmount();
               }else{
                         monto=monto-asiento.getAmount();
                       }    
                }
        if(monto!=0){
            return false;
        }
        return true;
      }
      //Metodo para guardar el asiento//
       public void botonGuardarAsientoContable(ActionEvent e) throws ClassNotFoundException, SQLException, IOException{
        if(e.getSource()==view.btnGuardarAsiento){
            if(asientoContable.isEmpty()==true){
                JOptionPane.showMessageDialog(null,"No se puede guardar un asiento vacio");
                return;
            }
            if(montoFinalValido()==false){
                JOptionPane.showMessageDialog(null,"Error: El total de debitos debe ser igual al total de creditos");
                return;
            }
           UserConnection conUsuario=new UserConnection();
           conAccount= new AccountConnection();
            
           Seat seat =new Seat(
                   
                   conUsuario.getUserId(currentUser.getUserName()),
                   view.dateFecha.getDate(),
                   view.txtDescripcion.getText()
                   );
           
           int idSeat=seatCon.addSeat(seat);
           
           for( AccountSeat asientoCuenta : asientoContable){
               AccountSeat accountSeat= new AccountSeat(
                   idSeat,
                   asientoCuenta.getDestiny(),
                   asientoCuenta.getIdCuenta(),
                   asientoCuenta.getAmount(),    
                   asientoCuenta.getSaldoPostOperacion()
               );
               seatCon.addAccountSeat(accountSeat);    
            }
            conAccount.actualizarSaldo(cuentasActualizar);
            JOptionPane.showMessageDialog(null,"Se ha registrado correctamente el asiento");
            limpiarTabla();
            setCuentasComboBox();
            view.txtDescripcion.setText("");
            view.dateFecha.setDate(null);
            view.dateFecha.setEnabled(true);
            view.txtDescripcion.setEditable(true);
            asientoContable=new ArrayList<AccountSeat>();
            cuentasActualizar=cuentas();
            cuentasActualizar.remove(0);
            }
        }
      //Metodo para cerrar la vista
    public void buttonBack(ActionEvent e){//Metodo que le da al boton volver la accion de salir de la ventana Agregar Asiento y volver al Menu Principal//
       if(e.getSource()==view.btnBack){
           closeAddAccountSeatView();
           mainMenu=new MainMenu();
           mainMenu.openMainMenuView();
       }
   }
 
    @Override
    public void actionPerformed(ActionEvent e) {
        cancelSeat(e);
        try {
            botonGuardarOperacion(e);
        } catch (ParseException ex) {
            Logger.getLogger(AddAccountSeat.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(AddAccountSeat.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            botonGuardarAsientoContable(e);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AddAccountSeat.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(AddAccountSeat.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(AddAccountSeat.class.getName()).log(Level.SEVERE, null, ex);
        }
        buttonBack(e);
    }
    
    
}
