//Clase encargada del comportamiento del menu principal//
package Controller;

import Model.SingletonUser;
import View.MainMenuView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class MainMenu implements ActionListener {

    //Atributos//
    private final MainMenuView mainMenuView;
    private Login login;
    private final SingletonUser currentUser= SingletonUser.getInstance();
    private AddUser addUser;
    private SearchUser searchUser;
    private ShowAccounts chartAccounts;
 
    
    //Contructor//
    public MainMenu(){
        mainMenuView=new MainMenuView();
        initializeListeners();
        this.mainMenuView.setTitle("Menu Principal"+"-"+currentUser.getUserName().toUpperCase()+"("+currentUser.getRol()+")");
        displayBasedRol(currentUser);
        }
    
    
    //Metodo  para inicializar los listener con los botones//
    public final void initializeListeners(){  
        this.mainMenuView.btnAddSeat.addActionListener(this);
        this.mainMenuView.btnAddUser.addActionListener(this);
        this.mainMenuView.btnDiaryBook.addActionListener(this);
        this.mainMenuView.btnExit.addActionListener(this);
        this.mainMenuView.btnLedger.addActionListener(this);
        this.mainMenuView.btnSales.addActionListener(this);
        this.mainMenuView.btnSearchUser.addActionListener(this);
        this.mainMenuView.btnShowAccounts.addActionListener(this);    
    }
    
    
    //Metodo para ocultar ciertos botones en funcion del rol del usuario//
    public final void displayBasedRol(SingletonUser current){
        if(currentUser.getRol().equalsIgnoreCase("Contador")){
            this.mainMenuView.btnAddUser.setVisible(false);
            this.mainMenuView.btnSearchUser.setVisible(false);
        }
        if(currentUser.getRol().equalsIgnoreCase("Vendedor")){
            this.mainMenuView.btnAddUser.setVisible(false);
            this.mainMenuView.btnSearchUser.setVisible(false);
            this.mainMenuView.btnAddSeat.setVisible(false);
            this.mainMenuView.btnDiaryBook.setVisible(false);
            this.mainMenuView.btnLedger.setVisible(false);
            this.mainMenuView.btnShowAccounts.setVisible(false);
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
    //Metodo para darle funcionalida al boton agregar usuario//
    public void buttonAddUser(ActionEvent e){
        if(e.getSource()==mainMenuView.btnAddUser){
            closeMainMenuView();
            addUser=new AddUser();
            addUser.openAddUserView();
        }
    }
    
    //Metodo para dar funcionalidad al boton buscar usuario//
    public void buttonSearchUser(ActionEvent e){
        if(e.getSource()==mainMenuView.btnSearchUser){
            closeMainMenuView();
            searchUser = new SearchUser();
            searchUser.openSearchUserView();
        }
    }
    //Metodo para acceder al plan de cuentas//
    public void buttonShowAccounts(ActionEvent e) throws SQLException, ClassNotFoundException, IOException{
        if(e.getSource()==mainMenuView.btnShowAccounts){
            closeMainMenuView();
            chartAccounts= new ShowAccounts();
            chartAccounts.openShowAccountsView();
        }
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
        buttonAddUser(e);
        buttonSearchUser(e);
        try {
            buttonShowAccounts(e);
        } catch (SQLException ex) {
            Logger.getLogger(MainMenu.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MainMenu.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MainMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
}
