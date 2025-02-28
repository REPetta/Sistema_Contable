
package Controller;

import Model.SingletonUser;
import View.PaymentManagementView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class PaymentManagement implements ActionListener{
    
    private final SingletonUser currentUser= SingletonUser.getInstance();
    private SalesSystem salesSystem;
    private final PaymentManagementView view;
    private AddPaymentMethod addPayMet;
    private ManagementPayment manPay;
    
    public PaymentManagement() {
        view= new PaymentManagementView();
        this.view.setTitle("Metodos de Pago"+"-"+currentUser.getUserName().toUpperCase()+"("+currentUser.getRol()+")");
        initializeListeners();
    }
    //Metodo  para inicializar los listener con los botones//
    public final void initializeListeners(){
        this.view.btnAddMethod.addActionListener(this);
        this.view.btnManMethod.addActionListener(this);
        this.view.btnBack.addActionListener(this);
    }
     //Metodo para abri la ventana//
   public void openView(){
       this.view.setVisible(true);
   }
   //Metodo para cerrar la ventana//
   public void closeView(){
       this.view.dispose();
   }
   //Metodo para el boton agregar metodo//
   public void buttonAdd(ActionEvent e){
        if(e.getSource()==view.btnAddMethod){
           closeView();
           addPayMet=new AddPaymentMethod();
           addPayMet.openView();
        }
    }
   //Metodo para el boton gestionar metodos//
    public void buttonManagement(ActionEvent e){
        if(e.getSource()==view.btnManMethod){
           closeView();
           manPay=new ManagementPayment();
           manPay.openView();
        }
    }
   //Metodo para salir
    public void buttonExit(ActionEvent e){
        if(e.getSource()==view.btnBack){
           closeView();
           salesSystem=new SalesSystem();
           salesSystem.openSalesSystemView();
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        buttonExit(e);
        buttonManagement(e);
        buttonAdd(e);
    }
    
    
    
}
