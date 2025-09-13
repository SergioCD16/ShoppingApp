package com.example.shoppingapp.classes;

public class ItemOrder {
    private int Quantity;
    private Product Product;

    public ItemOrder(Product product, int quantity) {
        this.Product = product;
        this.Quantity = quantity;
    }

    public Product getProduct() {
        return this.Product;
    }
    public int getQuantity() {
        return this.Quantity;
    }
    public void setProduct(Product product) {
        this.Product = product;
    }
    public void setQuantity(int quantity) {
        this.Quantity = quantity;
    }

    public String toString() {
        return "Product: " + (this.Product) + ", Quantity: " + (this.Quantity);
    }
}
