//Implementacion del patron singleton para que solo haya una instancia de la clase usuario//
package Controller;
import Model.User;

public class SingletonController {
    //Atributos
    private static SingletonController singletonUser;
    private User user;
    //
    //Contructor Privado
    SingletonController(){}
    //
    //Metodo para obtener la Instancia Unica//
    public static SingletonController getInstancia(){
        if(singletonUser==null){
            singletonUser=new SingletonController();
        }
        return singletonUser;
    }
    //
    //Metodo para establecer el usuario
    public void setUser(User user){
        this.user=user;
    }
    //
    //Metodo para obtener el usuario
    public User getUser(){
        return this.user;
    }
    //
}
