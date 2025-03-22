
package Controller;

import Connection.AccountConnection;
import Connection.AccountSeatConnection;
import Connection.CustomerConnection;
import Connection.ItemConnection;
import Connection.SalesConnection;
import Connection.UserConnection;
import Model.Account;
import Model.AccountSeat;
import Model.Bill;
import Model.Customer;
import Model.Item;
import Model.Sale;
import Model.SaleDetails;
import Model.SaleNode;
import Model.SaleType;
import Model.Seat;
import Model.SingletonUser;
import View.SalesSystemView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class SalesSystem implements ActionListener {
    //Atributos//
    private MainMenu mainMenu;
    DefaultTableModel modelo = new DefaultTableModel();
    private final SingletonUser currentUser= SingletonUser.getInstance();
    private CustomerManagement customerManagement;
    private ItemsManagement itemsManagement;
    private final SalesSystemView view;
    private PaymentManagement paymentManagement;
    private SalesReport salesReport;
    private BillManagement billManagement;
    private ItemConnection itemsCon;
    private CustomerConnection customerCon;
    private SalesConnection salesCon;
    private final AccountConnection conAccount;
    private SaleNode saleNode;
    private List<Item> listaArticulos;
    private final UserConnection conUsuario;
    private final AccountSeatConnection seatCon;
    private double subTotal;
    //Constructor//
    public SalesSystem(){
        view=new SalesSystemView();
        saleNode=new SaleNode();
        listaArticulos=new ArrayList<>();
        conUsuario=new UserConnection();
        conAccount=new AccountConnection();
        seatCon=new AccountSeatConnection();
        subTotal=0.0;
        initializeListeners();
        this.view.setTitle("Modulo de Ventas"+"-"+currentUser.getUserName().toUpperCase()+"("+currentUser.getRol()+")");
        setClientesBox();
        setArticulosBox();
        setMethodBox();
        iniciarTabla();
        displayBasedRol(currentUser);
        }
    //Metodo para inicializar//
    public final void initializeListeners(){
        this.view.btnSalir.addActionListener(this);
        this.view.btnItems.addActionListener(this);
        this.view.btnCustomer.addActionListener(this);
        this.view.btnFac.addActionListener(this);
        this.view.btnSales.addActionListener(this);
        this.view.btnPayment.addActionListener(this);
        this.view.btnAdd.addActionListener(this);
        this.view.btnCancel.addActionListener(this);
        this.view.btnSave.addActionListener(this);
    }
    //Metodo para ocultar ciertos botones en funcion del rol del usuario//
    public final void displayBasedRol(SingletonUser current){
      
        if(currentUser.getRol().equalsIgnoreCase("Vendedor")){
            this.view.btnItems.setVisible(false);
            this.view.btnPayment.setVisible(false);
          
        }
    }
    //Metodo para abrir la ventana //
    public void openSalesSystemView(){
        this.view.setVisible(true);
    }
    
    //Metodo para cerrar la ventana //
    public void closeSalesSystemView(){
        this.view.dispose();
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
            view.jCustomerBox.setModel(model); 
    // Asegúrate de que addSeatView tenga cbbCuentas
        } catch (IOException | SQLException | ClassNotFoundException e) {
            e.printStackTrace(); // Manejar la excepción según sea necesario
        }
    }
//inicializacion para combobox de cuentas//
    public List<Customer> clientes () throws IOException,  ClassNotFoundException,   SQLException{
        customerCon =new CustomerConnection();
        return customerCon.getCustomers();
     }
    //Metodo para inicializar el comboBox//
   public final void setArticulosBox(){
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
            view.jItemBox.setModel(model); 
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
            view.jMethodBox.setModel(model); 
    // Asegúrate de que addSeatView tenga cbbCuentas
        } catch (IOException | SQLException | ClassNotFoundException e) {
            e.printStackTrace(); // Manejar la excepción según sea necesario
        }
    }
 //Metodo para obtener el dni del cliente//
public int getCodeSelected(){
    String selectedItem = (String) view.jMethodBox.getSelectedItem();
    int methodCode=0;
if (selectedItem != null && !selectedItem.isEmpty()) {
    String[] parts = selectedItem.split("-");

    if (parts.length == 2) { // Nos aseguramos de que el formato es correcto
        String code = parts[1].trim();  // Obtener el DNI
        methodCode = Integer.parseInt(code);
    } else {
        JOptionPane.showMessageDialog(null, "Formato inválido en la selección del metodo.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
        return methodCode;
}
    //Metodo para obtener el dni del cliente//
public int getItemCodeSelected(){
    String selectedItem = (String) view.jItemBox.getSelectedItem();
    int itemCode=0;
if (selectedItem != null && !selectedItem.isEmpty()) {
    String[] parts = selectedItem.split("-");
    
    if (parts.length == 2) { // Nos aseguramos de que el formato es correcto
        String dni = parts[1].trim();  // Obtener el DNI
        itemCode = Integer.parseInt(dni);
        
    } else {
        JOptionPane.showMessageDialog(null, "Formato inválido en la selección del cliente.", "Error", JOptionPane.ERROR_MESSAGE);
    }
}
        return itemCode;
}

    //Metodo para darle funcionalida al boton agregar cliente//
    public void buttonCustomer(ActionEvent e){
        if(e.getSource()==view.btnCustomer){
            closeSalesSystemView();
           customerManagement=new CustomerManagement();
            customerManagement.openCustomerManagementView();
        }
    }
    //Metodo para darle funcionalida al boton agregar cliente//
    public void buttonItem(ActionEvent e){
        if(e.getSource()==view.btnItems){
            closeSalesSystemView();
            itemsManagement=new ItemsManagement();
            itemsManagement.openItemsManagementView();
        }
    }
    //Metodo para darle funcionalida al boton forma de pago/
    public void buttonPayment(ActionEvent e){
        if(e.getSource()==view.btnPayment){
            closeSalesSystemView();
            paymentManagement=new PaymentManagement();
            paymentManagement.openView();
        }
    }
    //Metodo para el boton ventas//
    public void buttonSalesReport(ActionEvent e){
        if(e.getSource()==view.btnSales){
            closeSalesSystemView();
            salesReport= new SalesReport();
            salesReport.openBook();
        }
    }
    //Metodo para el boton facturacion/
    public void buttonBills(ActionEvent e){
        if(e.getSource()==view.btnFac){
            closeSalesSystemView();
            billManagement= new BillManagement();
            billManagement.openView();
        }
    }
    
    //Metodo para darle funcionalidad al boton Salir//
    public void buttonExit(ActionEvent e){
        if(e.getSource()==view.btnSalir){
            closeSalesSystemView();
            mainMenu=new MainMenu();
            mainMenu.openMainMenuView();
        
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
        modelo.addColumn("Nombre Articulo");
        modelo.addColumn("Cantidad");
        modelo.addColumn("Valor Total");
        modelo.addColumn("Nombre de Cliente");
        modelo.addColumn("Metodo de Pago");

        view.jTableSales.setRowHeight(15);
        view.jTableSales.setModel(modelo);
        view.jTableSales.setRowHeight(25);
    }
     //Metodo para validar el que la venta sea posible//
     public boolean validSell(SaleNode saleNode , Item item ,double valorTotal,int cantidad){
            
         if(item.getStock()-cantidad<0){
             JOptionPane.showMessageDialog(null, "No hay suficiente Stock", "Error", JOptionPane.ERROR_MESSAGE);
             return false;
         }
         if(item.getStock()-cantidad<item.getIdItem()){
             JOptionPane.showMessageDialog(null, "El producto "+item.getItemName()+" con codigo "+item.getItemCode()+" tiene un stock por debajo del minimo", "Advertencia", JOptionPane.WARNING_MESSAGE);
         }
         
        return false;
            
     
     }
      //Metodo para cargar Jtabla
    public void cargarTabla(SaleNode saleNode , Item item ,double valorTotal,int cantidad){
    String[] datos =new String[6];
    SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
    String fechaFormateada = formato.format(saleNode.getSale().getSaleDate());
        datos[0]=fechaFormateada;
        datos[1]=item.getItemName();
        datos[2]= String.valueOf(cantidad);
        datos[3]= "$"+String.valueOf(valorTotal);
        datos[4]=saleNode.getCustomer().getClientName();
        datos[5]=saleNode.getSaleType().getSaleDescription();
        modelo.addRow(datos);   
    }
    //Metodo para obtener el dni del cliente//
public int getDniCustomerSelected(){
    String selectedItem = (String) view.jCustomerBox.getSelectedItem();
    int dniCliente=0;
if (selectedItem != null && !selectedItem.isEmpty()) {
    String[] parts = selectedItem.split("-");
    
    if (parts.length == 2) { // Nos aseguramos de que el formato es correcto
        String dni = parts[1].trim();  // Obtener el DNI
        dniCliente = Integer.parseInt(dni);
        
    } else {
        JOptionPane.showMessageDialog(null, "Formato inválido en la selección del cliente.", "Error", JOptionPane.ERROR_MESSAGE);
    }
}
        return dniCliente;
}
//Metodo para validar los campos//
    public boolean validFieldsPermanent(){
        
        if(getCodeSelected()==0){
            return false;
        }
        if(getDniCustomerSelected()==0){
            return false;
        }
        
        return view.jDateChooser.getDate()!=null;
    }
//Metodo para validar los campos //
    public boolean validFields(){
    
    return getItemCodeSelected()!=0;
       
    }
    //Metodo para obtenerLaVenta//
    public void getSaleCustomerAndMethod() throws SQLException{

        Customer customer;
        SaleType saleType;
        Sale sale= new Sale();
        
        if(validFieldsPermanent()){
            
            customer=customerCon.getCustomer(getDniCustomerSelected());
            saleType=salesCon.getMethod(getCodeSelected());
            
            java.util.Date fecha = view.jDateChooser.getDate();
            java.sql.Date sqlDate = new java.sql.Date(fecha.getTime());
            
            sale.setSaleDate(sqlDate);
            
            saleNode.setCustomer(customer);
            saleNode.setSaleType(saleType);
            saleNode.setSale(sale);
            saleNode.setSalesDetails(new ArrayList<>());
        }
    }
    //Metodo para limpiar la vista//
     public void limpiarVistaTotal(){

        setClientesBox();
        setArticulosBox();
        setMethodBox();
        view.jDateChooser.setDate(null);
        view.jCantidad.setValue(1);
        view.jDateChooser.setEnabled(true);
        view.jCustomerBox.setEnabled(true);
        view.jMethodBox.setEnabled(true);
        subTotal=0.0;
        saleNode=new SaleNode();
     }
     public void limpiarVistaParcial(){
            setArticulosBox();
            view.jCantidad.setValue(1);
            view.jDateChooser.setEnabled(false);
            view.jCustomerBox.setEnabled(false);
            view.jMethodBox.setEnabled(false);
     }
    public double formatDouble(double value) {
        return BigDecimal.valueOf(value)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
    }
   //Metodo para validar si no son null los campos  en saleNode//
    public boolean isNull(){
    
        if(saleNode.getCustomer()==null){
            return true;
        }
        if(saleNode.getSaleType()==null){
            return true;
        }
        
        return saleNode.getSale().getSaleDate()==null;
    }
   //Metodo para agregar una venta//
    public void buttonAddSale(ActionEvent e) throws SQLException{
        if(e.getSource()==view.btnAdd){
          
            Item item;
           
            if(isNull()){
                getSaleCustomerAndMethod();
               if(!isNull()){
                    if( validFields() ){
                        item=itemsCon.getItem(getItemCodeSelected());
                        int cantidad=(int) view.jCantidad.getValue();
                        subTotal=subTotal+(cantidad*item.getUnitPrice());
                         System.out.println(subTotal);
                        subTotal = formatDouble(subTotal);
                       
                        
                        if(validStock( item, cantidad)){
                            if(!stockMin(item,cantidad)){
                                JOptionPane.showMessageDialog(null, "El stock esta por debajo del minimo", "Advertencia", JOptionPane.WARNING_MESSAGE);
                            }
                             if(!validBalanceMercaderia(cantidad, item)){
                                        limpiarVistaParcial();
                                        return;
                                    }
                                    if(!validBalanceVenta(subTotal)){
                                        limpiarVistaParcial();
                                        subTotal=subTotal-(cantidad*item.getUnitPrice());
                                        return;
                                    }
                             SaleDetails saleDetail=new SaleDetails();
                             saleDetail.setIdItem(item.getIdItem());
       
                             saleDetail.setQuantity(cantidad);
                             saleDetail.setSalePrice(subTotal);
                             saleDetail.setSubTotal(subTotal);
                             
                            saleNode.addSalesDetails(saleDetail);
                                    
                            cargarTabla(saleNode, item, subTotal, cantidad);
                            item.setStock(item.getStock()-cantidad);
                            listaArticulos.add(item);
                            limpiarVistaParcial();
                            return;
                    
                        }else{
                            
                            JOptionPane.showMessageDialog(null, "No hay suficiente stock", "Error", JOptionPane.ERROR_MESSAGE);
                            limpiarVistaTotal();
                            return;
                    }
                       
                    }else{
                        JOptionPane.showMessageDialog(null, "No puede dejar ningun campo en blanco", "Error", JOptionPane.ERROR_MESSAGE);
                        limpiarVistaTotal();
                        return;
                    }
                }
                        JOptionPane.showMessageDialog(null, "No puede dejar ningun campo en blanco", "Error", JOptionPane.ERROR_MESSAGE);
                        limpiarVistaTotal();
                        return;
            }    
            if(validFields()){
                    
                    item=itemsCon.getItem(getItemCodeSelected());
                    int cantidad=(int) view.jCantidad.getValue();
                    subTotal=subTotal+(cantidad*item.getUnitPrice());
                    subTotal = formatDouble(subTotal);
                    System.out.println(subTotal);
                    //En caso de que ya se halla agregado a la venta el articulo anteriormente//
                    for (Item articulo : listaArticulos){
                       if(listaArticulos.contains(item)){
                            if(validStock( articulo, cantidad)){
                                if(!stockMin(articulo,cantidad)){
                                JOptionPane.showMessageDialog(null, "El stock esta por debajo del minimo", "Advertencia", JOptionPane.WARNING_MESSAGE);
                            }             
                                }
                                    if(!validBalanceMercaderia(cantidad, articulo)){
                                        limpiarVistaParcial();
                                        return;
                                    }
                                    if(!validBalanceVenta(subTotal)){
                                        limpiarVistaParcial();
                                        subTotal=subTotal-(cantidad*item.getUnitPrice());
                                        return;
                                    }
                                        SaleDetails saleDetail=new SaleDetails();
                                        
                                        saleDetail.setIdItem(item.getIdItem());
                                     
                                        saleDetail.setQuantity(cantidad);
                                        saleDetail.setSalePrice(subTotal);
                                        saleDetail.setSubTotal(subTotal);
                             
                                        saleNode.addSalesDetails(saleDetail);
                                    
                                        articulo.setStock(articulo.getStock()-cantidad);
                                        cargarTabla(saleNode, item, subTotal, cantidad);
                                        limpiarVistaParcial();
                                        return;
                            }
                       
                        }
                    
                    //En caso de que sea un nuevo articulo
                      if(validStock( item, cantidad)){
                            if(!stockMin(item,cantidad)){
                                JOptionPane.showMessageDialog(null, "El stock esta por debajo del minimo", "Advertencia", JOptionPane.WARNING_MESSAGE);
                            }
                             if(!validBalanceMercaderia(cantidad, item)){
                                        limpiarVistaParcial();
                                        return;
                                    }
                             
                             if(!validBalanceVenta(subTotal)){
                                        limpiarVistaParcial();
                                        subTotal=subTotal-(cantidad*item.getUnitPrice());
                                        return;
                             }
                             
                           SaleDetails saleDetail=new SaleDetails();
                                        
                           saleDetail.setIdItem(item.getIdItem());
                          
                           saleDetail.setQuantity(cantidad);
                           saleDetail.setSalePrice(subTotal);
                           saleDetail.setSubTotal(subTotal);
                             
                           saleNode.addSalesDetails(saleDetail);
                                        
                            cargarTabla(saleNode, item, subTotal, cantidad);
                            limpiarVistaParcial();
                            item.setStock(item.getStock()-cantidad);
                            listaArticulos.add(item);
                            return;
                    
                        }else{
                            
                            JOptionPane.showMessageDialog(null, "No hay suficiente stock", "Error", JOptionPane.ERROR_MESSAGE);
                            limpiarVistaTotal();
                            return;
                    }
                }
            
              JOptionPane.showMessageDialog(null, "No puede dejar ningun campo en blanco", "Error", JOptionPane.ERROR_MESSAGE);
              limpiarVistaParcial();
            }
        }
    //Metodo para obtener Total//
    private double obtenerTotal(){
        double total=0.0;
        for(SaleDetails detalle : saleNode.getSalesDetails()){
                total=total+detalle.getSalePrice();
        }
        
        return total;
    }
    //Metodo para guardar una venta//
    public void buttonSaveSale(ActionEvent e) throws SQLException, ClassNotFoundException, IOException{
        if(e.getSource()==view.btnSave){
           
            if(saleNode==null){
                JOptionPane.showMessageDialog(null, "No hay ninguna venta para agregar", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
      
          Sale sale= saleNode.getSale();
          sale.setIdUser(conUsuario.getUserId(currentUser.getUserName()));
          sale.setIdClient(saleNode.getCustomer().getIdClient());
          sale.setReceiptNumber(salesCon.getReceiptNumber()+1);
          sale.setSalesTotal(obtenerTotal());
          sale.setSaleState('V');
          sale.setIdSaleType(saleNode.getSaleType().getIdSaleType());
          
          salesCon.addSale(sale);
          
           for(SaleDetails detalle : saleNode.getSalesDetails()){
                detalle.setIdSale(salesCon.getIdLastSale());
                salesCon.addSaleDetails(detalle);
                itemsCon.decreaseStock(detalle.getIdItem(), detalle.getQuantity());
           }
           
           loadAccountSeat(sale.getSalesTotal());
           JOptionPane.showMessageDialog(null, "La venta ha sido agregada correctamente", "Exito", JOptionPane.INFORMATION_MESSAGE);
           
    int confirm = JOptionPane.showConfirmDialog(
                        null,
                        "¿Deseas imprimir la Factura?",
                        "Confirmación",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE
            );
             if(confirm == JOptionPane.YES_OPTION) { // Si el usua
                 Bill factura= new Bill();
                 factura.setIdSale(salesCon.getIdLastSale());
                 factura.setBillNumber(salesCon.getReceiptNumberBill());
                 factura.setBillDate(saleNode.getSale().getSaleDate());
                 factura.setBillState('V');
                 factura.setBillTotal(subTotal);
                 factura.setBillType(tipoFactura(saleNode.getCustomer()));
                 
                 salesCon.addBill(factura, factura.getIdSale());
            }
             
           limpiarVistaTotal();
           saleNode=new SaleNode();
           listaArticulos=new ArrayList<>();
           iniciarTabla();
        }
    }
    public char tipoFactura(Customer customer){
        
        if(customer.getClientType().equalsIgnoreCase("Responsable Inscripto") && customer.getIvaCondition().equalsIgnoreCase("Responsable Inscripto")){
            return 'A';
        }
        if(customer.getClientType().equalsIgnoreCase("Monotributista") && (customer.getIvaCondition().equalsIgnoreCase("Consumidor Final") ||  customer.getIvaCondition().equalsIgnoreCase("Monotributista"))){
            return 'B';
        }
        if(customer.getClientType().equalsIgnoreCase("Consumidor Final") && (customer.getIvaCondition().equalsIgnoreCase("Consumidor Final") ||  customer.getIvaCondition().equalsIgnoreCase("Monotributista"))){
            return 'C';
        }
        return 'C';
    }
     //Metodo para cancelar una venta//
    public void buttonCancelSale(ActionEvent e){
        if(e.getSource()==view.btnCancel){
            limpiarVistaTotal();
            saleNode= new SaleNode();
            iniciarTabla();
        }
    }
    //Metodo para validar el stock//
    public boolean validStock(Item item,int cantidad){
        
        return item.getStock()-cantidad >= 0;
    
    }
    
    //Metodo para el mensaje de stock minimo//
    public boolean stockMin(Item item, int cantidad){
        
        return item.getStock()-cantidad>item.getStockMin();
    }
    
  //Metodo para validar el ingreso antes de cargar en la base de datos las asientos contables//
    
    public boolean validBalanceMercaderia(int cantidad, Item item) throws SQLException{
            
            AccountConnection accountCon= new AccountConnection();
            Account account= accountCon.getAccountBox(131);
            if(account.getBalance()-(cantidad*item.getUnitPrice())<0){
                        JOptionPane.showMessageDialog(null, "La cuenta "+ account.getAccountName()+" no tiene suficiente saldo para realizar esta operacion", "Error", JOptionPane.ERROR_MESSAGE);
                        return false;
            }
            return true;
    }
      //Metodo para validar el ingreso antes de cargar en la base de datos las asientos contables//
     public boolean validBalanceVenta(double valorTotal) throws SQLException{
         
         if(saleNode.getSaleType().getType().equalsIgnoreCase("EFECTIVO")){
                AccountConnection accountCon= new AccountConnection();
                Account account= accountCon.getAccountBox(111);
                if(account.getBalance()-valorTotal<0){
                        JOptionPane.showMessageDialog(null, "La cuenta "+ account.getAccountName()+" no tiene suficiente saldo para realizar esta operacion", "Error", JOptionPane.ERROR_MESSAGE);
                        return false;
            }
         }
         
         if(saleNode.getSaleType().getType().equalsIgnoreCase("DEBITO")){
                AccountConnection accountCon= new AccountConnection();
                Account account= accountCon.getAccountBox(112);
                if(account.getBalance()-valorTotal<0){
                        JOptionPane.showMessageDialog(null, "La cuenta "+ account.getAccountName()+" no tiene suficiente saldo para realizar esta operacion", "Error", JOptionPane.ERROR_MESSAGE);
                        return false;
            }
         }
         
         if(saleNode.getSaleType().getType().equalsIgnoreCase("CREDITO")){
                AccountConnection accountCon= new AccountConnection();
                Account account= accountCon.getAccountBox(121);
                if(account.getBalance()-valorTotal<0){
                        JOptionPane.showMessageDialog(null, "La cuenta "+ account.getAccountName()+" no tiene suficiente saldo para realizar esta operacion", "Error", JOptionPane.ERROR_MESSAGE);
                        return false;
            }
         }
         
        return true;
     }
     //Metodo para actualizar la los asientosContables//
   public void  loadAccountSeat(double montoTotal ) throws SQLException, ClassNotFoundException, IOException{
       
        java.util.Date fechaActual = new Date();
        java.sql.Date fechaSQL = new java.sql.Date(fechaActual.getTime());
        List<Account> cuentasActualizar=new ArrayList<>();
        int idSeat=0;
        
        if(saleNode.getSaleType().getType().equalsIgnoreCase("EFECTIVO")){
                
                Account cuentaDebe=conAccount.getAccountBox(111);
                double nuevoMontoDebe=cuentaDebe.getBalance()-montoTotal;
                cuentaDebe.setBalance(nuevoMontoDebe);
        
                Account cuentaVenta=conAccount.getAccountBox(411);
                double nuevoMontoVenta=cuentaVenta.getBalance()+montoTotal;
                cuentaVenta.setBalance(nuevoMontoVenta);
        
                cuentasActualizar.add(cuentaDebe);
                cuentasActualizar.add(cuentaVenta);
       
                Seat seat =new Seat(
                   
                            conUsuario.getUserId(currentUser.getUserName()),
                            fechaSQL,
                            "Venta de Articulos"
                        );
           
                 idSeat=seatCon.addSeat(seat);

                AccountSeat debe= new AccountSeat(
                    idSeat,
                    "DEBER",
                    conAccount.getAccountBox(111).getIdAccount(),
                    montoTotal,    
                    nuevoMontoDebe
                );
                seatCon.addAccountSeat(debe);
               
                AccountSeat venta= new AccountSeat(

                    idSeat,
                    "HABER",
                    conAccount.getAccountBox(411).getIdAccount(),
                    montoTotal,    
                    nuevoMontoVenta
                );

                seatCon.addAccountSeat(venta);
  
              conAccount.actualizarSaldo(cuentasActualizar);
          }
        
        
        if(saleNode.getSaleType().getType().equalsIgnoreCase("DEBITO")){
                
                Account cuentaDebe=conAccount.getAccountBox(113);
                double nuevoMontoDebe=cuentaDebe.getBalance()-montoTotal;
                cuentaDebe.setBalance(nuevoMontoDebe);
        
                Account cuentaVenta=conAccount.getAccountBox(411);
                double nuevoMontoVenta=cuentaVenta.getBalance()+montoTotal;
                cuentaVenta.setBalance(nuevoMontoVenta);
        
                cuentasActualizar.add(cuentaDebe);
                cuentasActualizar.add(cuentaVenta);
       
                Seat seat =new Seat(
                   
                            conUsuario.getUserId(currentUser.getUserName()),
                            fechaSQL,
                            "Venta de Articulos"
                        );
           
                 idSeat=seatCon.addSeat(seat);

                AccountSeat debe= new AccountSeat(
                    idSeat,
                    "DEBER",
                    conAccount.getAccountBox(113).getIdAccount(),
                    montoTotal,    
                    nuevoMontoDebe
                );
                seatCon.addAccountSeat(debe);
               
                AccountSeat venta= new AccountSeat(

                    idSeat,
                    "HABER",
                    conAccount.getAccountBox(411).getIdAccount(),
                    montoTotal,    
                    nuevoMontoVenta
                );

                seatCon.addAccountSeat(venta);
  
              conAccount.actualizarSaldo(cuentasActualizar);
              
            }
        
         if(saleNode.getSaleType().getType().equalsIgnoreCase("CREDITO")){
         
                Account cuentaDebe=conAccount.getAccountBox(121);
                double nuevoMontoDebe=cuentaDebe.getBalance()-montoTotal;
                cuentaDebe.setBalance(nuevoMontoDebe);
        
                Account cuentaVenta=conAccount.getAccountBox(411);
                double nuevoMontoVenta=cuentaVenta.getBalance()+montoTotal;
                cuentaVenta.setBalance(nuevoMontoVenta);
        
                cuentasActualizar.add(cuentaDebe);
                cuentasActualizar.add(cuentaVenta);
       
                Seat seat =new Seat(
                   
                            conUsuario.getUserId(currentUser.getUserName()),
                            fechaSQL,
                            "Venta de Articulos"
                        );
           
                 idSeat=seatCon.addSeat(seat);

                AccountSeat debe= new AccountSeat(
                    idSeat,
                    "DEBER",
                    conAccount.getAccountBox(113).getIdAccount(),
                    montoTotal,    
                    nuevoMontoDebe
                );
                seatCon.addAccountSeat(debe);
               
                AccountSeat venta= new AccountSeat(

                    idSeat,
                    "HABER",
                    conAccount.getAccountBox(411).getIdAccount(),
                    montoTotal,    
                    nuevoMontoVenta
                );

                seatCon.addAccountSeat(venta);
  
              conAccount.actualizarSaldo(cuentasActualizar);
         }
         
         Account cuentaDebe=conAccount.getAccountBox(511);
         double nuevoMontoDebe=cuentaDebe.getBalance()+montoTotal;
         cuentaDebe.setBalance(nuevoMontoDebe);
        
         Account cuentaHaber=conAccount.getAccountBox(131);
         double nuevoMontoHaber=cuentaHaber.getBalance()-montoTotal;
         cuentaHaber.setBalance(nuevoMontoHaber);
        
         cuentasActualizar.add(cuentaDebe);
         cuentasActualizar.add(cuentaHaber);
         
                AccountSeat debe= new AccountSeat(
                    idSeat,
                    "DEBER",
                    conAccount.getAccountBox(511).getIdAccount(),
                    montoTotal,    
                    nuevoMontoDebe
                );
                seatCon.addAccountSeat(debe);
               
                AccountSeat venta= new AccountSeat(

                    idSeat,
                    "HABER",
                    conAccount.getAccountBox(131).getIdAccount(),
                    montoTotal,    
                    nuevoMontoHaber
                );

                seatCon.addAccountSeat(venta);
  
              conAccount.actualizarSaldo(cuentasActualizar);
         
        
       }
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        buttonCustomer(e);
        buttonItem(e);
        buttonExit(e);
        buttonPayment(e);
        buttonSalesReport(e);
        buttonBills(e);
        try {
            buttonAddSale(e);
        } catch (SQLException ex) {
            Logger.getLogger(SalesSystem.class.getName()).log(Level.SEVERE, null, ex);
        }
        buttonCancelSale(e);
        try {
            buttonSaveSale(e);
        } catch (SQLException ex) {
            Logger.getLogger(SalesSystem.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SalesSystem.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SalesSystem.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
    
}
