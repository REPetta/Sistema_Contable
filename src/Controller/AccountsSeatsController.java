//Clase en encargada del comportamiento de la vista AccountsSeats//
package Controller;

import Model.User;
import View.AccountsSeats;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AccountsSeatsController implements ActionListener {
    //Atributos//
    private AccountsSeats accountsSeatsView= new AccountsSeats();
    private MainMenuController mainMenuControllerView;
    private User currentUser=User.getInstancia();
    //Contructor//
    public AccountsSeatsController(){
        this.accountsSeatsView.setTitle("Buscar Asiento"+" - "+currentUser.getUserName()+" ( "+currentUser.getRol().substring(0, 1).toUpperCase()+currentUser.getRol().substring(1).toLowerCase()+ " ) " );
        this.accountsSeatsView.btnSearch.addActionListener(this);
        this.accountsSeatsView.btnExit.addActionListener(this);
    }
    //Metodos//
    public void openAccountsSeatsControllerView(){
        accountsSeatsView.setVisible(true);
    }
    public void closeAccountsSeatsControllerView(){
        accountsSeatsView.dispose();
    }
   public void buttonExit(ActionEvent e){//Metodo que le da al boton salir  la accion de salir de la ventana buscar asiento y volver al Menu Principal//
       if(e.getSource()==accountsSeatsView.btnExit){
          closeAccountsSeatsControllerView();
          mainMenuControllerView= new MainMenuController();
          mainMenuControllerView.openMainMenuView();
       }
   }
    @Override
    public void actionPerformed(ActionEvent e) {
        buttonExit(e);
    }
    
}
