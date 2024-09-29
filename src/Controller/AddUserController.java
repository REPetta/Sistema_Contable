//Clase encargada del comportamiento de la vista de "Agregar Usuario"//

package Controller;

import Model.User;
import View.AddUser;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import ConnectionsBD.UserManagementConnection;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class AddUserController implements ActionListener {
    //Atributos//
    private AddUser addUserView=new AddUser();
    private MainMenuController mainMenuController;
    private User currentUser=User.getInstancia();
    //Metodos//
    public AddUserController(){//Conecta el boton Volver con la Clase
        this.addUserView.setTitle("Agregar Usuario"+" - "+currentUser.getUserName()+" ( "+currentUser.getRol().substring(0, 1).toUpperCase()+currentUser.getRol().substring(1).toLowerCase()+ " ) " );
        this.addUserView.btnBack.addActionListener(this);
        this.addUserView.btnAdd.addActionListener(this);
    }
    public void openAddUserView(){//Muestra la ventana//
        addUserView.setVisible(true);
    }
    public void closeAddUserView(){//Cierra la ventana//
        addUserView.dispose();
    }
   public void buttonBack(ActionEvent e){//Metodo que le da al boton volver la accion de salir de la ventana Agregar Usuario y volver al Menu Principal//
       if(e.getSource()==addUserView.btnBack){
           closeAddUserView();
           mainMenuController=new MainMenuController();
           mainMenuController.openMainMenuView();
       }
   
   }
   public void buttonAdd(ActionEvent e) throws SQLException, ClassNotFoundException, IOException{
       if(e.getSource()==addUserView.btnAdd){
          String name=addUserView.txtName.getText().trim();
          String lastname= addUserView.txtLastName.getText().trim();
          String username=addUserView.txtUserName.getText().trim();
          String password=addUserView.txtPassword.getText().trim();
          String rol=addUserView.txtRol.getText().trim();
          String dniText=addUserView.txtDni.getText().trim();
          Integer dni= dniValidation(dniText);
          if(dni!=null){
              if(rolValidation(rol)==true){
                  if(!name.isEmpty() && !lastname.isEmpty() && !username.isEmpty() && !password.isEmpty() && !rol.isEmpty() && dni!=null){
                    User user=new User(name,lastname,username,password,rol,dni);
                    UserManagementConnection userManagementConnection=new UserManagementConnection();
                     if(userManagementConnection.validateUser(user)==true){
                        userManagementConnection.addUser(user);
                        JOptionPane.showMessageDialog(null,"El usuario "+ user.getUserName()+ " ha sido agregado correctamente");
                        addUserView.txtName.setText("");
                         addUserView.txtLastName.setText("");
                        addUserView.txtDni.setText("");
                        addUserView.txtUserName.setText("");
                        addUserView.txtPassword.setText("");
                        addUserView.txtRol.setText("");
              }
              else{
                JOptionPane.showMessageDialog(null,"El usuario "+ user.getUserName()+ " ya esta registrado en el sistema");
                  addUserView.txtName.setText("");
                  addUserView.txtLastName.setText("");
                  addUserView.txtDni.setText("");
                  addUserView.txtUserName.setText("");
                  addUserView.txtPassword.setText("");
                  addUserView.txtRol.setText("");
              }
       }else{
          JOptionPane.showMessageDialog(null,"No se puede dejar ningun campo en blanco");
                  addUserView.txtName.setText("");
                  addUserView.txtLastName.setText("");
                  addUserView.txtDni.setText("");
                  addUserView.txtUserName.setText("");
                  addUserView.txtPassword.setText("");
                  addUserView.txtRol.setText("");
          }
      }else{
                JOptionPane.showMessageDialog(null,"El rol debe ser valido");
                  addUserView.txtName.setText("");
                  addUserView.txtLastName.setText("");
                  addUserView.txtDni.setText("");
                  addUserView.txtUserName.setText("");
                  addUserView.txtPassword.setText("");
                  addUserView.txtRol.setText("");
              }
            
   }else{
           JOptionPane.showMessageDialog(null,"El dni debe ser valido");
                  addUserView.txtName.setText("");
                  addUserView.txtLastName.setText("");
                  addUserView.txtDni.setText("");
                  addUserView.txtUserName.setText("");
                  addUserView.txtPassword.setText("");
                  addUserView.txtRol.setText("");
          }
        
       }
   }
   
    @SuppressWarnings("empty-statement")
   public Integer dniValidation(String dni){
       Integer dniValido=null;
       try{
           dniValido=Integer.parseInt(dni);
           return dniValido;
       }catch (NumberFormatException e){
           return null;
       }
   
   }
   public boolean rolValidation(String rol){
       rol=rol.toUpperCase();
       if(rol.equals("ADMINISTRADOR") || rol.equals("ESTANDAR")){
            return true;
   }else{
       return false;
   }
   }
    @Override
    public void actionPerformed(ActionEvent e) {
      buttonBack(e);
        try {
            buttonAdd(e);
        } catch (SQLException ex) {
            Logger.getLogger(AddUserController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AddUserController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(AddUserController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}



