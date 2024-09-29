//Clase que se encargar del comportamiento de la vista Usuario a Dar de Baja//
package Controller;

import ConnectionsBD.UserManagementConnection;
import Model.User;
import View.DetailsUserDelete;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;


public class DetailsUserDeleteController implements ActionListener {
    //Atributos//
    private DetailsUserDelete detailsUserView;
    private User currentUser=User.getInstancia();
    private User user;
    private UserManagementConnection userManagementConnection=new UserManagementConnection();

    //Metodos//
    public DetailsUserDeleteController(User user) throws ClassNotFoundException, SQLException, IOException{
        this.user=user;
        this.detailsUserView=new DetailsUserDelete();
        this.detailsUserView.btnNot.addActionListener(this);
        this.detailsUserView.btnYes.addActionListener(this);
        this.detailsUserView.setTitle("Usuario a Dar de Baja"+" - "+currentUser.getUserName()+" ( "+currentUser.getRol().substring(0, 1).toUpperCase()+currentUser.getRol().substring(1).toLowerCase()+ " ) " );
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
    //Metodo para el boton no, que cierra la ventana//
    public void buttonNot(ActionEvent e){
        if(e.getSource()==detailsUserView.btnNot){
            this.detailsUserView.dispose();
        }
    }
    public void buttonYes(ActionEvent e) throws SQLException, ClassNotFoundException, IOException{
        if(e.getSource()==detailsUserView.btnYes){
            userManagementConnection.cancelUser(user);
            JOptionPane.showMessageDialog(null,"El usuario "+user.getUserName()+" ha sido dado de baja correctamente");
            this.detailsUserView.dispose();
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
       buttonNot(e);
        try {
            buttonYes(e);
        } catch (SQLException ex) {
            Logger.getLogger(DetailsUserDeleteController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DetailsUserDeleteController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DetailsUserDeleteController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
