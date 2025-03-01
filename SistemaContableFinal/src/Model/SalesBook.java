
package Model;

public class SalesBook {
    
   private String producto;
   private int cantidad_vendida;
   private int total_reacudado;
   private double variacion_cantidad;
   private double variacion_recaudado;
   private int cantidad_vendida_anterior;
   private int cantidad_recaudado_anterior;

    public SalesBook(String producto, int cantidad_vendida, int total_reacudado, double variacion_cantidad, double variacion_recaudado) {
        this.producto = producto;
        this.cantidad_vendida = cantidad_vendida;
        this.total_reacudado = total_reacudado;
        this.variacion_cantidad = variacion_cantidad;
        this.variacion_recaudado = variacion_recaudado;
    }

    public SalesBook(String producto, int cantidad_vendida, int total_reacudado, double variacion_cantidad, double variacion_recaudado, int cantidad_vendida_anterior, int cantidad_recaudado_anterior) {
        this.producto = producto;
        this.cantidad_vendida = cantidad_vendida;
        this.total_reacudado = total_reacudado;
        this.variacion_cantidad = variacion_cantidad;
        this.variacion_recaudado = variacion_recaudado;
        this.cantidad_vendida_anterior = cantidad_vendida_anterior;
        this.cantidad_recaudado_anterior = cantidad_recaudado_anterior;
    }

    public SalesBook() {
    }

    public int getCantidad_vendida_anterior() {
        return cantidad_vendida_anterior;
    }

    public void setCantidad_vendida_anterior(int cantidad_vendida_anterior) {
        this.cantidad_vendida_anterior = cantidad_vendida_anterior;
    }

    public int getCantidad_recaudado_anterior() {
        return cantidad_recaudado_anterior;
    }

    public void setCantidad_recaudado_anterior(int cantidad_recaudado_anterior) {
        this.cantidad_recaudado_anterior = cantidad_recaudado_anterior;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public int getCantidad_vendida() {
        return cantidad_vendida;
    }

    public void setCantidad_vendida(int cantidad_vendida) {
        this.cantidad_vendida = cantidad_vendida;
    }

    public int getTotal_reacudado() {
        return total_reacudado;
    }

    public void setTotal_reacudado(int total_reacudado) {
        this.total_reacudado = total_reacudado;
    }

    public double getVariacion_cantidad() {
        return variacion_cantidad;
    }

    public void setVariacion_cantidad(double variacion_cantidad) {
        this.variacion_cantidad = variacion_cantidad;
    }

    public double getVariacion_recaudado() {
        return variacion_recaudado;
    }

    public void setVariacion_recaudado(double variacion_recaudado) {
        this.variacion_recaudado = variacion_recaudado;
    }
   
   
}
