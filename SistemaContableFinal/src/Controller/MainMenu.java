//Clase encargada del comportamiento del menu principal//
package Controller;

import Model.SingletonUser;
import View.MainMenuView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class MainMenu implements ActionListener {

    //Atributos//
    private final MainMenuView mainMenuView;
    private Login login;
    private final SingletonUser currentUser= SingletonUser.getInstance();
 
    
    //Contructor//
    public MainMenu(){
        mainMenuView=new MainMenuView();
        initializeListeners();
        this.mainMenuView.setTitle("Menu Principal"+"-"+currentUser.getUserName().toUpperCase()+"("+getRol(currentUser)+")");
        displayBasedRol(currentUser);
        }
    
    
    //Metodo  para inicializar los listener con los botones//
    public final void initializeListeners(){  
        this.mainMenuView.btnAddSeat.addActionListener(this);
        this.mainMenuView.btnAddUser.addActionListener(this);
        this.mainMenuView.btnDelUser.addActionListener(this);
        this.mainMenuView.btnDiaryBook.addActionListener(this);
        this.mainMenuView.btnExit.addActionListener(this);
        this.mainMenuView.btnLedger.addActionListener(this);
        this.mainMenuView.btnSales.addActionListener(this);
        this.mainMenuView.btnSearchUser.addActionListener(this);
        this.mainMenuView.btnShowAccounts.addActionListener(this);    
    }
    
    //Metodo para obtener el rol del usuario en funcion de las tareas que tiene permitidas//
    public final String getRol( SingletonUser current){
        if(current.getTasks().contains("agregar_usuario")){
            return "ADMIN";
        }
        return "COMMON";
    }
    
    //Metodo para ocultar ciertos botones en funcion del rol del usuario//
    public final void displayBasedRol(SingletonUser current){
        if(getRol(current).equalsIgnoreCase("COMMON")){
            this.mainMenuView.btnAddUser.setVisible(false);
            this.mainMenuView.btnDelUser.setVisible(false);
        }
    }
    
    //Metodo para abrir la ventana del menu principal//
    public void openMainMenuView(){
        this.mainMenuView.setVisible(true);
    }
    
    //Metodo para cerrar la ventana del menu principal//
    public void closeMainMenuView(){
        this.mainMenuView.dispose();
    }
    
    //Metodo para darle funcionalidad al boton Salir//
    public void buttonExit(ActionEvent e){
        if(e.getSource()==mainMenuView.btnExit){
            closeMainMenuView();
            login=new Login();
            login.openLoginView();
        
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        buttonExit(e);
    }
    
    
}
