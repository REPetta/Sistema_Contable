
package Controller;

import Connection.AccountConnection;
import Connection.AccountSeatConnection;
import Connection.ItemConnection;
import Connection.UserConnection;
import Model.Account;
import Model.AccountSeat;
import Model.Item;
import Model.Seat;
import Model.SingletonUser;
import View.AddItemView;
import static View.LoginView.blinkingFields;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.math.BigDecimal;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AddItem implements ActionListener {
    
    private final SingletonUser currentUser= SingletonUser.getInstance();
    private final AddItemView view;
    private ItemsManagement itemsManagement;
    private ItemConnection itemCon;
    private final UserConnection conUsuario;
    private final AccountSeatConnection seatCon;
    private final AccountConnection conAccount;
    
    public AddItem() {
        view=new AddItemView();
        conAccount=new AccountConnection();
        seatCon=new AccountSeatConnection();
        conUsuario=new UserConnection();
        itemCon=new ItemConnection();
         this.view.setTitle("Agregar Articulo"+"-"+currentUser.getUserName().toUpperCase()+"("+currentUser.getRol()+")");
         initializeListeners();
    }
    //Metodo para los listeners//
    public final void initializeListeners(){
            this.view.btnAddItem.addActionListener(this);
            this.view.btnExit.addActionListener(this);
    }
    //Metodo para abrir//
    public void openAddItemView(){
       this.view.setVisible(true);
   }
   //Metodo para cerrar la ventana//
   public void closeAddItemView(){
       this.view.dispose();
   }
    //Metodo para validar el ingreso antes de cargar en la base de datos las asientos contables//
    
    public boolean validBalance(int newStock, double newCost) throws SQLException{
            
            AccountConnection accountCon= new AccountConnection();
            Account account= accountCon.getAccountBox(111);
            if(account.getBalance()-(newCost*newStock)<0){
                        JOptionPane.showMessageDialog(null, "La cuenta "+ account.getAccountName()+" no tiene suficiente saldo para realizar esta operacion", "Error", JOptionPane.ERROR_MESSAGE);
                        return false;
            }
            return true;
    }
   //Metodo para limpiar los campos//
   public void fieldsClear(){
       view.txtName.setText("");
       view.txtItemCode.setText("");
       view.txtCost.setText("");
       view.txtDescrip.setText("");
       view.txtStock.setText("");
       view.txtStockMin.setText("");
   }
   //Metodo para validar si los campos estan vacios  o tienen un formato incorrecto//
    public boolean validateFields(String[] fields,String[] fieldNames){
         JTextField[] jtextFields={
            view.txtName,
             view.txtItemCode,
             view.txtCost,
             view.txtStock,
             view.txtStockMin
        };
          boolean hasBugs= false;
         StringBuilder bugs=new StringBuilder(); //Esta parte construye un string con el mensaje de error//
         for (int i=0; i<fields.length-1; i++){
             if(fields[i].isEmpty()){
                 bugs.append("- El campo ").append(fieldNames[i]).append(" no puede estar vacio. \n ");
                 blinkingFields(jtextFields[i]); //Metodo pàra hacer titildear el campo vacio//
                 hasBugs=true;
             }
            
         }
          if(view.txtDescrip.getText().trim().isEmpty()){
                 bugs.append("- El campo ").append(fieldNames[3]).append(" no puede estar vacio. \n ");
                 hasBugs=true;
             }
         if(hasBugs){ //Si hay un campo vacio  ejecuta el mensaje de error//
             JOptionPane.showMessageDialog(
                null,
                "Se han encontrado los siguientes problemas:\n" + bugs,
                "Error",
                JOptionPane.ERROR_MESSAGE
             );
             return false;
         }else{//Si no hay un campo vacio verifica que el formato de los ingresado sea valido para ese campo//
             StringBuilder invalidFields=new StringBuilder(); //Esta parte construye un string con el mensaje de error//
             for (int i=0; i<fields.length-1; i++){
                if(fieldNames[i].equalsIgnoreCase("Codigo de Articulo")){//Valida el dni//
                    if(!isValidCode(fields[i])){
                        invalidFields.append("- El campo ").append(fieldNames[i]).append(" no esta en un formato valido. \n ");
                        blinkingFields(jtextFields[i]);
                    }
                }
                if(fieldNames[i].equalsIgnoreCase("Precio Unitario")){ //Valida el rol//
                    if(!isValidCost(fields[i])){
                        invalidFields.append("- El campo ").append(fieldNames[i]).append(" no esta en un formato valido. \n ");
                        blinkingFields(jtextFields[i]);
                    }
                }
                if(fieldNames[i].equalsIgnoreCase("Stock Actual")){ //Valida el rol//
                    if(!isValidCode(fields[i])){
                        invalidFields.append("- El campo ").append(fieldNames[i]).append(" no esta en un formato valido. \n ");
                        blinkingFields(jtextFields[i]);
                    }
                }
                if(fieldNames[i].equalsIgnoreCase("Stock Minimo")){ //Valida el rol//
                    if(!isValidCode(fields[i])){
                        invalidFields.append("- El campo ").append(fieldNames[i]).append(" no esta en un formato valido. \n ");
                        blinkingFields(jtextFields[i]);
                    }
                }
         }
             if(!invalidFields.isEmpty()){//Si hay campos con formatos invalidos se ejecuta//
                JOptionPane.showMessageDialog(
                    null,
                    "Se han encontrado los siguientes problemas:\n" +invalidFields,
                    "Error",
                    JOptionPane.ERROR_MESSAGE
             );
             return false;
             }
         }
     return true;
    }
    //Metodo para validar el Dni//
    public boolean isValidCode(String code){
            try{
                
                int codeNumber= Integer.parseInt(code);
                if(codeNumber<= 0 || codeNumber>=99999999){
                    return false;
                }
                
                }catch(NumberFormatException e){
                   return false;
                }
        return true;
    }
    //Metodo para validar el Dni//
    public boolean isValidCost(String price){
            try{
                
               // Intentar convertir el string a un número flotante
                 BigDecimal priceNumber = new BigDecimal(price);
                 // Verificar que el número sea mayor a 0
                 // Verificar que el número sea mayor a 0
                if (priceNumber.compareTo(BigDecimal.ZERO) <= 0) {
                    return false;
                }
                // Verificar que tenga exactamente dos decimales
                    int scale = priceNumber.scale();
                         if (scale == 2) {
                            return true;
                }
                } catch (NumberFormatException | NullPointerException e) {
                     return false;
            }
        return false;
    }
   //Metodo para Agregar el articulo//
     public void buttonAddItem (ActionEvent e) throws SQLException, ClassNotFoundException, IOException{
         if(e.getSource()==view.btnAddItem){
               String[] fields = {
                    view.txtName.getText().trim(),
                    view.txtItemCode.getText().trim(),
                    view.txtCost.getText().trim(),
                    view.txtStock.getText().trim(),
                    view.txtStockMin.getText().trim(),
                    view.txtDescrip.getText().trim()
                    
        };
        String[] fieldNames = {
            "Nombre de Articulo",
            "Codigo de Articulo",
            "Precio Unitario",
            "Stock Actual",
            "Stock Minimo",
            "Descripcion",
        };
          // Validar los campos
        if (validateFields(fields, fieldNames)) {
            int confirm = JOptionPane.showConfirmDialog(
                        null,
                        "¿Estás seguro de que deseas agregar este Articulo?",
                        "Confirmación",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE
            );
             if(confirm == JOptionPane.YES_OPTION) { // Si el usuario confirma, intenta agregarlo//
                  itemCon=new ItemConnection();  
                  Item item= new Item();
                    
                    String name = fields[0];
                    int itemCode = Integer.parseInt(fields[1]);
                    double itemPrice= Double.parseDouble(fields[2]);
                    int stock= Integer.parseInt(fields[3]);
                    int stockMin=  Integer.parseInt(fields[4]);
                    String descripcion=fields[5];
            
                    item.setItemName(name);
                    item.setItemCode(itemCode);
                    item.setUnitPrice(itemPrice);
                    item.setStock(stock);
                    item.setStockMin(stockMin);
                    item.setItemDescription(descripcion);
                    
                    if(!validBalance(stock,itemPrice)){
                        fieldsClear();
                        return;
                    }
                    
                    if(!itemCon.isItemExist(itemCode)){    //Si el usuario no existe en la base lo carga , en caso contrario retorna un mensaje de error//
                        boolean load=itemCon.addItem(item);
                            if(load){
                                JOptionPane.showMessageDialog(
                                null,
                                "El Articulo ha sido agregado exitosamente  \n",
                                 "Confirmacion",
                                 JOptionPane.INFORMATION_MESSAGE
                                );
                                loadAccountSeat(itemPrice*stock);
                                fieldsClear();
                            }else{
                                JOptionPane.showMessageDialog(
                                null,
                                "El Articulo no ha podido ser agregado \n",
                                 "Error",
                                 JOptionPane.ERROR_MESSAGE
                                );
                                fieldsClear();
                            }
                    }else{
                    JOptionPane.showMessageDialog(
                                null,
                                "El Articulo  "+ item.getItemName()+" ya existe \n",
                                 "Error",
                                 JOptionPane.ERROR_MESSAGE
                                );
                    fieldsClear();
                     }
                  }
            
            }
            fieldsClear();
    }
            } 
   //Metodo para salir//
   public void buttonExit(ActionEvent e){
       if(e.getSource()==view.btnExit){
           closeAddItemView();
           itemsManagement= new  ItemsManagement();
           itemsManagement.openItemsManagementView();
       }
   }

   //Metodo para actualizar la los asientosContables//
   public void  loadAccountSeat(double monto) throws SQLException, ClassNotFoundException, IOException{
       java.util.Date fechaActual = new Date();
        java.sql.Date fechaSQL = new java.sql.Date(fechaActual.getTime());
        List<Account> cuentasActualizar=new ArrayList<>();
        
        Account cuentaMercaderia=conAccount.getAccountBox(131);
        double nuevoMontoMercaderia=cuentaMercaderia.getBalance()+monto;
        cuentaMercaderia.setBalance(nuevoMontoMercaderia);
        
        Account cuentaCaja=conAccount.getAccountBox(111);
        double nuevoMontoCaja=cuentaCaja.getBalance()-monto;
        cuentaCaja.setBalance(nuevoMontoCaja);
        
       cuentasActualizar.add(cuentaMercaderia);
       cuentasActualizar.add(cuentaCaja);
       
       Seat seat =new Seat(
                   
                   conUsuario.getUserId(currentUser.getUserName()),
                   fechaSQL,
                   "Compra de Mercaderia"
                   );
           
           int idSeat=seatCon.addSeat(seat);

               AccountSeat mercaderia= new AccountSeat(
                   idSeat,
                   "DEBER",
                   conAccount.getAccountBox(131).getIdAccount(),
                   monto,    
                   nuevoMontoMercaderia
               );
               seatCon.addAccountSeat(mercaderia);
               
               AccountSeat caja= new AccountSeat(

                   idSeat,
                   "HABER",
                   conAccount.getAccountBox(111).getIdAccount(),
                   monto,    
                   nuevoMontoCaja
               );

               seatCon.addAccountSeat(caja);
  
              conAccount.actualizarSaldo(cuentasActualizar);
            }
            
   
    @Override
    public void actionPerformed(ActionEvent e) {
        buttonExit(e);
        try {
            buttonAddItem(e);
        } catch (SQLException ex) {
            Logger.getLogger(AddItem.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AddItem.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(AddItem.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
