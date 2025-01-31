/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

public class SaleDetails {
    
    private int idSaleDetails;
    private int idItem;
    private int idSale;
    private int quantity;
    private double salePrice;
    private double subTotal;

    public SaleDetails() {
    }

    public SaleDetails(int idSaleDetails, int idItem, int idSale, int quantity, double salePrice, double subTotal) {
        this.idSaleDetails = idSaleDetails;
        this.idItem = idItem;
        this.idSale = idSale;
        this.quantity = quantity;
        this.salePrice = salePrice;
        this.subTotal = subTotal;
    }

    public int getIdSaleDetails() {
        return idSaleDetails;
    }

    public void setIdSaleDetails(int idSaleDetails) {
        this.idSaleDetails = idSaleDetails;
    }

    public int getIdItem() {
        return idItem;
    }

    public void setIdItem(int idItem) {
        this.idItem = idItem;
    }

    public int getIdSale() {
        return idSale;
    }

    public void setIdSale(int idSale) {
        this.idSale = idSale;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(double salePrice) {
        this.salePrice = salePrice;
    }

    public double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
    }
    
    
}
