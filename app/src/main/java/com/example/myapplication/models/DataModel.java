package com.example.myapplication.models;
import com.google.firebase.database.Exclude;

public class DataModel
{
    private String name;
    private int quantity;
    private double price;
    private double originalPrice;
    private String imageName;

    public DataModel() {
    }

    public DataModel(String name, int quantity, double price, String imageName)
    {
        this.name = name;
        this.quantity = quantity;
        this.imageName = imageName;
        this.price = price;
        this.originalPrice = price;

    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageName() {
        return imageName;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice()
    {
        return price;
    }

    public void setPrice(double price)
    {
        this.price = price;
    }

    public double getOriginalPrice() {
        return originalPrice;
    }

    public void resetToOriginalPrice() {
        this.price = originalPrice;
    }
}








