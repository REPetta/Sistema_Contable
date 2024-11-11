//Clase encargada del  comporamiento de la vista de ventas
package Controller;

import Model.User;
import View.SalesSystem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class SalesController implements ActionListener {
    
    private SalesSystem ventasView= new SalesSystem();
    private MainMenuController mainMenuController;
    private User currentUser=User.getInstancia();
    
    public SalesController(){
        this.ventasView.setTitle("Area de Ventas"+" - "+currentUser.getUserName()+" ( "+currentUser.getRol().substring(0, 1).toUpperCase()+currentUser.getRol().substring(1).toLowerCase()+ " ) " );
        this.ventasView.btnSalir.addActionListener(this);
    }
    
     public void openView(){//Muestra la ventana//
        ventasView.setVisible(true);
    }
    public void closeView(){//Cierra la ventana//
        ventasView.dispose();
    }
    
    public void buttonBack(ActionEvent e){//Metodo que le da al boton volver la accion de salir de la ventana Agregar Usuario y volver al Menu Principal//
       if(e.getSource()==ventasView.btnSalir){
           closeView();
           mainMenuController=new MainMenuController();
           mainMenuController.openMainMenuView();
       }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        buttonBack(e);
    }
    
    
    
}
