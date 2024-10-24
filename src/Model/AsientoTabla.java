
package Model;

import java.util.Date;

/**
 *
 * @author elias
 */
public class AsientoTabla {
    private Date fecha;
    private String destino;
    private String  cuenta;
    private float importe;
    private String descripcion;
    private int idCuenta;

    public AsientoTabla(Date fecha, String destino, String cuenta, float importe, String descripcion, int idCuenta) {
        this.fecha = fecha;
        this.destino = destino;
        this.cuenta = cuenta;
        this.importe = importe;
        this.descripcion = descripcion;
        this.idCuenta = idCuenta;
    }

    public AsientoTabla() {
    }
    

    public int getIdCuenta() {
        return idCuenta;
    }

    public void setIdCuenta(int idCuenta) {
        this.idCuenta = idCuenta;
    }

    
    
    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public String getCuenta() {
        return cuenta;
    }

    public void setCuenta(String cuenta) {
        this.cuenta = cuenta;
    }

  

    public float getImporte() {
        return importe;
    }

    public void setImporte(float importe) {
        this.importe = importe;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    
    
}
