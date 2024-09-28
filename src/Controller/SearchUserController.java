
package Controller;

import View.SearchUser;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class SearchUserController implements ActionListener {
    //Atributos//
    private SearchUser searchUserView=new SearchUser();
    private MainMenuController mainMenuController;
    private SingletonController singletonController;
    //Metodos//
    public SearchUserController(SingletonController singletonController){//Conecta el boton Volver con la Clase
        this.singletonController=singletonController;
        this.searchUserView.btnBack.addActionListener(this);
        }
    public void openSearchUserView(){
            searchUserView.setVisible(true);
        }
    public void closeSearchUserView(){
        searchUserView.dispose();
        }
    public void buttonBack(ActionEvent e){//Metodo que le da al boton volver la accion de salir de la ventana Buscar Usuario y volver al Menu Principal//
       if(e.getSource()==searchUserView.btnBack){
           closeSearchUserView();
           mainMenuController=new MainMenuController(singletonController);
           mainMenuController.openMainMenuView();
       }
   }
   
    @Override
    public void actionPerformed(ActionEvent e) {
       buttonBack(e);
    }
    
}
