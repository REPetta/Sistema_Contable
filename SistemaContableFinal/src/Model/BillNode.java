
package Model;

import java.util.List;


public class BillNode {

    private Bill bill;
    private String customerName;
    private List<String> items;

    public BillNode(Bill bill, String customerName, List<String> itemName) {
        this.bill = bill;
        this.customerName = customerName;
        this.items = itemName;
    }

    public Bill getBill() {
        return bill;
    }

    public void setBill(Bill bill) {
        this.bill = bill;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public List<String> getItems() {
        return items;
    }

    public void setItems(List<String> itemName) {
        this.items = itemName;
    }
    
    public void setItem(String itemName){
        this.items.add(itemName);
    }

}
