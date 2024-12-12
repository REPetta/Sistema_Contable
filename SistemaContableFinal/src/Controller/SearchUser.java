//Clase de para el comportamiento de la clase encargada de la vista buscar usuario//

package Controller;

import Connection.UserConnection;
import Model.SingletonUser;
import Model.User;
import static View.LoginView.blinkingFields;
import View.SearchUserView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

class SearchUser implements ActionListener {
    
    private MainMenu mainMenu;
    private final SingletonUser currentUser= SingletonUser.getInstance();
    private final SearchUserView searchUserView;
    private DetailsUser detailsUser;
    
    //Constructor//
    public SearchUser(){
        
        searchUserView= new SearchUserView();
        initializeListeners();
        this.searchUserView.setTitle("Buscar Usuario"+"-"+currentUser.getUserName().toUpperCase()+"("+currentUser.getRol()+")");
    }
   
     //Metodo para inicializar los listener con los botones//
    public final void initializeListeners(){
        
        this.searchUserView.btnSearch.addActionListener(this);
        this.searchUserView.btnBack.addActionListener(this);
        
    }
     //Metodo para abrir la ventana de buscar  usuario//
    public void openSearchUserView(){
        this.searchUserView.setVisible(true);
    }
    
    //Metodo para cerrar la ventana de buscar usuario//
    public void closeSearchUserView(){
        this.searchUserView.dispose();
    }
    
    //Metodo para el comportamiento del boton buscar//
    public void buttonSearch(ActionEvent e) throws SQLException{
        if(e.getSource()==searchUserView.btnSearch){
            String nameToSearch=this.searchUserView.txtUserName.getText().trim();
            if(validateFields(nameToSearch)){
                UserConnection con= new UserConnection();
                User userToSearch=con.getUserValid(nameToSearch);
                if(userToSearch!=null){
                    closeSearchUserView();
                    detailsUser= new DetailsUser(userToSearch);
                    detailsUser.openDetailsUserView();
                }else{
                    JOptionPane.showMessageDialog(
                                    null,
                                    "El usuario buscado no existe \n",
                                    "Error",
                                    JOptionPane.ERROR_MESSAGE
                                    );
                                    fieldsClear();
                        }
                    }
                }
    }
    //Metodo para validar campos//
    
     public boolean validateFields(String userName){
         if(userName.equalsIgnoreCase("")){
             JOptionPane.showMessageDialog(
                                null,
                                "El nombre de usuario no puede estar en blanco \n",
                                 "Error",
                                 JOptionPane.ERROR_MESSAGE
                                );
                                fieldsClear();
                                blinkingFields(searchUserView.txtUserName);
             return false;
         }else{
             return true;
         }
     }
     
    //Metodo para limpiar los campos//
     public void fieldsClear(){
         this.searchUserView.txtUserName.setText("");
     };
     
    //Metodo para el comportamiento del boton volver//
    public void buttonBack(ActionEvent e){
        if(e.getSource()==searchUserView.btnBack){
                closeSearchUserView();
                mainMenu= new MainMenu();
                mainMenu.openMainMenuView();
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        buttonBack(e);
        try {
            buttonSearch(e);
        } catch (SQLException ex) {
            Logger.getLogger(SearchUser.class.getName()).log(Level.SEVERE, null, ex);
        }
}
}
