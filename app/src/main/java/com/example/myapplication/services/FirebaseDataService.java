package com.example.myapplication.services;

import com.example.myapplication.models.DataModel;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class FirebaseDataService {

    public interface FirestoreCallback {
        void onSuccess(ArrayList<DataModel> productList);
        void onFailure(Exception e);
    }

    public static void getAllProducts(final FirestoreCallback callback)
    {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference productsRef = db.collection("products");

        ArrayList<DataModel> arrProducts = new ArrayList<>();

        productsRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    // שליפת השדות מתוך המסמך
                    String name = document.getString("name");
                    int quantity = document.getLong("quantity").intValue();
                    double price = document.getDouble("price");
                    String image = document.getString("imageName");

                    // יצירת אובייקט DataModel והוספתו לרשימה
                    DataModel product = new DataModel(name, quantity, price, image);
                    arrProducts.add(product);
                }
                callback.onSuccess(arrProducts);
            } else {
                callback.onFailure(task.getException());
            }
        });
    }
}
