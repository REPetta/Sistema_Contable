//Clase encargada del comportamiento de  la vista agregar usuario
package Controller;

import Connection.UserConnection;
import Model.SingletonUser;
import Model.User;
import View.AddUserView;
import static View.AddUserView.blinkingFields;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTextField;


public class AddUser implements ActionListener{
    
    private final AddUserView addUserView;
    private MainMenu mainMenu;
    private final SingletonUser currentUser= SingletonUser.getInstance();
    
    //Constructor//
    public AddUser () {
        addUserView= new AddUserView();
        initializeListeners();
        this.addUserView.setTitle("Menu Principal"+"-"+currentUser.getUserName().toUpperCase()+"("+currentUser.getRol()+")");
        
    
    }
     //Metodo para abrir la ventana del agregar el usuario//
    public void openAddUserView(){
        this.addUserView.setVisible(true);
    }
    
    //Metodo para cerrar la ventana del agregar el usuario//
    public void closeAddUserView(){
        this.addUserView.dispose();
    }
    
    //Metodo  para inicializar los listener con los botones//
    public final void initializeListeners(){  
       addUserView.btnAdd.addActionListener(this);
       addUserView.btnBack.addActionListener(this);
    }
    
    //Metodo para el comportamiento del boton Agregar//
    public void buttonAdd(ActionEvent e) throws SQLException{
    
        if(e.getSource()==addUserView.btnAdd){
            String [] fields={
                addUserView.txtName.getText().trim(),
                addUserView.txtLastName.getText().trim(),
                addUserView.txtDni.getText().trim(),
                addUserView.txtUserName.getText().trim(),
                addUserView.txtPassword.getText().trim(),
                addUserView.txtRol.getText().trim()
            };
            
            if(validateFields(fields)){ //Si los campos son validos procede a intentar cargar el usuario en la base de datos//
                 int confirm = JOptionPane.showConfirmDialog(
                        null,
                        "¿Estás seguro de que deseas agregar este usuario?",
                        "Confirmación",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE
            );
                  if(confirm == JOptionPane.YES_OPTION) { // Si el usuario confirma, intenta agregarlo//
                    UserConnection con= new UserConnection();
                    User user = new User(
                    );
                    user.setName(fields[0]);
                    user.setLastName(fields[1]);
                    user.setDni(Integer.parseInt(fields[2]));
                    user.setUserName(fields[3]);
                    user.setPassword(fields[4]);
                    
                    if(!con.isUserExist(fields[3])){    //Si el usuario no existe en la base lo carga , en caso contrario retorna un mensaje de error//
                        boolean load=con.addUser(user, fields[5]);
                            if(load){
                                JOptionPane.showMessageDialog(
                                null,
                                "El usuario ha sido cargado exitosamente  \n",
                                 "Confirmacion",
                                 JOptionPane.INFORMATION_MESSAGE
                                );
                                fieldsClear();
                            }else{
                                JOptionPane.showMessageDialog(
                                null,
                                "El usuario no ha podido ser cargado \n",
                                 "Error",
                                 JOptionPane.ERROR_MESSAGE
                                );
                                fieldsClear();
                            }
                    }else{
                    JOptionPane.showMessageDialog(
                                null,
                                "El usuario  "+ user.getUserName()+" ya existe \n",
                                 "Error",
                                 JOptionPane.ERROR_MESSAGE
                                );
                    fieldsClear();
                     }
                  }
            
            }
            fieldsClear();
    }
    }
    
    //Metodo para limpiar los campos//
   public void fieldsClear(){
       addUserView.txtName.setText("");
       addUserView.txtLastName.setText("");
       addUserView.txtDni.setText("");
       addUserView.txtUserName.setText("");
       addUserView.txtPassword.setText("");
       addUserView.txtRol.setText("");
   }
   
    //Metodo para validar si los campos estan vacios  o tienen un formato incorrecto//
    public boolean validateFields(String[] fields){
         String[] fieldNames=  {"Nombre", "Apellido", "DNI", "Nombre de Usuario","Contraseña","Rol"};
         JTextField[] jtextFields={
            addUserView.txtName,
            addUserView.txtLastName,
            addUserView.txtDni,
            addUserView.txtUserName,
            addUserView.txtPassword,
            addUserView.txtRol
        };
          boolean hasBugs= false;
         StringBuilder bugs=new StringBuilder(); //Esta parte construye un string con el mensaje de error//
         for (int i=0; i<fields.length; i++){
             if(fields[i].isEmpty()){
                 bugs.append("- El campo ").append(fieldNames[i]).append(" no puede estar vacio. \n ");
                 blinkingFields(jtextFields[i]); //Metodo pàra hacer titildear el campo vacio//
                 hasBugs=true;
             }
         }
         if(hasBugs){ //Si hay un campo vacio  ejecuta el mensaje de error//
             JOptionPane.showMessageDialog(
                null,
                "Se han encontrado los siguientes problemas:\n" + bugs,
                "Error",
                JOptionPane.ERROR_MESSAGE
             );
             return false;
         }else{//Si no hay un campo vacio verifica que el formato de los ingresado sea valido para ese campo//
             StringBuilder invalidFields=new StringBuilder(); //Esta parte construye un string con el mensaje de error//
             for (int i=0; i<fields.length; i++){
                if(fieldNames[i].equalsIgnoreCase("DNI")){//Valida el dni//
                    if(!isValidDni(fields[i])){
                        invalidFields.append("- El campo ").append(fieldNames[i]).append(" no esta en un formato valido. \n ");
                        blinkingFields(jtextFields[i]);
                    }
                }
                if(fieldNames[i].equalsIgnoreCase("Rol")){ //Valida el rol//
                    if(!fields[i].equalsIgnoreCase("ADMINISTRADOR") && !fields[i].equalsIgnoreCase("ESTANDAR")){
                        invalidFields.append("- El campo ").append(fieldNames[i]).append(" debe ser 'ADMINISTRADOR' o 'ESTANDAR'. \n ");
                        blinkingFields(jtextFields[i]);
                    }
                }
         }
             if(!invalidFields.isEmpty()){//Si hay campos con formatos invalidos se ejecuta//
                JOptionPane.showMessageDialog(
                    null,
                    "Se han encontrado los siguientes problemas:\n" +invalidFields,
                    "Error",
                    JOptionPane.ERROR_MESSAGE
             );
             return false;
             }
         }
     return true;
    }
    
    //Metodo para validar el Dni//
    public boolean isValidDni(String dni){
            try{
                
                int dniNumber= Integer.parseInt(dni);
                return dniNumber<=99999999;
                
                }catch(NumberFormatException e){
                   return false;
                }
    }

  
    
    //Metodo para darle funcionalidad al boton Salir//
    public void buttonExit(ActionEvent e){
        if(e.getSource()==addUserView.btnBack){
            closeAddUserView();
            mainMenu= new MainMenu();
            mainMenu.openMainMenuView();
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        buttonExit(e);
        try {
            buttonAdd(e);
        } catch (SQLException ex) {
            Logger.getLogger(AddUser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
