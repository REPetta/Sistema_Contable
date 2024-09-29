//Clase encargada del comportamiento del Login//
package Controller;


import ConnectionsBD.LoginConnection;
import Model.User;
import View.Login;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;


public class LoginController implements ActionListener{//Implementa la interfaz ActionListener para manejar eventos de accion//
    //Atributos//
    private Login loginView=new Login();//Crea una instancia de la clase Login//
    private MainMenuController mainMenuController;
    private User instanceUser=User.getInstancia();
    private LoginConnection loginConnection=new LoginConnection();
    //Metodos//
    
    public LoginController(){
        this.loginView.btnIngress.addActionListener(this);//Conecta el boton ingresar del Login con el LoginController//
    }
    
    
    public void openViewLogin(){//Metodo para hacer visible la ventana del Login//
        loginView.setVisible(true);
    }
    public void loginIngress(ActionEvent e) throws ClassNotFoundException, SQLException, IOException{
        if(e.getSource()==loginView.btnIngress){//Si se detecta un click en el boton Ingresar ejecuta el bloque//
            LoginConnection con= new LoginConnection();//Crea una instancia de LoginConnection//
            String user=loginView.txtUser.getText();//Guarda el nombre de usuario ingresado//
            String password=String.valueOf(loginView.txtPassword.getPassword());//Guarda la contraseña ingresada//
            if(con.validateUser(user, password)==true){//Si el usuario es valido//
                //Inicializa la instancia del usuario actual//
                User userNew=loginConnection.instanceUserGet(user);
                instanceUser.setDni(userNew.getDni());
                instanceUser.setName(userNew.getName());
                instanceUser.setLastName(userNew.getLastName());
                instanceUser.setUserName(userNew.getUserName());
                instanceUser.setRol(userNew.getRol());
                instanceUser.setTasks(userNew.getTasks());
                
                JOptionPane.showMessageDialog(null,"<html><center>Buenos Dias\n<center>Bienvenido al sistema<html><center>");//Despliga mensaje de bienvenida//
                mainMenuController= new MainMenuController();
                mainMenuController.openMainMenuView();
                loginView.dispose();//Cierra la interfaz grafica del Login//
            }
            else{//Si el usuario no es valido//
                JOptionPane.showMessageDialog(null,"<html><center>Usuario y/o Contraseña incorrectas\n<center>Vuelva a intentarlo<html><center>");//Despliga mensaje de error//
                loginView.txtUser.setText("");//Vacia la casilla de usuario//
                loginView.txtPassword.setText("");//Vacia la casilla de contraseña//
            }
            
        }
    }

    @Override
    @SuppressWarnings("empty-statement")
    public void actionPerformed(ActionEvent e) {
        try{
            loginIngress(e);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
      }
    }


