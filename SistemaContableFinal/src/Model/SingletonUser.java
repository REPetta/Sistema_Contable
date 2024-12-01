//Clase encargada del patron singleton para tener una unica instancia de acceso global del usuario
package Model;

import java.util.List;


public class SingletonUser {
    
    //Atributos//
    private static SingletonUser instance; // Única instancia

    private String userName;
    private List<String> tasks;
    
    //Constructor//
    public SingletonUser(){}
    
    //Metodo para obtener la instancia unica//
    public static SingletonUser getInstance(){
        if(instance==null){
            instance= new SingletonUser();
        }
        return instance;
    }
    
   //Setters and Getters//

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<String> getTasks() {
        return tasks;
    }

    public void setTasks(List<String> tasks) {
        this.tasks = tasks;
    }
    
    
    
}
