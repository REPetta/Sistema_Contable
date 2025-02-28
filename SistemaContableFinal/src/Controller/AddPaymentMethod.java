
package Controller;

import Connection.SalesConnection;
import Model.SaleType;
import Model.SingletonUser;
import View.AddPaymentMethodView;
import static View.AddPaymentMethodView.blinkingFields;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.math.RoundingMode;
import javax.swing.JOptionPane;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextField;


public class AddPaymentMethod implements ActionListener {
    
    private final SingletonUser currentUser= SingletonUser.getInstance();
    private final AddPaymentMethodView view;
    private SalesConnection salesCon;
    private PaymentManagement payMan;

    public AddPaymentMethod() {
        view=new AddPaymentMethodView();
        this.view.setTitle("Agregar Metodo de Pago"+"-"+currentUser.getUserName().toUpperCase()+"("+currentUser.getRol()+")");
        initializeListeners();
    }
    //Metodo  para inicializar los listener con los botones//
    public final void initializeListeners(){
        this.view.btnAddPayment.addActionListener(this);
        this.view.btnExit.addActionListener(this);
    }
     //Metodo para abri la ventana//
   public void openView(){
       this.view.setVisible(true);
   }
   //Metodo para cerrar la ventana//
   public void closeView(){
       this.view.dispose();
   }
    //Metodo para limpiar los campos//
   public void fieldsClear(){
       view.txtDescrip.setText("");
       view.txtCode.setText("");
       view.txtDiscount.setText("");
       view.txtPaymentTime.setText("");
       view.txtQuots.setText("");
   }
   //Metodo para validar el Dni//
    public boolean isValidCode(String code){
            try{
                
                int codeNumber= Integer.parseInt(code);
                if(codeNumber< 0 || codeNumber>=99){
                    return false;
                }
                
                }catch(NumberFormatException e){
                   return false;
                }
        return true;
    }
    //Metodo para validar el Dni//
    public boolean isValidPayment(String code){
            try{
                
                int codeNumber= Integer.parseInt(code);
                if(codeNumber< 0 ){
                    return false;
                }
                
                }catch(NumberFormatException e){
                   return false;
                }
        return true;
    }
    //Metodo para validar el Dni//
    public boolean isValidQuota(String code){
            try{
                
                int codeNumber= Integer.parseInt(code);
                if(codeNumber<0){
                    return false;
                }
                
                }catch(NumberFormatException e){
                   return false;
                }
        return true;
    }
    
  public BigDecimal convertPercentageToDecimal(String percentageStr) {
    try {
        // Convertir el String a entero
        int percentage = Integer.parseInt(percentageStr.trim());

       // Convertir el entero a decimal dividiéndolo por 100 y asegurando dos decimales
        return BigDecimal.valueOf(percentage)
                .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);

    } catch (NumberFormatException e) {
        // Si el input no es un número válido, retorna null o lanza una excepción
        System.err.println("Error: El valor ingresado no es un número válido.");
        return null; 
    }
}
    public boolean isValidDiscount(String discount) {
    try {
        // Convertir el string a un número entero
        int discountInt = Integer.parseInt(discount);
        
        // Convertir el entero a decimal dividiéndolo por 100
        BigDecimal discountNumber = BigDecimal.valueOf(discountInt)
                .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
        System.out.println(discount);
        // Verificar que esté en el rango 0.00 - 0.99
        if (discountNumber.compareTo(BigDecimal.ZERO) < 0 || discountNumber.compareTo(new BigDecimal("0.99")) > 0) {
            return false;
        }

        // Verificar que tenga exactamente dos decimales
        return discountNumber.scale() == 2;

    } catch (NumberFormatException | NullPointerException e) {
        return false;
    }
}
    //Metodo para validar si los campos estan vacios  o tienen un formato incorrecto//
    public boolean validateFields(String[] fields,String[] fieldNames){
         JTextField[] jtextFields={
            view.txtDescrip,
             view.txtCode,
             view.txtPaymentTime,
             view.txtQuots,
             view.txtDiscount
        };
          boolean hasBugs= false;
         StringBuilder bugs=new StringBuilder(); //Esta parte construye un string con el mensaje de error//
         for (int i=0; i<fields.length; i++){
             if(fields[i].isEmpty()){
                 bugs.append("- El campo ").append(fieldNames[i]).append(" no puede estar vacio. \n ");
                 blinkingFields(jtextFields[i]); //Metodo pàra hacer titildear el campo vacio//
                 hasBugs=true;
             }
            
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
             for (int i=0; i<fields.length; i++){
                if(fieldNames[i].equalsIgnoreCase("Codigo")){//Valida el dni//
                    if(!isValidCode(fields[i])){
                        invalidFields.append("- El campo ").append(fieldNames[i]).append(" no esta en un formato valido. \n ");
                        blinkingFields(jtextFields[i]);
                    }
                }
                if(fieldNames[i].equalsIgnoreCase("Plazo de Pago")){ //Valida el rol//
                    if(!isValidPayment(fields[i])){
                        invalidFields.append("- El campo ").append(fieldNames[i]).append(" no esta en un formato valido. \n ");
                        blinkingFields(jtextFields[i]);
                    }
                }
                if(fieldNames[i].equalsIgnoreCase("Cuotas")){ //Valida el rol//
                    if(!isValidQuota(fields[i])){
                        invalidFields.append("- El campo ").append(fieldNames[i]).append(" no esta en un formato valido. \n ");
                        blinkingFields(jtextFields[i]);
                    }
                }
                if(fieldNames[i].equalsIgnoreCase("Descuento")){ //Valida el rol//
                    if(!isValidDiscount(fields[i])){
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
   //Metodo para agregar el cliente//
     public void buttonAddMethod(ActionEvent e) throws SQLException{
         if(e.getSource()==view.btnAddPayment){
               String[] fields = {
                    view.txtDescrip.getText().trim(),
                    view.txtCode.getText().trim(),
                    view.txtPaymentTime.getText().trim(),
                    view.txtQuots.getText().trim(),
                    view.txtDiscount.getText().trim()
        };
        
        String[] fieldNames = {
            "Descripcion",
            "Codigo ",
            "Plazo de Pago",
            "Cuotas",
            "Descuento"
        };
          // Validar los campos
         // Validar los campos
        if (validateFields(fields, fieldNames)) {
            int confirm = JOptionPane.showConfirmDialog(
                        null,
                        "¿Estás seguro de que deseas agregar este Metodo de Pago?",
                        "Confirmación",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE
            );
             if(confirm == JOptionPane.YES_OPTION) { // Si el usuario confirma, intenta agregarlo//
                  salesCon=new SalesConnection();  
                  SaleType method= new SaleType();
                    
                    String descrip = fields[0];
                    int code = Integer.parseInt(fields[1]);
                    int payment = Integer.parseInt(fields[2]);
                    int quotas = Integer.parseInt(fields[3]);
                    double discount= convertPercentageToDecimal(fields[4]).doubleValue();
                    
                    method.setSaleDescription(descrip);
                    method.setCode(code);
                    method.setPaymentTerm(payment);
                    method.setQuotas(quotas);
                    method.setDiscount(discount);
            
                    
                    
                    if(!salesCon.isMethodExist(code)){    //Si el usuario no existe en la base lo carga , en caso contrario retorna un mensaje de error//
                        boolean load=salesCon.addTypeSale(method);
                            if(load){
                                JOptionPane.showMessageDialog(
                                null,
                                "El Metodo de Pago ha sido agregado exitosamente  \n",
                                 "Confirmacion",
                                 JOptionPane.INFORMATION_MESSAGE
                                );
                                fieldsClear();
                            }else{
                                JOptionPane.showMessageDialog(
                                null,
                                "El Metodo de Pago no ha podido ser agregado \n",
                                 "Error",
                                 JOptionPane.ERROR_MESSAGE
                                );
                                fieldsClear();
                            }
                    }else{
                    JOptionPane.showMessageDialog(
                                null,
                                "El Metodo de Pago  "+ method.getSaleDescription()+" ya existe \n",
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
             
   
//Metodo para salir
    public void buttonExit(ActionEvent e){
        if(e.getSource()==view.btnExit){
           closeView();
           payMan=new PaymentManagement();
           payMan.openView();
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            buttonAddMethod(e);
        } catch (SQLException ex) {
            Logger.getLogger(AddPaymentMethod.class.getName()).log(Level.SEVERE, null, ex);
        }
        buttonExit(e);
    }
    
    
}
