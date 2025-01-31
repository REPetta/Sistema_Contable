
package Model;

/**
 *
 * @author Rodrigo
 */
public class SaleType {
    
    private int idSaleType;
    private String saleDescription;
    private char saleState;
    private int paymentTerm;
    private int quotas;
    private double  discount;

    public SaleType() {
    }

    public SaleType(int idSaleType, String saleDescription, char saleState, int paymentTerm, int quotas, double discount) {
        this.idSaleType = idSaleType;
        this.saleDescription = saleDescription;
        this.saleState = saleState;
        this.paymentTerm = paymentTerm;
        this.quotas = quotas;
        this.discount = discount;
    }

    public int getIdSaleType() {
        return idSaleType;
    }

    public void setIdSaleType(int idSaleType) {
        this.idSaleType = idSaleType;
    }

    public String getSaleDescription() {
        return saleDescription;
    }

    public void setSaleDescription(String saleDescription) {
        this.saleDescription = saleDescription;
    }

    public char getSaleState() {
        return saleState;
    }

    public void setSaleState(char saleState) {
        this.saleState = saleState;
    }

    public int getPaymentTerm() {
        return paymentTerm;
    }

    public void setPaymentTerm(int paymentTerm) {
        this.paymentTerm = paymentTerm;
    }

    public int getQuotas() {
        return quotas;
    }

    public void setQuotas(int quotas) {
        this.quotas = quotas;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }
    
    
    
}
