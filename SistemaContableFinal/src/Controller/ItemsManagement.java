
package Controller;

import Model.SingletonUser;
import View.ItemsManagementView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class ItemsManagement implements ActionListener{
    
    private final SingletonUser currentUser= SingletonUser.getInstance();
    private SalesSystem salesSystem;
    private final ItemsManagementView view;
    private AddItem addItem;
    private ManagementStock managementStock;

    public ItemsManagement () {
        this.view = new ItemsManagementView();
        this.view.setTitle("Articulos"+"-"+currentUser.getUserName().toUpperCase()+"("+currentUser.getRol()+")");
        initializeListeners();

    }
    
    public final void  initializeListeners(){
        this.view.btnAddItem.addActionListener(this);
        this.view.btnManStock.addActionListener(this);
        this.view.btnBack.addActionListener(this);
    }
      //Metodo para abri la ventana//
   public void openItemsManagementView(){
       this.view.setVisible(true);
   }
   //Metodo para cerrar la ventana//
   public void closeItemsManagementView(){
       this.view.dispose();
   }
   //Boton agregar item//
    public void buttonAddItem(ActionEvent e){
        if(e.getSource()==view.btnAddItem){
            closeItemsManagementView();
            addItem=new AddItem();
            addItem.openAddItemView();
        } 
    }
    //Boton Gestionar Stock//
    public void buttonManagementStock(ActionEvent e){
        if(e.getSource()==view.btnManStock){
            closeItemsManagementView();
            managementStock=new ManagementStock();
            managementStock.openManagementStockView();
        } 
    }
    //Boton salir//
    public void buttonExit(ActionEvent e){
        if(e.getSource()==view.btnBack){
            closeItemsManagementView();
            salesSystem=new SalesSystem();
            salesSystem.openSalesSystemView();
        } 
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        buttonExit(e);
        buttonManagementStock(e);
        buttonAddItem(e);
    }
    
    
}
