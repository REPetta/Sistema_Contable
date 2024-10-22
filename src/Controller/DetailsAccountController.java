//Metodo para el conportamiento de la vista de Detalles de la Cuenta//
package Controller;

import Model.User;
import View.DetailsAccount;


public class DetailsAccountController {
    //Atributos//
    private DetailsAccount detailsAccountView;
    private User currentUser=User.getInstancia();
    private ChartAccountsController chartAccounts=ChartAccountsController.getInstancia();
   
    //Contructor//
    public DetailsAccountController() {
            this.detailsAccountView=new DetailsAccount();
            this.detailsAccountView.setTitle("Detalles"+" - "+currentUser.getUserName()+" ( "+currentUser.getRol().substring(0, 1).toUpperCase()+currentUser.getRol().substring(1).toLowerCase()+ " ) " );
    }
    //Metodos//
    //Muestra la ventana//
    public void openDetailsUserView(){
        detailsAccountView.setVisible(true);
}
}
