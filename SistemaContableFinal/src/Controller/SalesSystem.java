
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
    private final SalesSystemView view;
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
    public void buttonAddCustomer(ActionEvent e){
        if(e.getSource()==view.btnCustomer){
            closeSalesSystemView();
           customerManagement=new CustomerManagement();
            customerManagement.openCustomerManagementView();
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
        buttonAddCustomer(e);
        buttonExit(e);
    }

    
    
}
