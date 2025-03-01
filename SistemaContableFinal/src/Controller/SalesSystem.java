
package Controller;

import Model.SingletonUser;
import View.SalesSystemView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SalesSystem implements ActionListener {
    //Atributos//
    private MainMenu mainMenu;
    private final SingletonUser currentUser= SingletonUser.getInstance();
    private CustomerManagement customerManagement;
    private ItemsManagement itemsManagement;
    private final SalesSystemView view;
    private PaymentManagement paymentManagement;
    private SalesReport salesReport;
    private BillManagement billManagement;
    //Constructor//
    public SalesSystem(){
        view=new SalesSystemView();
        initializeListeners();
        this.view.setTitle("Modulo de Ventas"+"-"+currentUser.getUserName().toUpperCase()+"("+currentUser.getRol()+")");
//        displayBasedRol(currentUser);
        }
    //Metodo para inicializar//
    public final void initializeListeners(){
        this.view.btnSalir.addActionListener(this);
        this.view.btnItems.addActionListener(this);
        this.view.btnCustomer.addActionListener(this);
        this.view.btnFac.addActionListener(this);
        this.view.btnSales.addActionListener(this);
        this.view.btnPayment.addActionListener(this);
    }
//    //Metodo para ocultar ciertos botones en funcion del rol del usuario//
//    public final void displayBasedRol(SingletonUser current){
//        if(currentUser.getRol().equalsIgnoreCase("Contador")){
//            this.view.btnAddUser.setVisible(false);
//            this.view.btnSearchUser.setVisible(false);
//            this.view.btnSeats.setVisible(false);
//        }
//        if(currentUser.getRol().equalsIgnoreCase("Vendedor")){
//            this.view.btnAddUser.setVisible(false);
//            this.view.btnSearchUser.setVisible(false);
//            this.view.btnAddSeat.setVisible(false);
//            this.view.btnDiaryBook.setVisible(false);
//            this.view.btnLedger.setVisible(false);
//            this.view.btnShowAccounts.setVisible(false);
//            this.view.btnSeats.setVisible(false);
//        }
//    }
    //Metodo para abrir la ventana //
    public void openSalesSystemView(){
        this.view.setVisible(true);
    }
    
    //Metodo para cerrar la ventana //
    public void closeSalesSystemView(){
        this.view.dispose();
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
    
    @Override
    public void actionPerformed(ActionEvent e) {
        buttonCustomer(e);
        buttonItem(e);
        buttonExit(e);
        buttonPayment(e);
        buttonSalesReport(e);
        buttonBills(e);
    }

    
    
}
