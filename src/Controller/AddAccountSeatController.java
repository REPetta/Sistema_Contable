//Clase encargada del comportamiento de la vista de agregar asiento//
package Controller;

import ConnectionsBD.AccountSeatConnection;
import Model.Account;
import Model.AsientoTabla;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
public class AddAccountSeatController implements ActionListener{
    //Atributos//
    private AccountSeatConnection conexionAsientoBD=new AccountSeatConnection();
    private AddAccountSeat addAccountSeatView=new AddAccountSeat();
    private MainMenuController mainMenuController;
    private User currentUser=User.getInstancia();
    DefaultTableModel modelo = new DefaultTableModel();
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
    }
}

    public void limpiarVista() {
        addAccountSeatView.txtDescripcion.setText("");
        addAccountSeatView.txtImporte.setText("");
        addAccountSeatView.cBoxDestiny.setSelectedIndex(0);
        addAccountSeatView.comboCuenta.setSelectedIndex(0);
        addAccountSeatView.dateFecha.setDate(null);
        iniciarTabla();
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
    
    public void cargarTabla(AsientoTabla asiento){
    String[] datos =new String[5];
        datos[0]=asiento.getFecha().toString();
        datos[1]=asiento.getDescripcion();
        datos[2]=asiento.getCuenta();
        if (asiento.getDestino()=="HABER") {
            datos[4]=String.valueOf(asiento.getImporte());
            
        } else {
             datos[3]=String.valueOf(asiento.getImporte());
        }
        
       
        modelo.addRow(datos);   
    
    }
    public void buttonBack(ActionEvent e){//Metodo que le da al boton volver la accion de salir de la ventana Agregar Asiento y volver al Menu Principal//
       if(e.getSource()==addAccountSeatView.btnBack){
           closeAddAccountSeatView();
           mainMenuController=new MainMenuController();
           mainMenuController.openMainMenuView();
       }
   }
   
    @Override
    public void actionPerformed(ActionEvent e) {
        buttonBack(e);
        cancelSeat(e);
        try {
            accionBtnAgregarAtabla(e);
        } catch (ParseException ex) {
            Logger.getLogger(AddAccountSeatController.class.getName()).log(Level.SEVERE, null, ex);
        }
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
            addAccountSeatView.comboCuenta.setModel(model); 
    // Asegúrate de que addSeatView tenga cbbCuentas
        } catch (IOException | SQLException | ClassNotFoundException e) {
            e.printStackTrace(); // Manejar la excepción según sea necesario
        }
    }
    
    public AsientoTabla getAsientoTabla() throws ParseException{
        AsientoTabla asientoTabla= new AsientoTabla();
         asientoTabla.setFecha(addAccountSeatView.dateFecha.getDate());
        asientoTabla.setDescripcion(addAccountSeatView.txtDescripcion.getText());
        asientoTabla.setDestino(addAccountSeatView.cBoxDestiny.getSelectedItem().toString());
        asientoTabla.setImporte(Float.parseFloat(addAccountSeatView.txtImporte.getText().toString()));
        asientoTabla.setCuenta(addAccountSeatView.comboCuenta.getSelectedItem().toString());
        asientoTabla.setIdCuenta(conexionAsientoBD.obtenerIdCuentaPorNombre(addAccountSeatView.comboCuenta.getSelectedItem().toString()));
        return asientoTabla;
    
    }
    
    public void accionBtnAgregarAtabla(ActionEvent e) throws ParseException{
        if(e.getSource()== addAccountSeatView.btnSaveOperation){
  /*          SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String fechaSql = sdf.format(getAsientoTabla().getFecha()); 
            System.out.println(fechaSql);
             System.out.println(getAsientoTabla().getDescripcion());
             System.out.println(getAsientoTabla().getCuenta());
             System.out.println(getAsientoTabla().getDestino());
             System.out.println(getAsientoTabla().getImporte());
             System.out.println(getAsientoTabla().getIdCuenta());*/
              cargarTabla(getAsientoTabla());
        }
    
    }

}
