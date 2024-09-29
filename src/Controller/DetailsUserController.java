
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
    private User currentUser=User.getInstancia();

    //Metodos//
    public DetailsUserController(User user) throws ClassNotFoundException, SQLException, IOException{
        this.detailsUserView=new DetailsUser(); 
        this.detailsUserView.setTitle("Resultados de la Busqueda"+" - "+currentUser.getUserName()+" ( "+currentUser.getRol().substring(0, 1).toUpperCase()+currentUser.getRol().substring(1).toLowerCase()+ " ) " );
        this.detailsUserView.viewName.setText(user.getName());
        this.detailsUserView.viewLastName.setText(user.getLastName());
        this.detailsUserView.viewDni.setText(String.valueOf(user.getDni()));
        this.detailsUserView.viewUserName.setText(user.getUserName());
        this.detailsUserView.viewRol.setText((user.getRol().substring(0, 1).toUpperCase()+user.getRol().substring(1).toLowerCase()));
        }
    //Muestra la ventana//
    public void openDetailsUserView(){
        detailsUserView.setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
       
    }
    
}
