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
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

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
            String [] fields={
                loginView.txtUser.getText(),
                new String(loginView.txtPassword.getPassword())
            };
                   
            if(nonEmptyFields(fields)){
                UserConnection con= new UserConnection();
                User user= con.getUserValid(fields[0]);
                if(validateUser(user,fields[1])){
                    JOptionPane.showMessageDialog(null, "Bienvenido al Sistema "+ user.getUserName(), "Confirmacion" , JOptionPane.INFORMATION_MESSAGE);
                    instance=SingletonUser.getInstance();
                    instance.setUserName(user.getUserName());
                    instance.setTasks(user.getTasks());
                    mainMenu= new MainMenu();
                    closeLoginView();
                    mainMenu.openMainMenuView();
                }
               fieldsClear();
            }
           
            fieldsClear();
       }
   }
   
   //Metodo para limpiar los campos//
   public void fieldsClear(){
       loginView.txtUser.setText("");
       loginView.txtPassword.setText("");
   }
   
   // Metodo para validar el usuario//
    public boolean validateUser(User user , String pass){
       
        if(user==null){
            JOptionPane.showMessageDialog(
                null,
                "El usuario ingresado no es valido\"\n" ,
                "Error",
                JOptionPane.ERROR_MESSAGE
             );
            blinkingFields(loginView.txtUser);
            blinkingFields(loginView.txtPassword);
            return false;
        }
        if(!user.getPassword().equals(pass)){
            JOptionPane.showMessageDialog(
                null,
                "La contraseña ingresada no es valida \n" ,
                "Error",
                JOptionPane.ERROR_MESSAGE
             );
            blinkingFields(loginView.txtPassword);
            return false;
        }
        return true;
    }
    
    //Metodo para validar que no haya campos vacios en la interfaz del login//
    public boolean nonEmptyFields(String[] fields){
       String[] fieldNames={"Nombre de Usuario","Contraseña"};
       StringBuilder bugs= new StringBuilder();
       JTextField[] jtextFields={loginView.txtUser, loginView.txtPassword};
       boolean hasBugs= false;
       
       for (int i=0; i<fields.length; i++){
             if(fields[i].isEmpty()){
                 bugs.append("-El campo ").append(fieldNames[i]).append(" no puede estar vacio.\n ");
                 blinkingFields(jtextFields[i]);
                 hasBugs= true;
             }
         }
         if(hasBugs){
             JOptionPane.showMessageDialog(
                null,
                "Se han encontrado los siguientes problemas:\n" + bugs,
                "Error",
                JOptionPane.ERROR_MESSAGE
             );
             
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
