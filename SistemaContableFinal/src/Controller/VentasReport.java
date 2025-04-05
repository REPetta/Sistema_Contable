/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Model.SalesBook;
import java.util.Iterator;
import java.util.List;
import net.sf.jasperreports.engine.JRDataSource;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;


public class VentasReport implements JRDataSource {
    private final Iterator<SalesBook> iterator;
    private SalesBook actual;

    public VentasReport(List<SalesBook> ventas) {
        this.iterator = ventas.iterator();
    }

    @Override
    public boolean next() throws JRException {
        if (iterator.hasNext()) {
            actual = iterator.next();
            return true;
        }
        return false;
    }

    @Override
    public Object getFieldValue(JRField jrField) throws JRException {
        if (actual == null) return null;

        switch (jrField.getName()) {
            case "producto":
                return actual.getProducto();
            case "cantidadVendida":
                return actual.getCantidad_vendida();
            case "totalRecaudado":
                return actual.getTotal_reacudado();
            case "variacionCantidad":
                return actual.getVariacion_cantidad();
            case "variacionRecaudado":
                return actual.getVariacion_recaudado();
            default:
                return null;
        }
    }
}


