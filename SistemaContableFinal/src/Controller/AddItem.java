
package Controller;

import Model.SingletonUser;
import View.AddItemView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class AddItem implements ActionListener {
    
    private final SingletonUser currentUser= SingletonUser.getInstance();
    private final AddItemView view;
    private ItemsManagement itemsManagement;
    
    public AddItem() {
        view=new AddItemView();
         this.view.setTitle("Agregar Articulo"+"-"+currentUser.getUserName().toUpperCase()+"("+currentUser.getRol()+")");
         initializeListeners();
    }
    //Metodo para los listeners//
    public final void initializeListeners(){
            this.view.btnAddItem.addActionListener(this);
            this.view.btnExit.addActionListener(this);
    }
    //Metodo para abrir//
    public void openAddItemView(){
       this.view.setVisible(true);
   }
   //Metodo para cerrar la ventana//
   public void closeAddItemView(){
       this.view.dispose();
   }
   public void buttonExit(ActionEvent e){
       if(e.getSource()==view.btnExit){
           closeAddItemView();
           itemsManagement= new  ItemsManagement();
           itemsManagement.openItemsManagementView();
       }
   }
    @Override
    public void actionPerformed(ActionEvent e) {
        buttonExit(e);
    }
    
}
