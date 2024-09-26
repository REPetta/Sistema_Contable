//Clase encargada de la gestion de usuarios//
package Controller;

import View.AddSeat;
import View.AddUser;
import View.DeleteUser;
import View.MainMenu;
import View.SearchUser;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenuController implements ActionListener{//Implementa la interfaz ActionListener para manejar eventos de accion//
    //Atributos//
    private MainMenu mainMenuView=new MainMenu();//Crea una instancia de la clase MainMenu//
    private AddUserController addUserController= new AddUserController();
    //Metodos//
    public void openMainMenuView(){//Metodo para hacer visible la ventana del MainMenu//
        mainMenuView.setVisible(true);
    }
    public MainMenuController(){
        this.mainMenuView.btnAddUser.addActionListener(this);//Conecta el boton agregar usuario con esta clase//
        this.mainMenuView.btnDelUser.addActionListener(this);//Conecta el boton borrar usuario con esta clase//
        this.mainMenuView.btnSearchUser.addActionListener(this);//Conecta el boton buscar usuario con esta clase//
        this.mainMenuView.btnAddSeat.addActionListener(this);//Conecta el boton agregar asiento con esta clase//
    }
    public void buttomAddUser(ActionEvent e){               //Accion del boton agregar usuario//
        if(e.getSource()==mainMenuView.btnAddUser){//Ejecuta el bloque solo si se presiona el boton agregar usuario//
            addUserController.openAddUserController();
            mainMenuView.dispose();
        }
    }
    public void buttomDelUser(ActionEvent e){//Arreglar fron//
        if(e.getSource()==mainMenuView.btnDelUser){
            System.out.println("Error aqui");
            DeleteUser form=new DeleteUser();
            form.setVisible(true);
            mainMenuView.dispose();
        }
    }
    public void buttomSearchUser(ActionEvent e){
        if(e.getSource()==mainMenuView.btnSearchUser){
            SearchUser form=new SearchUser();
            form.setVisible(true);
            mainMenuView.dispose();
        }
    }
    public void buttomAddSeat(ActionEvent e){
        if(e.getSource()==mainMenuView.btnAddSeat){
            AddSeat form=new AddSeat();
            form.setVisible(true);
            mainMenuView.dispose();
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        buttomAddUser(e);
    }
    
}
