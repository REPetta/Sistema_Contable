
package Controller;

import Connection.SalesConnection;
import Model.SaleType;
import Model.SingletonUser;
import View.EditPaymentMethodView;
import static View.EditPaymentMethodView.blinkingFields;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.math.RoundingMode;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;



public class EditPaymentMethod implements ActionListener{
    private final SingletonUser currentUser= SingletonUser.getInstance();
    private ManagementPayment payMen;
    private final EditPaymentMethodView view;
    private SalesConnection salesCon=new SalesConnection();
    private final int codeMethod;
    
    public EditPaymentMethod(int code) throws SQLException {
        view= new EditPaymentMethodView();
        this.view.setTitle("Modificar Metodo"+"-"+currentUser.getUserName().toUpperCase()+"("+currentUser.getRol()+")");
        initializeListeners();
        setStates();
        loadMethod(code);
        codeMethod=code;
    }
    //Metodo  para inicializar los listener con los botones//
    public final void initializeListeners(){
        this.view.btnExit.addActionListener(this);
        this.view.btnEdit.addActionListener(this);

    }
     //Metodo para abri la ventana//
   public void openView(){
       this.view.setVisible(true);
   }
   //Metodo para cerrar la ventana//
   public void closeView(){
       this.view.dispose();
   }
   //Metodo para convertir en porcentaje //
   public int convertToPercentage(double value) {
    return (int) (value * 100);
}
   //Metodo para cargar los datos del cliente a editar//
    public final void loadMethod(int code) throws SQLException{
        SaleType method= salesCon.getMethod(code);
        this.view.txtDescrip.setText(method.getSaleDescription());
        this.view.txtPaymentTime.setText(String.valueOf(method.getPaymentTerm()));
        this.view.txtQuots.setText(String.valueOf(method.getQuotas()));
        this.view.txtDiscount.setText(String.valueOf(convertToPercentage(method.getDiscount())));
        this.view.jStateBox.setSelectedItem(method.getSaleState().toUpperCase());
        
    }
   //Metodo para setear la razon social//
    public final void setStates() {
        
            List<String> estados = new ArrayList<>(Arrays.asList("","DISPONIBLE","NO DISPONIBLE"));
            
            // Crear un modelo para el JComboBox
            DefaultComboBoxModel<String> model = 

                new DefaultComboBoxModel<>();

            for (String estado : estados) {
                model.addElement(estado); // Agregar el nombre de la cuenta
            }

            // Setear el modelo en el JComboBox
            view.jStateBox.setModel(model); 
    // Asegúrate de que addSeatView tenga cbbCuentas

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
    public boolean isValidPayment(JTextField  code){
            try{
                String payment = code.getText();
                int codeNumber= Integer.parseInt(payment);
                if(codeNumber< 0 ){
                    return false;
                }
                
                }catch(NumberFormatException e){
                   return false;
                }
        return true;
    }
    //Metodo para validar el Dni//
    public boolean isValidQuota(JTextField code){
            try{
                String quota = code.getText();
                int codeNumber= Integer.parseInt(quota);
                if(codeNumber<0){
                    return false;
                }
                
                }catch(NumberFormatException e){
                   return false;
                }
        return true;
    }
   //Metodo para convertir a decimal//
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
   public boolean isValidDiscount(JTextField discount) {
    try {
        // Convertir el string a un número entero
        String disc = discount.getText();
        int discountInt = Integer.parseInt(disc);
        
        // Convertir el entero a decimal dividiéndolo por 100
        BigDecimal discountNumber = BigDecimal.valueOf(discountInt)
                .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
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
   // Método para validar si los campos están vacíos o tienen un formato incorrecto
public boolean validateFields(Object[] fields, String[] fieldNames) {
    boolean hasBugs = false;
    StringBuilder bugs = new StringBuilder(); // Mensaje de error acumulado
    
    // Recorre los campos para verificar si están vacíos
    for (int i = 0; i < fields.length; i++) {
        if (fields[i] instanceof JTextField) {
            JTextField textField = (JTextField) fields[i];
            if (textField.getText().trim().isEmpty()) {
                bugs.append("- El campo ").append(fieldNames[i]).append(" no puede estar vacío.\n");
                blinkingFields(textField); // Método para hacer titilar el campo vacío
                hasBugs = true;

            }
        } else if (fields[i] instanceof JComboBox) {
            JComboBox<?> comboBox = (JComboBox<?>) fields[i];
            if (comboBox.getSelectedItem() == null || comboBox.getSelectedItem().toString().trim().isEmpty()) {
                bugs.append("- El campo ").append(fieldNames[i]).append(" no puede estar vacío.\n");
                hasBugs = true;
            }
        }      
    }
    // Si hay campos vacíos, muestra un mensaje de error
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
                if (fieldNames[i].equalsIgnoreCase("Plazo de Pago")) {
                    if (fields[i] instanceof JTextField && !isValidPayment((JTextField) fields[i])) {
                        invalidFields.append("- El campo ").append(fieldNames[i]).append(" no está en un formato válido.\n");
                        blinkingFields((JTextField) fields[i]);
                    }
                }
                if (fieldNames[i].equalsIgnoreCase("Cuotas")) {
                    if (fields[i] instanceof JTextField && !isValidQuota((JTextField) fields[i])) {
                    invalidFields.append("- El campo ").append(fieldNames[i]).append(" no está en un formato válido.\n");
                    blinkingFields((JTextField) fields[i]);
                    }
                }
            if (fieldNames[i].equalsIgnoreCase("Descuento")) {
                if (fields[i] instanceof JTextField && !isValidDiscount((JTextField) fields[i])) {
                    invalidFields.append("- El campo ").append(fieldNames[i]).append(" no está en un formato válido.\n");
                    blinkingFields((JTextField) fields[i]);
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
     public void buttonEditCustomer(ActionEvent e) throws SQLException{
         if(e.getSource()==view.btnEdit){
               Object[] fields = {
            view.txtDescrip,
            view.txtPaymentTime,
            view.txtQuots,
            view.txtDiscount,
            view.jStateBox
        };
        String[] fieldNames = {
            "Descripcion",
            "Plazo de Pago",
            "Cuotas",
            "Descuento",
            "Estado",

        };
          // Validar los campos
        if (validateFields(fields, fieldNames)) {
            int confirm = JOptionPane.showConfirmDialog(
                        null,
                        "¿Estás seguro de que deseas editar este Metodo de Pago?",
                        "Confirmación",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE
            );
             if(confirm == JOptionPane.YES_OPTION) { // Si el usuario confirma, intenta agregarlo//
                  salesCon=new SalesConnection();  
                  SaleType method= new SaleType();
                    
                    String descripcion = ((JTextField) fields[0]).getText().trim();
                     int plazo = Integer.parseInt(((JTextField) fields[1]).getText().trim());
                    int cuotas = Integer.parseInt(((JTextField) fields[2]).getText().trim());
                   // Convertir el descuento usando convertPercentageToDecimal
                    BigDecimal discountBigDecimal = convertPercentageToDecimal(((JTextField) fields[3]).getText().trim());
                     // Convertir a double para su uso
                    double descuento = (discountBigDecimal != null) ? discountBigDecimal.doubleValue() : 0.0;
                    String estado  = ((JComboBox<?>) fields[4]).getSelectedItem().toString().trim();
                    
                    method.setSaleDescription(descripcion);
                    method.setPaymentTerm(plazo);
                    method.setQuotas(cuotas);
                    method.setDiscount(descuento);
                    method.setSaleState(estado.toLowerCase());
                    method.setCode(codeMethod);
                   
                    boolean load=salesCon.updateMethod(method);
                        System.out.print(load);
                            if(load){
                                JOptionPane.showMessageDialog(
                                null,
                                "El Metodo de Pago ha sido modificado exitosamente  \n",
                                 "Confirmacion",
                                 JOptionPane.INFORMATION_MESSAGE
                                );

                            }else{
                                JOptionPane.showMessageDialog(
                                null,
                                "El Metodo de Pago no ha podido ser modificado \n",
                                 "Error",
                                 JOptionPane.ERROR_MESSAGE
                                );

                            }
                    
                  }
            
            }
 
    }
            }

   //Metodo para salir
    public void buttonExit(ActionEvent e){
        if(e.getSource()==view.btnExit){
           closeView();
           payMen=new ManagementPayment();
           payMen.openView();
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        buttonExit(e);
        try {
            buttonEditCustomer(e);
        } catch (SQLException ex) {
            Logger.getLogger(EditPaymentMethod.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
