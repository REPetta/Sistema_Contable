
package Controller;

import View.SearchUser;
import View.MainMenu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SearchUserController implements ActionListener {
    //Atributos//
    private SearchUser searchUserView=new SearchUser();
    private MainMenu mainMenuView=new MainMenu();
    //Metodos//
    public SearchUserController(){//Conecta el boton Volver con la Clase
        this.searchUserView.btnBack.addActionListener(this);
        }
    public void openSearchUserView(){
            searchUserView.setVisible(true);
        }
    public void closeSearchUserView(){
        searchUserView.dispose();
        }
     /* Todos los metodos buttonBack despliegan la vista pero no traen sus controladores
    public void buttonBack(ActionEvent e){//Metodo que le da al boton volver la accion de salir de la ventana Buscar Usuario y volver al Menu Principal//
       if(e.getSource()==searchUserView.btnBack){
           closeSearchUserView();
           mainMenuView.setVisible(true);
       }
   }
    */
    @Override
    public void actionPerformed(ActionEvent e) {
       // buttonBack(e);
    }
    
}
