//Metodo para el conportamiento de la vista de Detalles de la Cuenta//
package Controller;

import ConnectionsBD.ChartAccountsConnection;
import Model.Account;
import Model.User;
import View.DetailsAccount;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;


public class DetailsAccountController implements ActionListener{
    //Atributos//
    private DetailsAccount detailsAccountView;
    private User currentUser=User.getInstancia();
    private Account selectedAccount; // Cuenta seleccionada
 
    //Contructor//
    public DetailsAccountController(Account selectedAccount) {
            this.detailsAccountView=new DetailsAccount();
            this.selectedAccount=selectedAccount;
            this.detailsAccountView.setTitle("Detalles"+" - "+currentUser.getUserName()+" ( "+currentUser.getRol().substring(0, 1).toUpperCase()+currentUser.getRol().substring(1).toLowerCase()+ " ) " );
            this.detailsAccountView.btnOk.addActionListener(this);
            if (this.selectedAccount != null) {
                this.detailsAccountView.txtName.setText(this.selectedAccount.getAccountName());
                this.detailsAccountView.txtCode.setText(String.valueOf(this.selectedAccount.getAccountCode()));
                this.detailsAccountView.txtAmount.setText(String.valueOf("$"+this.selectedAccount.getAccountBalance()));
                this.detailsAccountView.txtType.setText(this.selectedAccount.getAccountType());
            } else {
                // Manejo de error si la cuenta es nula
                JOptionPane.showMessageDialog(null, "La cuenta seleccionada es nula.", "Error", JOptionPane.ERROR_MESSAGE);
        }   
    }
    //Metodos//
    //Muestra la ventana//
    public void openDetailsAccountView(){
        detailsAccountView.setVisible(true);
}
    public void buttonYes(ActionEvent e){
        if(e.getSource()==detailsAccountView.btnOk){
            detailsAccountView.dispose();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        buttonYes(e);
    }
}
