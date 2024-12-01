//Clase encarga del login del usuario en el sistema//
package Controller;


import Connection.UserConnection;
import Model.SingletonUser;
import Model.User;
import View.LoginView;
import static View.LoginView.blinkingFields;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class Login implements ActionListener{
    
    //Atributos//
    private final LoginView loginView;
    private SingletonUser instance;
    private MainMenu mainMenu;
    
   //Constructor//
    
    public Login(){
        loginView= new LoginView();
        inicializatedListeners();
    }
    
    //Metodos para inicializar listeners//
    public final void inicializatedListeners(){
        this.loginView.btnIngress.addActionListener(this);
    }
    
    //Metodo para ingresar en la aplicacion//
   public void buttonIngress(ActionEvent e) throws SQLException{
       if(e.getSource()==loginView.btnIngress){
           
            String userName=loginView.txtUser.getText();
            String pass=new String(loginView.txtPassword.getPassword());
            
            if(nonEmptyFields(userName,pass)){
                UserConnection con= new UserConnection();
                User user= con.getUserValid(userName);
                if(validateUser(user,pass)){
                    JOptionPane.showMessageDialog(null, "Bienvenido al Sistema "+ userName);
                    instance=SingletonUser.getInstance();
                    instance.setUserName(userName);
                    instance.setTasks(user.getTasks());
                    mainMenu= new MainMenu();
                    closeLoginView();
                    mainMenu.openMainMenuView();
                }
                loginView.txtUser.setText("");
                loginView.txtPassword.setText("");
            }
           
            loginView.txtUser.setText("");
            loginView.txtPassword.setText("");
       }
   }
   
   // Metodo para validar el usuario//
    public boolean validateUser(User user , String pass){        
        if(user==null){
            JOptionPane.showMessageDialog(null,"El usuario ingresado no es valido");
            blinkingFields(loginView.txtUser);
            return false;
        }
        if(!user.getPassword().equals(pass)){
            JOptionPane.showMessageDialog(null,"La contreseña ingresada no es correcta");
            blinkingFields(loginView.txtPassword);
            return false;
        }
        return true;
    }
    
    //Metodo para validar que no haya campos vacios en la interfaz del login//
    public boolean nonEmptyFields(String userName, String pass){
        if(userName==null || userName.isEmpty() ){
            if(!pass.isEmpty()){
                JOptionPane.showMessageDialog(null,"El campo NOMBRE DE USUARIO no puede estar vacio");
                blinkingFields(loginView.txtUser);
                return false;
                }else{
                    JOptionPane.showMessageDialog(null,"No puede haber campos vacios");
                    blinkingFields(loginView.txtUser);
                    blinkingFields(loginView.txtPassword);
                    return false;
                        }
                }
         if( pass==null || pass.isEmpty() ){
                   JOptionPane.showMessageDialog(null,"El campo CONTRASEÑA no puede estar vacio");
                   blinkingFields(loginView.txtPassword);
                   return false;
             }
        return true;
        }
    
    
    //Metodo para mostrar la ventana del login//
    public void openLoginView(){
        this.loginView.setVisible(true);
    }
    
    //Metodo para cerrar la ventana del login//
    public void closeLoginView(){
        this.loginView.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            buttonIngress(e);
        } catch (SQLException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    
}
