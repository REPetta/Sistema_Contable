
package Model;

import java.util.Date;

/**
 *
 * @author Rodrigo
 */
public class Bill {
    
    private int idBill;
    private int idSale;
    private int billNumber;
    private Date billDate;
    private double billTotal;
    private char billState;
    private char billType;

    public Bill() {
    }

    public Bill(int idBill, int idSale, int billNumber, Date billDate, double billTotal, char billState, char billType) {
        this.idBill = idBill;
        this.idSale = idSale;
        this.billNumber = billNumber;
        this.billDate = billDate;
        this.billTotal = billTotal;
        this.billState = billState;
        this.billType = billType;
    }

    public int getIdBill() {
        return idBill;
    }

    public void setIdBill(int idBill) {
        this.idBill = idBill;
    }

    public int getIdSale() {
        return idSale;
    }

    public void setIdSale(int idSale) {
        this.idSale = idSale;
    }

    public int getBillNumber() {
        return billNumber;
    }

    public void setBillNumber(int billNumber) {
        this.billNumber = billNumber;
    }

    public Date getBillDate() {
        return billDate;
    }

    public void setBillDate(Date billDate) {
        this.billDate = billDate;
    }

    public double getBillTotal() {
        return billTotal;
    }

    public void setBillTotal(double billTotal) {
        this.billTotal = billTotal;
    }

    public char getBillState() {
        return billState;
    }

    public void setBillState(char billState) {
        this.billState = billState;
    }

    public char getBillType() {
        return billType;
    }

    public void setBillType(char billType) {
        this.billType = billType;
    }
    
    
}
