
package Controller;

import Model.SingletonUser;
import View.CustomerManagementView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class CustomerManagement implements ActionListener {
     private final SingletonUser currentUser= SingletonUser.getInstance();
     private SalesSystem salesSystem;
     private AddCustomer addCustomer;
     private ClientManagement clientManagement;
     private final CustomerManagementView view;

     
     public CustomerManagement(){
          view=new CustomerManagementView();
          this.view.setTitle("Clientes"+"-"+currentUser.getUserName().toUpperCase()+"("+currentUser.getRol()+")");
          initializeListeners();
}
     //Metodo  para inicializar los listener con los botones//
    public final void initializeListeners(){
        this.view.btnAddCustomer.addActionListener(this);
        this.view.btnManCustomer.addActionListener(this);
        this.view.btnBack.addActionListener(this);
    }
     //Metodo para abri la ventana//
   public void openCustomerManagementView(){
       this.view.setVisible(true);
   }
   //Metodo para cerrar la ventana//
   public void closeCustomerManagementView(){
       this.view.dispose();
   }
   //Metodo para el boton agregar cliente//
   public void buttonAddCustomer(ActionEvent e){
       if(e.getSource()==view.btnAddCustomer){
           closeCustomerManagementView();
           addCustomer= new AddCustomer();
           addCustomer.openAddCustomerView();
       }
   }
   //Metodo para el boton Gestionar Cliente//
   public void buttonManagementCustomer(ActionEvent e){
       if(e.getSource()==view.btnManCustomer){
           closeCustomerManagementView();
           clientManagement= new ClientManagement();
           clientManagement.openClientManagementView();
       }
   }
    //Metodo para salir
    public void buttonExit(ActionEvent e){
        if(e.getSource()==view.btnBack){
           closeCustomerManagementView();
           salesSystem=new SalesSystem();
           salesSystem.openSalesSystemView();
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        buttonAddCustomer(e);
        buttonManagementCustomer(e);
        buttonExit(e);
    }
}
