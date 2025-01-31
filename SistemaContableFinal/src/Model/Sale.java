
package Model;

import java.util.Date;


public class Sale {
    
 private int idUser;
 private int idClient;
 private Date saleDate;
 private int receiptNumber;
 private double salesTotal;
 private char saleState;
 private int idSaleType;

    public Sale() {
    }

    public Sale(int idUser, int idClient, Date saleDate, int receiptNumber, double salesTotal, char saleState, int idSaleType) {
        this.idUser = idUser;
        this.idClient = idClient;
        this.saleDate = saleDate;
        this.receiptNumber = receiptNumber;
        this.salesTotal = salesTotal;
        this.saleState = saleState;
        this.idSaleType = idSaleType;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public Date getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(Date saleDate) {
        this.saleDate = saleDate;
    }

    public int getReceiptNumber() {
        return receiptNumber;
    }

    public void setReceiptNumber(int receiptNumber) {
        this.receiptNumber = receiptNumber;
    }

    public double getSalesTotal() {
        return salesTotal;
    }

    public void setSalesTotal(double salesTotal) {
        this.salesTotal = salesTotal;
    }

    public char getSaleState() {
        return saleState;
    }

    public void setSaleState(char saleState) {
        this.saleState = saleState;
    }

    public int getIdSaleType() {
        return idSaleType;
    }

    public void setIdSaleType(int idSaleType) {
        this.idSaleType = idSaleType;
    }
 
 
}
