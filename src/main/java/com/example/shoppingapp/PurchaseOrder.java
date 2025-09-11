package com.example.shoppingapp;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class PurchaseOrder {
    private int UserID;
    private ArrayList<ItemOrder> ItemsPurchased;
    private boolean PurchaseDone;
    private LocalDateTime PurchaseDate;

    public PurchaseOrder(int userID) {
        this.UserID = userID;
        this.ItemsPurchased = new ArrayList<ItemOrder>();
        this.PurchaseDone = false;
        this.PurchaseDate = null;
    }

    public int getUserID() {
        return this.UserID;
    }
    public ArrayList<ItemOrder> getItemsPurchased() {
        return this.ItemsPurchased;
    }
    public boolean getPurchaseDone() {
        return this.PurchaseDone;
    }
    public LocalDateTime getPurchaseDate() {
        return this.PurchaseDate;
    }
    public void setUserID(int userID) {
        this.UserID = userID;
    }
    public void setPurchaseDate(LocalDateTime purchaseDate) {
        this.PurchaseDate = purchaseDate;
    }

    public String toString() {
        return "User ID: " + (this.UserID) + ", Items Purchased: " + (this.ItemsPurchased) +
                ", Purchase Date: " + (this.PurchaseDate);
    }

    /**
     * Changes a PurchaseOrder to finished
     */
    public void setPurchaseOrderTrue() {
        this.PurchaseDone = true;
        this.PurchaseDate = LocalDateTime.now();
    }

    /**
     * Adds a new ItemOrder instance to a PurchaseOrder.
     * If the Item in ItemOrder was already in the
     * PurchaseOrder, only the quantity is updated
     **/
    public void addItemOrder(ItemOrder itemOrder) {
        if (!ItemsPurchased.contains(itemOrder)) {
            ItemsPurchased.add(itemOrder);
        } else {
            String productName = itemOrder.getProduct().getTitle(), namei;
            int auxQuantity, additionalQuantity;

            for (int i = 0; i < ItemsPurchased.size(); i++) {
                namei = ItemsPurchased.get(i).getProduct().getTitle();

                if(productName.equals(namei)) {
                    auxQuantity = ItemsPurchased.get(i).getQuantity();
                    additionalQuantity = itemOrder.getQuantity();
                    itemOrder.setQuantity(additionalQuantity + auxQuantity);
                    ItemsPurchased.remove(i);
                    ItemsPurchased.add(itemOrder);
                    break;
                }
            }
        }
    }

    /**
     * Calculates the total amount of the PurchaseOrder.
     **/
    public static float calcularImporte(ArrayList<ItemOrder> itemsPurchased) {
        // The amount starts at 5€ due to shipping costs.
        float totalAmount = 5, pricei;
        Product producti = null;
        int length = itemsPurchased.size()-1, quantityi;

        for (int i = 0; i <= length; i++) {
            producti = itemsPurchased.get(i).getProduct();
            quantityi = itemsPurchased.get(i).getQuantity();

            pricei = producti.getPrice();
            totalAmount += pricei*quantityi;
        }
        return totalAmount;
    }
}
