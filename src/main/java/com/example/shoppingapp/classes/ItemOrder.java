package com.example.shoppingapp.classes;

public class ItemOrder {
    private int Quantity;
    private int ProductID;
    private int OrderID;
    private int ItemID;

    public ItemOrder(int productID, int quantity, int orderID) {
        this.ProductID = productID;
        this.Quantity = quantity;
        this.ItemID = 0;
        this.OrderID = orderID;
    }

    public ItemOrder(int productID, int quantity, int orderID, int itemID) {
        this.ProductID = productID;
        this.Quantity = quantity;
        this.ItemID = itemID;
        this.OrderID = orderID;
    }

    public int getProductID() {
        return this.ProductID;
    }
    public int getQuantity() {
        return this.Quantity;
    }
    public int getItemID() {return this.ItemID;}
    public int getOrderID() {return this.OrderID;}
    public void setProductID(int productID) {
        this.ProductID = productID;
    }
    public void setQuantity(int quantity) {
        this.Quantity = quantity;
    }

    @Override
    public String toString() {
        return "Product ID: " + (this.ProductID) + ", Quantity: " + (this.Quantity);
    }
}
