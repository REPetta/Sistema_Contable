//Clase encargada del comportamiento de "Buscar Usuario"//
package Controller;

import ConnectionsBD.UserManagementConnection;
import Model.User;
import View.SearchUser;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;



public class SearchUserController implements ActionListener {
    //Atributos//
    private SearchUser searchUserView=new SearchUser();
    private MainMenuController mainMenuController;
    private DetailsUserController detailsUserView;
    private User currentUser=User.getInstancia();
    private UserManagementConnection userManagementConnection=new UserManagementConnection();
    //Metodos//
    public SearchUserController(){//Conecta el boton Volver con la Clase
        this.searchUserView.setTitle("Buscar Usuario"+" - "+currentUser.getUserName()+" ( "+currentUser.getRol().substring(0, 1).toUpperCase()+currentUser.getRol().substring(1).toLowerCase()+ " ) " );//La parte de getRol()... Se asegura que la primera letra sea mayuscuala y el resto minuscula//
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
            String userName=searchUserView.txtUserName.getText().trim();//Elimina espacios en blanco//
            if(!userName.isEmpty()){//Si no esta vacio el campo//
                User user;
                user=userManagementConnection.getUserColumns(userName);
                //Si el usuario no esta vacio y es valido//
                if(user!=null && "alta".equals(user.getState())){
                        detailsUserView= new DetailsUserController(user);
                        detailsUserView.openDetailsUserView();
                        searchUserView.txtUserName.setText("");
                }
                //En caso contrario//
                else{
                    JOptionPane.showMessageDialog(null,"Error el usuario no existe");
                    searchUserView.txtUserName.setText("");
                }
            }
            else{
              JOptionPane.showMessageDialog(null,"Error no sea ingresado ningun nombre de usuario");
              searchUserView.txtUserName.setText("");
            }
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
