
package Controller;

import Connection.Connections;
import Model.SalesBook;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;
import net.sf.jasperreports.view.JasperViewer;


public class Reports {
    //Metodo para exportar como pdf del libro diario
     public void libroDiarioReportPDF(Date fechaInicio, Date fechaFin) throws SQLException, JRException {
        try {
            Connections con = new Connections();

            // Cargar el reporte Jasper
            JasperReport jasperMasterReport = (JasperReport) JRLoader.loadObject(
                    getClass().getResource("/Report/LibroDiarioPDF.jasper")
            );

            // Pasar los parámetros al reporte
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("fechaInicio", fechaInicio);
            parameters.put("fechaFin", fechaFin);

            // Llenar el reporte con datos y parámetros
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperMasterReport, parameters, con.connect());
            JasperViewer reporteMaster = new JasperViewer(jasperPrint,false);
            reporteMaster.setTitle(" JMR - Informe Contador ");
            reporteMaster.setVisible(true);
            
            
        } catch (JRException ex) {
            JOptionPane.showMessageDialog(null, "Error al generar el reporte: " + ex.getMessage());
        }
    }
     //Metodo para exportar como pdf del libro diario
     public void libroDiarioReportExcel(Date fechaInicio, Date fechaFin) throws SQLException, JRException {
        try {
            Connections con = new Connections();
            
            // Pasar los parámetros al reporte
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("fechaInicio", fechaInicio);
            parameters.put("fechaFin", fechaFin);
            
           JasperReport jasperReport = (JasperReport) JRLoader.loadObject(getClass().getResource("/Report/LibroDiarioPDF.jasper"));
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, con.connect());

            // Exportar a Excel
            JRXlsxExporter exporter = new JRXlsxExporter();
            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput("C:/Users/Usuario/Desktop/Reportes/LibroDiario.xlsx"));

            // Configuración para Excel "limpio"
            SimpleXlsxReportConfiguration configuration = new SimpleXlsxReportConfiguration();
            configuration.setDetectCellType(true);
            configuration.setCollapseRowSpan(false);
            configuration.setOnePagePerSheet(false);
            configuration.setRemoveEmptySpaceBetweenRows(true);
            configuration.setRemoveEmptySpaceBetweenColumns(true);
            configuration.setWhitePageBackground(false);
            configuration.setIgnoreGraphics(true);
            configuration.setWrapText(true);

        exporter.setConfiguration(configuration);

            exporter.exportReport();
            
            JOptionPane.showMessageDialog(
                null,
                "El reporte fue exportado exitosamente.",
                "Éxito",
                JOptionPane.INFORMATION_MESSAGE
        );
        } catch (JRException ex) {
            JOptionPane.showMessageDialog(null, "Error al generar el reporte: " + ex.getMessage());
        }
    }
     
     //Metodo para exportar como pdf del libro mayor
     public void libroMayorReportPDF(Date fechaInicio, Date fechaFin, int idCuenta) throws SQLException, JRException {
        try {
            Connections con = new Connections();

            // Cargar el reporte Jasper
            JasperReport jasperMasterReport = (JasperReport) JRLoader.loadObject(
                    getClass().getResource("/Report/LibroMayorPDF.jasper")
            );

            // Pasar los parámetros al reporte
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("fechaInicio", fechaInicio);
            parameters.put("fechaFin", fechaFin);
            parameters.put("idC", idCuenta);

            // Llenar el reporte con datos y parámetros
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperMasterReport, parameters, con.connect());
            JasperViewer reporteMaster = new JasperViewer(jasperPrint,false);
            reporteMaster.setTitle(" JMR - Informe Contador ");
            reporteMaster.setVisible(true);

        } catch (JRException ex) {
            JOptionPane.showMessageDialog(null, "Error al generar el reporte: " + ex.getMessage());
        }
    }
       //Metodo para exportar como pdf del libro mayor
     public void libroMayorReportExcel(Date fechaInicio, Date fechaFin, int idCuenta) throws SQLException, JRException {
        try {
            Connections con = new Connections();

            // Cargar el reporte Jasper
            JasperReport jasperMasterReport = (JasperReport) JRLoader.loadObject(
                    getClass().getResource("/Report/LibroMayorPDF.jasper")
            );

            // Pasar los parámetros al reporte
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("fechaInicio", fechaInicio);
            parameters.put("fechaFin", fechaFin);
            parameters.put("idC", idCuenta);

            // Llenar el reporte con datos y parámetros
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperMasterReport, parameters, con.connect());
            
             // Exportar a Excel
            JRXlsxExporter exporter = new JRXlsxExporter();
            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput("C:/Users/Usuario/Desktop/Reportes/LibroMayor.xlsx"));

            // Configuración para Excel "limpio"
            SimpleXlsxReportConfiguration configuration = new SimpleXlsxReportConfiguration();
            configuration.setDetectCellType(true);
            configuration.setCollapseRowSpan(false);
            configuration.setOnePagePerSheet(false);
            configuration.setRemoveEmptySpaceBetweenRows(true);
            configuration.setRemoveEmptySpaceBetweenColumns(true);
            configuration.setWhitePageBackground(false);
            configuration.setIgnoreGraphics(true);
            configuration.setWrapText(true);

        exporter.setConfiguration(configuration);

            exporter.exportReport();
            
            JOptionPane.showMessageDialog(
                null,
                "El reporte fue exportado exitosamente.",
                "Éxito",
                JOptionPane.INFORMATION_MESSAGE
        );

        } catch (JRException ex) {
            JOptionPane.showMessageDialog(null, "Error al generar el reporte: " + ex.getMessage());
        }
    }
     //Metodo para exportar como pdf de la facturacion
     public void facturacionReportPDF(Date fecha, int codigoArticulo, int dni) throws SQLException, JRException {
        try {
            Connections con = new Connections();

            // Cargar el reporte Jasper
            JasperReport jasperMasterReport = (JasperReport) JRLoader.loadObject(
                    getClass().getResource("/Report/Facturacion.jasper")
            );

            // Pasar los parámetros al reporte
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("fechaFactura", fecha);
            if(codigoArticulo==-1){
                parameters.put("codigoArticulo", null);
            }else{
                parameters.put("codigoArticulo", codigoArticulo);
            }
            if(dni==-1){
                parameters.put("dni", null);
            }else{
                parameters.put("dni", dni);
            }
            
            // Llenar el reporte con datos y parámetros
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperMasterReport, parameters, con.connect());
            JasperViewer reporteMaster = new JasperViewer(jasperPrint,false);
            reporteMaster.setTitle(" JMR - Informe Contador ");
            reporteMaster.setVisible(true);

        } catch (JRException ex) {
            JOptionPane.showMessageDialog(null, "Error al generar el reporte: " + ex.getMessage());
        }
    }
         //Metodo para exportar como pdf del libro mayor
     public void facturacionReportExcel(Date fecha, int codigoArticulo, int dni) throws SQLException, JRException {
        try {
            Connections con = new Connections();

            // Cargar el reporte Jasper
            JasperReport jasperMasterReport = (JasperReport) JRLoader.loadObject(
                    getClass().getResource("/Report/Facturacion.jasper")
            );

           // Pasar los parámetros al reporte
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("fechaFactura", fecha);
            if(codigoArticulo==-1){
                parameters.put("codigoArticulo", null);
            }else{
                parameters.put("codigoArticulo", codigoArticulo);
            }
            if(dni==-1){
                parameters.put("dni", null);
            }else{
                parameters.put("dni", dni);
            }

            // Llenar el reporte con datos y parámetros
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperMasterReport, parameters, con.connect());
            
             // Exportar a Excel
            JRXlsxExporter exporter = new JRXlsxExporter();
            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput("C:/Users/Usuario/Desktop/Reportes/Facturacion.xlsx"));

            // Configuración para Excel "limpio"
            SimpleXlsxReportConfiguration configuration = new SimpleXlsxReportConfiguration();
            configuration.setDetectCellType(true);
            configuration.setCollapseRowSpan(false);
            configuration.setOnePagePerSheet(false);
            configuration.setRemoveEmptySpaceBetweenRows(true);
            configuration.setRemoveEmptySpaceBetweenColumns(true);
            configuration.setWhitePageBackground(false);
            configuration.setIgnoreGraphics(true);
            configuration.setWrapText(true);

        exporter.setConfiguration(configuration);

            exporter.exportReport();
            
            JOptionPane.showMessageDialog(
                null,
                "El reporte fue exportado exitosamente.",
                "Éxito",
                JOptionPane.INFORMATION_MESSAGE
        );

        } catch (JRException ex) {
            JOptionPane.showMessageDialog(null, "Error al generar el reporte: " + ex.getMessage());
        }
    }
     //Metodo para exportar como pdf de los asientos
     public void asientosReportPDF(Date fechaI, Date fechaF, int idUsuario) throws SQLException, JRException {
        try {
            Connections con = new Connections();

            // Cargar el reporte Jasper
            JasperReport jasperMasterReport = (JasperReport) JRLoader.loadObject(
                    getClass().getResource("/Report/Asientos.jasper")
            );

            // Pasar los parámetros al reporte
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("fechaInicio", fechaI);
            parameters.put("fechaFin", fechaF);
            if(idUsuario==-1){
                parameters.put("idUsuario", null);
            }else{
                parameters.put("idUsuario", idUsuario);
            }

            // Llenar el reporte con datos y parámetros
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperMasterReport, parameters, con.connect());
            JasperViewer reporteMaster = new JasperViewer(jasperPrint,false);
            reporteMaster.setTitle(" JMR - Informe Contador ");
            reporteMaster.setVisible(true);

        } catch (JRException ex) {
            JOptionPane.showMessageDialog(null, "Error al generar el reporte: " + ex.getMessage());
        }
    }
     
     //Metodo para exportar como pdf del libro mayor
     public void asientosReportExcel(Date fechaI, Date fechaF, int idUsuario) throws SQLException, JRException {
        try {
            Connections con = new Connections();

            // Cargar el reporte Jasper
            JasperReport jasperMasterReport = (JasperReport) JRLoader.loadObject(
                    getClass().getResource("/Report/Asientos.jasper")
            );

            // Pasar los parámetros al reporte
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("fechaInicio", fechaI);
            parameters.put("fechaFin", fechaF);
            if(idUsuario==-1){
                parameters.put("idUsuario", null);
            }else{
                parameters.put("idUsuario", idUsuario);
            }

            // Llenar el reporte con datos y parámetros
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperMasterReport, parameters, con.connect());
            
             // Exportar a Excel
            JRXlsxExporter exporter = new JRXlsxExporter();
            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput("C:/Users/Usuario/Desktop/Reportes/Asientos.xlsx"));

            // Configuración para Excel "limpio"
            SimpleXlsxReportConfiguration configuration = new SimpleXlsxReportConfiguration();
            configuration.setDetectCellType(true);
            configuration.setCollapseRowSpan(false);
            configuration.setOnePagePerSheet(false);
            configuration.setRemoveEmptySpaceBetweenRows(true);
            configuration.setRemoveEmptySpaceBetweenColumns(true);
            configuration.setWhitePageBackground(false);
            configuration.setIgnoreGraphics(true);
            configuration.setWrapText(true);

        exporter.setConfiguration(configuration);

            exporter.exportReport();
            
            JOptionPane.showMessageDialog(
                null,
                "El reporte fue exportado exitosamente.",
                "Éxito",
                JOptionPane.INFORMATION_MESSAGE
        );

        } catch (JRException ex) {
            JOptionPane.showMessageDialog(null, "Error al generar el reporte: " + ex.getMessage());
        }
    }
     //Metodo para exportar el reporte de ventas como PDF
       public void generarReporteVentasPDF(List<SalesBook> ventas) {
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
        //Metodo para exportar el reporte de ventas como PDF
       public void generarReporteVentasExcel(List<SalesBook> ventas) {
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
             // Exportar a Excel
            JRXlsxExporter exporter = new JRXlsxExporter();
            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput("C:/Users/Usuario/Desktop/Reportes/ReporteVentas.xlsx"));

            // Configuración para Excel "limpio"
            SimpleXlsxReportConfiguration configuration = new SimpleXlsxReportConfiguration();
            configuration.setDetectCellType(true);
            configuration.setCollapseRowSpan(false);
            configuration.setOnePagePerSheet(false);
            configuration.setRemoveEmptySpaceBetweenRows(true);
            configuration.setRemoveEmptySpaceBetweenColumns(true);
            configuration.setWhitePageBackground(false);
            configuration.setIgnoreGraphics(true);
            configuration.setWrapText(true);

        exporter.setConfiguration(configuration);

            exporter.exportReport();
            
            JOptionPane.showMessageDialog(
                null,
                "El reporte fue exportado exitosamente.",
                "Éxito",
                JOptionPane.INFORMATION_MESSAGE
        );

    } catch (Exception e) {
        e.printStackTrace();
    }
}
    //Metodo para exportar la factura como pdf
        public void generarFacturaPDF(int idFactura) throws SQLException, JRException {
        try {
            Connections con = new Connections();

            // Cargar el reporte Jasper
            JasperReport jasperMasterReport = (JasperReport) JRLoader.loadObject(
                    getClass().getResource("/Report/Factura.jasper")
            );
            
            // Pasar los parámetros al reporte
            Map<String, Object> parameters = new HashMap<>();        
                parameters.put("facturaID", idFactura);
            

            // Llenar el reporte con datos y parámetros
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperMasterReport, parameters, con.connect());
            JasperViewer reporteMaster = new JasperViewer(jasperPrint,false);
            reporteMaster.setTitle(" JMR - Informe Contador ");
            reporteMaster.setVisible(true);
            
            
        } catch (JRException ex) {
            JOptionPane.showMessageDialog(null, "Error al generar el reporte: " + ex.getMessage());
        }
    }
}
