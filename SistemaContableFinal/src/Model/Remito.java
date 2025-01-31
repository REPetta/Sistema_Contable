
package Model;

import java.util.Date;

/**
 *
 * @author Rodrigo
 */
public class Remito {
    
    private int idRemito;
    private int idSale;
    private int remitoNumber;
    private Date remitoDate;
    private char remitoState;
    private String destiny;

    public Remito() {
    }

    public Remito(int idRemito, int idSale, int remitoNumber, Date remitoDate, char remitoState, String destiny) {
        this.idRemito = idRemito;
        this.idSale = idSale;
        this.remitoNumber = remitoNumber;
        this.remitoDate = remitoDate;
        this.remitoState = remitoState;
        this.destiny = destiny;
    }

    public int getIdRemito() {
        return idRemito;
    }

    public void setIdRemito(int idRemito) {
        this.idRemito = idRemito;
    }

    public int getIdSale() {
        return idSale;
    }

    public void setIdSale(int idSale) {
        this.idSale = idSale;
    }

    public int getRemitoNumber() {
        return remitoNumber;
    }

    public void setRemitoNumber(int remitoNumber) {
        this.remitoNumber = remitoNumber;
    }

    public Date getRemitoDate() {
        return remitoDate;
    }

    public void setRemitoDate(Date remitoDate) {
        this.remitoDate = remitoDate;
    }

    public char getRemitoState() {
        return remitoState;
    }

    public void setRemitoState(char remitoState) {
        this.remitoState = remitoState;
    }

    public String getDestiny() {
        return destiny;
    }

    public void setDestiny(String destiny) {
        this.destiny = destiny;
    }
    
    
}
