
package Model;

import java.util.List;

public class SaleNode {
    
    private Sale sale;
    private Customer customer;
    private SaleType saleType;
    private List<SaleDetails> salesDetails;

    public SaleNode() {
    }

    public SaleNode(Sale sale, Customer customer, SaleType saleType, List<SaleDetails> salesDetails) {
        this.sale = sale;
        this.customer = customer;
        this.saleType = saleType;
        this.salesDetails = salesDetails;
    }

    public Sale getSale() {
        return sale;
    }

    public void setSale(Sale sale) {
        this.sale = sale;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public SaleType getSaleType() {
        return saleType;
    }

    public void setSaleType(SaleType saleType) {
        this.saleType = saleType;
    }

    public List<SaleDetails> getSalesDetails() {
        return salesDetails;
    }

    public void setSalesDetails(List<SaleDetails> salesDetails) {
        this.salesDetails = salesDetails;
    }
    
    public void addSalesDetails(SaleDetails saleDetail){
        this.salesDetails.add(saleDetail);
    }
}
