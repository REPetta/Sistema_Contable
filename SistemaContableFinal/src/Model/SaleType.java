
package Model;

/**
 *
 * @author Rodrigo
 */
public class SaleType {
    
    private int idSaleType;
    private String saleDescription;
    private String saleState;
    private int paymentTerm;
    private int quotas;
    private double  discount;
    private int code;

    public SaleType() {
    }

    public SaleType(int idSaleType, String saleDescription, String saleState, int paymentTerm, int quotas, double discount,int code) {
        this.idSaleType = idSaleType;
        this.saleDescription = saleDescription;
        this.saleState = saleState;
        this.paymentTerm = paymentTerm;
        this.quotas = quotas;
        this.discount = discount;
        this.code=code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
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

    public String getSaleState() {
        return saleState;
    }

    public void setSaleState(String saleState) {
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
