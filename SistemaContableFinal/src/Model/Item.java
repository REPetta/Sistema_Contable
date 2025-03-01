
package Model;

/**
 *
 * @author Rodrigo
 */
public class Item {
    
    private int idItem;
    private String itemName;
    private String itemDescription;
    private double unitPrice;
    private int stock;
    private int itemCode;
    private int stockMin;

    public Item() {
    }

    public Item(int idItem, String itemName, String itemDescription, double unitPrice, int stock) {
        this.idItem = idItem;
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.unitPrice = unitPrice;
        this.stock = stock;
    }

    public Item(int idItem, String itemName, String itemDescription, double unitPrice, int stock, int itemCode, int stockMin) {
        this.idItem = idItem;
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.unitPrice = unitPrice;
        this.stock = stock;
        this.itemCode = itemCode;
        this.stockMin=stockMin;
    }

    public int getStockMin() {
        return stockMin;
    }

    public void setStockMin(int stockMin) {
        this.stockMin = stockMin;
    }
     
    public int getItemCode() {
        return itemCode;
    }

    public void setItemCode(int itemCode) {
        this.itemCode = itemCode;
    }

    
    public int getIdItem() {
        return idItem;
    }

    public void setIdItem(int idItem) {
        this.idItem = idItem;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
    
    
}
