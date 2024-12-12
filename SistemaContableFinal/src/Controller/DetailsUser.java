//Clase encargada del control de la vista Detalles de Usuario//
package Controller;

import Connection.UserConnection;
import Model.SingletonUser;
import Model.User;
import View.DetailsUserView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public final class DetailsUser implements ActionListener{
    
    private final DetailsUserView detailsUserView;
    private MainMenu mainMenu;
    private SearchUser userSearch;
    private final SingletonUser currentUser= SingletonUser.getInstance();
    private final User searchUser;
   
    //Constructor//
    public DetailsUser(User user){
        searchUser=user;
        detailsUserView=new DetailsUserView();
        initializeListeners();
        loadFields(searchUser);
        this.detailsUserView.setTitle("Detalles de Usuario"+"-"+currentUser.getUserName().toUpperCase()+"("+currentUser.getRol()+")");
    }
    
    //Metodo para cargar los campos//
    public void loadFields(User user){
    
        detailsUserView.viewName.setText(user.getName());
        detailsUserView.viewLastName.setText(user.getLastName());
        detailsUserView.viewDni.setText( String.valueOf(user.getDni()));
        detailsUserView.viewUserName.setText(user.getUserName());
        if(user.getTasks().contains("agregar_usuario")){
            detailsUserView.viewRol.setText("Administrador");
        }else{
            detailsUserView.viewRol.setText("Estandar");
        }
    }
    
    //Metodo para inicializar los listener con los botones//
    public final void initializeListeners(){
        detailsUserView.btnDel.addActionListener(this);
        detailsUserView.btnBack.addActionListener(this);
    }
    
        //Metodo para abrir la ventana del detalles de usuario/
    public void openDetailsUserView(){
        this.detailsUserView.setVisible(true);
    }
    
    //Metodo para cerrar la ventana de detalles de usuario//
    public void closeDetailsUserView(){
        this.detailsUserView.dispose();
    }
    
    //Metodo para la funcionalidad del boton borrar//
    public void buttonDel(ActionEvent e) throws SQLException{
        if(e.getSource()==detailsUserView.btnDel){
         int confirm = JOptionPane.showConfirmDialog(
                        null,
                        "¿Estás seguro de que deseas dar de baja a este usuario?",
                        "Confirmación",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE
            );
                  if(confirm == JOptionPane.YES_OPTION) { // Si el usuario confirma, intenta agregarlo//
                       UserConnection con= new UserConnection();
                       boolean success=con.delUser(searchUser);
                       if(success){
                                JOptionPane.showMessageDialog(
                                null,
                                "El usuario ha sido dado de baja exitosamente  \n",
                                 "Confirmacion",
                                 JOptionPane.INFORMATION_MESSAGE
                                );
                                 closeDetailsUserView();
                                userSearch=new SearchUser();
                                userSearch.openSearchUserView();
                       }else{
                                JOptionPane.showMessageDialog(
                                null,
                                "El usuario no ha podido ser dado de baja \n",
                                 "Error",
                                 JOptionPane.ERROR_MESSAGE
                                );
                                closeDetailsUserView();
                                userSearch=new SearchUser();
                                userSearch.openSearchUserView();
                       }
                  }
                  
        }
    }
    
    //Metodo para la funcionalidad del boton volver//
    public void buttonBack(ActionEvent e){
        if(e.getSource()==detailsUserView.btnBack){
                closeDetailsUserView();
                userSearch=new SearchUser();
                userSearch.openSearchUserView();
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
            buttonBack(e);
        try {
            buttonDel(e);
        } catch (SQLException ex) {
            Logger.getLogger(DetailsUser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
