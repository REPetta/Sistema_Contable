//Clase encargada del comportamiento de  la vista Dar de Baja a Usuario//
package Controller;

import ConnectionsBD.UserManagementConnection;
import Model.User;
import View.DeleteUser;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;


public class DeleteUserController implements ActionListener{
    //Atributos//
    private DeleteUser deleteUserView=new DeleteUser();
    private DetailsUserDeleteController detailsUserDeleteView;
    private MainMenuController mainMenuController;
    private User currentUser=User.getInstancia();
    private UserManagementConnection userManagementConnection=new UserManagementConnection();
    //Metodos//
    public DeleteUserController(){//Conecta el boton Volver con la Clase//
        this.deleteUserView.setTitle("Dar de Baja Usuario"+" - "+currentUser.getUserName()+" ( "+currentUser.getRol().substring(0, 1).toUpperCase()+currentUser.getRol().substring(1).toLowerCase()+ " ) " );
        this.deleteUserView.btnBack.addActionListener(this);
        this.deleteUserView.btnDel.addActionListener(this);
    }
    public void openDeleteUserView(){//Muestra la ventana//
        deleteUserView.setVisible(true);
    }
    public void closeDeleteUserView(){//Cierra la ventana//
        deleteUserView.dispose();
    }
    public void buttonBack(ActionEvent e){//Metodo que le da al boton volver la accion de salir de la ventana Dar de Baja Usuario y volver al Menu Principal//
       if(e.getSource()==deleteUserView.btnBack){
           closeDeleteUserView();
           mainMenuController=new MainMenuController();
           mainMenuController.openMainMenuView();
           
       }
   }
    public void buttonDel(ActionEvent e) throws SQLException, ClassNotFoundException, IOException{
        if(e.getSource()==deleteUserView.btnDel){
            String userName=deleteUserView.txtUserName.getText().trim();//Elimina espacios en blanco//
            if(!userName.isEmpty()){//Si no esta vacio el campo//
                User user;
                user=userManagementConnection.getUserColumns(userName);
                //Si el usuario no esta vacio y es valido//
                if(user!=null && "alta".equals(user.getState())){
                        detailsUserDeleteView= new DetailsUserDeleteController(user);
                        detailsUserDeleteView.openDetailsUserView();
                        deleteUserView.txtUserName.setText("");
                }
                //En caso contrario//
                else{
                    JOptionPane.showMessageDialog(null,"Error el usuario no existe");
                    deleteUserView.txtUserName.setText("");
                }
            }
            else{
              JOptionPane.showMessageDialog(null,"Error no sea ingresado ningun nombre de usuario");
              deleteUserView.txtUserName.setText("");
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        buttonBack(e);
        try {
            buttonDel(e);
        } catch (SQLException ex) {
            Logger.getLogger(DeleteUserController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DeleteUserController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DeleteUserController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
