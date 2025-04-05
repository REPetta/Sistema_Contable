/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Model.SalesBook;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author Rodrigo
 */
public class ListaVentasPDF {
    
    public void generarReporte(List<SalesBook> ventas) {
    try {
        // Cargar el archivo .jasper
        
        JasperReport jasperReport = (JasperReport) JRLoader.loadObject(
                    getClass().getResource("/Report/ReporteVentas.jasper")
                );
        
        // Crear la fuente de datos con la lista de ventas
        JRDataSource dataSource = new VentasReport(ventas);

        // Llenar el reporte
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, new HashMap<>(), dataSource);

      // Llenar el reporte con datos y parámetros
            JasperViewer reporteMaster = new JasperViewer(jasperPrint,false);
            reporteMaster.setTitle(" JMR - Informe Contador ");
            reporteMaster.setVisible(true);

    } catch (Exception e) {
        e.printStackTrace();
    }
}

}
