//Clase encargada de la gestion de usuarios//
package Controller;

import Model.User;
import View.MainMenu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenuController implements ActionListener{//Implementa la interfaz ActionListener para manejar eventos de accion//
    //Atributos//
    private MainMenu mainMenuView=new MainMenu();
    private AddUserController addUserController;
    private DeleteUserController deleteUserController;
    private SearchUserController searchUserController;
    private AddSeatController addSeatController;
    private LoginController loginController;
    private User currentUser=User.getInstancia();
    
    //Metodos//
    public void openMainMenuView(){//Metodo para hacer visible la ventana del MainMenu//
        mainMenuView.setVisible(true);
    }
    public void closeMainMenuView(){//Metodo para cerrar el menu principal
        mainMenuView.dispose();
    }
    public MainMenuController(){
        this.mainMenuView.btnAddUser.addActionListener(this);//Conecta el boton agregar usuario con esta clase//
        this.mainMenuView.btnDelUser.addActionListener(this);//Conecta el boton borrar usuario con esta clase//
        this.mainMenuView.btnSearchUser.addActionListener(this);//Conecta el boton buscar usuario con esta clase//
        this.mainMenuView.btnAddSeat.addActionListener(this);//Conecta el boton agregar asiento con esta clase//
        this.mainMenuView.btnExit.addActionListener(this);//Conecta el boton salir con esta clase
        this.mainMenuView.setTitle("Menu Principal"+" - "+currentUser.getUserName()+" ( "+currentUser.getRol().substring(0, 1).toUpperCase()+currentUser.getRol().substring(1).toLowerCase()+ " ) " );
        if(currentUser.getTasks().contains("agregar_usuario")==false){
            this.mainMenuView.btnAddUser.setVisible(false);
            this.mainMenuView.btnDelUser.setVisible(false);
            this.mainMenuView.btnSearchUser.setVisible(false);
        }
    }
    public void buttonAddUser(ActionEvent e ){               //Accion del boton Agregar Usuario//
        if(e.getSource()==mainMenuView.btnAddUser){//Ejecuta el bloque solo si se presiona el boton agregar usuario//
            addUserController= new AddUserController();
            addUserController.openAddUserView();//Abre  la ventana de Agregar Usuario//
            closeMainMenuView();//Cierra  el menu principal//
        }
    }
    public void buttonDelUser(ActionEvent e){   //Metodo que le da al boton Dar de Baja Usuario la funcion de abrir la ventana Dar de Baja Usuario y cerrar el Menu Principal//
        if(e.getSource()==mainMenuView.btnDelUser){
            deleteUserController= new DeleteUserController();
            deleteUserController.openDeleteUserView();
            closeMainMenuView();
        }
    }
    public void buttonSearchUser(ActionEvent e){//Metodo que le da al boton Buscar Usuario la funcion de abrir la ventana de Buscar Usuario y cerrar el Menu Principal//
        if(e.getSource()==mainMenuView.btnSearchUser){
            searchUserController=new SearchUserController();
            searchUserController.openSearchUserView();
            closeMainMenuView();
        }
    }
    public void buttonAddSeat(ActionEvent e){//Metodo que le da al boton Agregar Asiento la funcion de abrir la ventana de Agregar Asiento y cerrar el Menu Principal//
        if(e.getSource()==mainMenuView.btnAddSeat){
            addSeatController=new AddSeatController();
            addSeatController.openAddSeatView();
            closeMainMenuView();
        }
    }
    public void buttonExit(ActionEvent e){//Metodo que le da al boton Salir la funcion de cerrar el Menu Principal y volver al Login//
        if(e.getSource()==mainMenuView.btnExit){
            closeMainMenuView();
            loginController=new LoginController();
            loginController.openViewLogin();  
        }
    }
   
    @Override
    public void actionPerformed(ActionEvent e) {
        buttonAddUser(e);
        buttonDelUser(e);
        buttonSearchUser(e);
        buttonAddSeat(e);
        buttonExit(e);
    }
    
}
