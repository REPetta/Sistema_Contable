
package Controller;

import Connection.BooksConnection;
import Model.SalesBook;
import Model.SingletonUser;
import View.SalesReportView;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.lang.System.Logger;
import java.lang.System.Logger.Level;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;


public class SalesReport implements ActionListener{
    

    DefaultTableModel modelo = new DefaultTableModel();
    private final SingletonUser currentUser=SingletonUser.getInstance();
    private final SalesReportView view;
    private final BooksConnection librosCon;
    private SalesSystem salesSystem;
    
    public SalesReport(){
        this.view=new SalesReportView();
        librosCon=new BooksConnection();
        iniciarTabla();
        this.view.setTitle("Ver Reporte de ventas"+" - "+currentUser.getUserName()+" ( "+currentUser.getRol().substring(0, 1).toUpperCase()+currentUser.getRol().substring(1).toLowerCase()+ " ) " );
        initializeListeners();
    }
    
    public final void initializeListeners(){
         this.view.btnBuscar.addActionListener(this);
         this.view.btnSalir.addActionListener(this);
         this.view.btnExcelExport.addActionListener(this);
         this.view.btnPdfExport.addActionListener(this);

    }
    
    public void openBook(){
        this.view.setVisible(true);
    }
    
    public void closeBook(){
        this.view.dispose();
    }
    
    public void btnBuscar(ActionEvent e) throws ClassNotFoundException, SQLException, IOException{
        if(e.getSource()==view.btnBuscar){
        try{
            // Obtener las fechas seleccionadas de los DateChooser
            int mes= view.jMonthChooser.getMonth();
            int anio=view.jYearChooser.getYear();
            

           // Obtener la lista de asientos contables entre las fechas seleccionadas
            List<SalesBook> listaVentas= librosCon.obtenerListaVentas(mes, anio);
            // Actualizar la tabla con los resultados
            actualizarTabla(listaVentas);
            
        }catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Ocurrió un error al buscar los asientos.");
        }
    }
    }
    
    public void actualizarTabla(List<SalesBook> listaVentas) throws IOException, SQLException, ClassNotFoundException{
         iniciarTabla();
         int cantidadTotalVendida=0;
         int cantidadTotalRecaudado=0;
         int cantidadTotalVendidaAnterior=0;
         int cantidadTotalRecaudadoAnterior=0;
         if (listaVentas.isEmpty()) {
                    String[] filaVacia = {"Sin datos", "", "", "", ""};
                     modelo.addRow(filaVacia);
                     return;
        }
  
        // Recorrer la lista de asientos contables
        String[] filaSeparadora = {"", "", "", "",""};
        modelo.addRow(filaSeparadora);
        for(SalesBook venta: listaVentas){
                     String[] datos= new String[5];
                        datos[0]=venta.getProducto();
                        datos[1]=String.valueOf(venta.getCantidad_vendida());
                        datos[2]="$"+String.valueOf(venta.getTotal_reacudado());
                        datos[3]=String.valueOf(venta.getVariacion_cantidad())+"%";
                        datos[4]=String.valueOf(venta.getVariacion_recaudado())+"%";
                        cantidadTotalVendida=cantidadTotalVendida+venta.getCantidad_vendida();
                        cantidadTotalRecaudado=cantidadTotalRecaudado+venta.getTotal_reacudado();
                        cantidadTotalVendidaAnterior=cantidadTotalVendidaAnterior+venta.getCantidad_vendida_anterior();
                        cantidadTotalRecaudadoAnterior=cantidadTotalRecaudadoAnterior+venta.getCantidad_recaudado_anterior();
                        modelo.addRow(datos);
            }
        
        double variacionCantidad = (cantidadTotalVendidaAnterior == 0) ? 0 
            : ((cantidadTotalVendida - cantidadTotalVendidaAnterior) * 100.0) / cantidadTotalVendidaAnterior;
        double variacionRecaudado = (cantidadTotalRecaudadoAnterior == 0) ? 0 
            : ((cantidadTotalRecaudado - cantidadTotalRecaudadoAnterior) * 100.0) / cantidadTotalRecaudadoAnterior;
        
           String[] filaFinal=new String[5];
        filaFinal[0] ="Total";
        filaFinal[1] =String.valueOf(cantidadTotalVendida);
        filaFinal[2] ="$"+String.valueOf(cantidadTotalRecaudado);
        filaFinal[3] = String.valueOf(variacionCantidad)+"%";
        filaFinal[4] = String.valueOf(variacionRecaudado)+"%";
        modelo.addRow(filaFinal);
         }
           
    public final void iniciarTabla() {
        
        modelo = new DefaultTableModel() {
            public boolean isCellEditable(int fila, int columna) {
                if (columna == 1 && columna == 2 && columna == 3) {
                    return true;
                } else {
                    return false;
                }
            }
        };
        
        modelo.addColumn("Producto");
        modelo.addColumn("Cantidad Vendida");
        modelo.addColumn("Total Recaudo");
        modelo.addColumn("Variacion Mensual Productos");
        modelo.addColumn("Variacion Mensual Ingresos");
        
        view.jTableDiario.setRowHeight(15);
        view.jTableDiario.setModel(modelo);
        view.jTableDiario.setRowHeight(25);
        
        view.jTableDiario.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            // Verificar si la fila es una fila separadora (todos los valores son vacíos)
            boolean esFilaSeparadora = modelo.getValueAt(row, 0).equals("") && 
                                       modelo.getValueAt(row, 1).equals("") && 
                                       modelo.getValueAt(row, 2).equals("") && 
                                       modelo.getValueAt(row, 3).equals("") &&
                                       modelo.getValueAt(row, 4).equals("");

            if (esFilaSeparadora) {
                cell.setBackground(Color.BLACK);
                cell.setForeground(Color.WHITE); // Texto blanco para contraste
            } else {
                // Restaurar color normal para las demás filas
                cell.setBackground(Color.WHITE);
                cell.setForeground(Color.BLACK);
            }

            return cell;
        }
    });
    }
    
       public void limpiarVista() {
         
         iniciarTabla();
        }
       
       public void buttonBack(ActionEvent e){
           if(e.getSource()==view.btnSalir){
               closeBook();
               salesSystem=new SalesSystem();
               salesSystem.openSalesSystemView();
           }
       }
       
     
    @Override
    public void actionPerformed(ActionEvent e) {
         try{   
            buttonBack(e);
            }catch(Exception ex){
                ex.printStackTrace();
            }
        try {
            btnBuscar(e);
    }   catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(SalesReport.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(SalesReport.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(SalesReport.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
   
}
}
