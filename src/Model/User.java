//Clase encargada de guardar la informacion de los usuarios//

package Model;

import java.util.ArrayList;

public class User {
    //Atributos//
    private String name;
    private String lastName;
    private int dni;
    private String userName;
    private String password;
    private ArrayList<String> tasks;
    //Contructores//
    public User(String name, String lastName, int dni, String userName, String password){
        this.name=name;
        this.lastName=lastName;
        this.dni=dni;
        this.userName=userName;
        this.password=password;
        this.tasks=new ArrayList<>();
    }
    //Setters and Getters//
    public void setName(String n){
        this.name=n;
    }
    public String getName(){
        return this.name;
    }
    public void setLastName(String ln){
        this.lastName=ln;
    }
    public String getLastName(){
        return this.lastName;
    }
    public void setDni(int d){
        this.dni=d;
    }
    public int getDni(){
        return this.dni;
    }
    public void setUserName(String un){
        this.userName=un;
    }
    public String getUserName(){
        return this.userName;
    }
    public void setPassword(String p){
        this.password=p;
    }
    public String getPassword(){
        return this.password;
    }
    public void setTask(String t){
        tasks.add(t);
    }
    public ArrayList<String> getTasks(){
        return this.tasks;
    }
}
