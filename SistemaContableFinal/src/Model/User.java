//Clase que encarga de contener la informacio del usuario//
package Model;

import java.util.List;


public class User {
    
    //Atributos//
    private String name;
    private String lastName;
    private int dni;
    private String userName;
    private String password;
    private List<String> tasks;
    
   //Constructor//

    public User(String name, String lastName, int dni, String userName, String password, List<String> tasks) {
        this.name = name;
        this.lastName = lastName;
        this.dni = dni;
        this.userName = userName;
        this.password = password;
        this.tasks = tasks;
    }

    public User() {
    }
    
    
    
    //Getters and Setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getDni() {
        return dni;
    }

    public void setDni(int dni) {
        this.dni = dni;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getTasks() {
        return tasks;
    }

    public void setTasks(List<String> tasks) {
        this.tasks = tasks;
    }
    
    
    
    
}
