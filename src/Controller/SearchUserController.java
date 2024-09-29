//Clase encargada del comportamiento de "Buscar Usuario"//
package Controller;

import View.SearchUser;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class SearchUserController implements ActionListener {
    //Atributos//
    private SearchUser searchUserView=new SearchUser();
    private MainMenuController mainMenuController;
    private DetailsUserController detailsUserView;
    //Metodos//
    public SearchUserController(){//Conecta el boton Volver con la Clase
        this.searchUserView.btnBack.addActionListener(this);
        this.searchUserView.btnSearch.addActionListener(this);
        }
    public void openSearchUserView(){//Muestra la ventan//
            searchUserView.setVisible(true);
        }
    public void closeSearchUserView(){//Cierra la ventana//
        searchUserView.dispose();
        }
    public void buttonBack(ActionEvent e){//Metodo que le da al boton volver la accion de salir de la ventana Buscar Usuario y volver al Menu Principal//
       if(e.getSource()==searchUserView.btnBack){
           closeSearchUserView();
           mainMenuController=new MainMenuController();
           mainMenuController.openMainMenuView();
       }
   }
    public void buttonSearch(ActionEvent e) throws ClassNotFoundException, SQLException, IOException{
        if(e.getSource()==searchUserView.btnSearch){
            String userName=searchUserView.txtUserName.getText();
            detailsUserView= new DetailsUserController(userName);
            detailsUserView.openDetailsUserView();
            searchUserView.txtUserName.setText("");
            
        }
   }
    @Override
    public void actionPerformed(ActionEvent e) {
       buttonBack(e);
        try {
            buttonSearch(e);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SearchUserController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(SearchUserController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SearchUserController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
