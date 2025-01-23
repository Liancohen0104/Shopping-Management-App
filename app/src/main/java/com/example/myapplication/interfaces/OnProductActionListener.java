package com.example.myapplication.interfaces;
import com.example.myapplication.models.DataModel;

public interface OnProductActionListener {
    void onQuantityChange(DataModel product, int newQuantity);
    void onSetQuantityOfProduct(DataModel product);
    void onRemoveProduct(DataModel product);
}

