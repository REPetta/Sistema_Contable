
package Controller;

import ConnectionsBD.UserManagementConnection;
import Model.User;
import View.DetailsUser;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;


public class DetailsUserController implements ActionListener {
    //Atributos//
    private DetailsUser detailsUserView;
    private String rol;
    private UserManagementConnection userManagementConnection=new UserManagementConnection();
    private User user;
    int idUser;

    //Metodos//
    public DetailsUserController(String userName) throws ClassNotFoundException, SQLException, IOException{
        try{
            this.rol=userManagementConnection.getPerfil(userName);
        }catch(ClassNotFoundException | SQLException e){}
        try{
            this.idUser=userManagementConnection.getIdUser(userName);
            this.user=userManagementConnection.getUserColumns(idUser);
        }catch(ClassNotFoundException | SQLException e){}
        this.detailsUserView=new DetailsUser();
        this.detailsUserView.viewName.setText(user.getName());
        this.detailsUserView.viewLastName.setText(user.getLastName());
        this.detailsUserView.viewDni.setText(String.valueOf(user.getDni()));
        this.detailsUserView.viewUserName.setText(user.getUserName());
        this.detailsUserView.viewRol.setText(rol);
    }
    //Muestra la ventana//
    public void openDetailsUserView(){
        detailsUserView.setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
       
    }
    
}
