

package Controller;

import View.AddUser;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddUserController implements ActionListener {
    //Atributos//
    private AddUser addUserView=new AddUser();
    //Metodos//
    public void openAddUserController(){
        addUserView.setVisible(true);
    }

   

    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }
    
}
