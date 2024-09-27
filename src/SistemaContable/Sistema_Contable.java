//Clase Main//
package SistemaContable;

import Controller.LoginController;

public class Sistema_Contable {

    public static void main(String[] args) {
        LoginController loginController=new LoginController();
        loginController.openViewLogin();//Hace visible la interfaz grafica del login//
    }
    
}
