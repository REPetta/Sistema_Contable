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
    private String rol;
    private String state;
    private ArrayList<String> tasks;
    private static User user;
    //Contructores//
    public User(String name, String lastName, int dni, String userName){
        this.name=name;
        this.lastName=lastName;
        this.dni=dni;
        this.userName=userName;
        this.tasks=new ArrayList<>();
    }
      public User(String name, String lastName, int dni, String userName,String rol){
        this.name=name;
        this.lastName=lastName;
        this.dni=dni;
        this.userName=userName;
        this.tasks=new ArrayList<>();
        this.rol=rol;
    }
      public User(){
      }
      public User(String name, String lastName, int dni, String userName,String rol,String state){
        this.name=name;
        this.lastName=lastName;
        this.dni=dni;
        this.userName=userName;
        this.tasks=new ArrayList<>();
        this.rol=rol;
        this.state=state;
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
    public void setTasks(ArrayList<String> ts){
        this.tasks=ts;
    }
    public ArrayList<String> getTasks(){
        return this.tasks;
    }
    public void setRol(String r){
        this.rol=r;
    }
    public String getRol(){
        return this.rol;
    }
    public void setState(String s){
        this.state=s;
    }
    public String getState(){
        return this.state;
    }
    //Metodo para obtener la Instancia Unica//
    public static User getInstancia(){
        if(user==null){
            user=new User();
        }
        return user;
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
}
