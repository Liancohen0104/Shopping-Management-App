package com.example.myapplication.fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myapplication.adapters.CustomeAdapterProducts;
import com.example.myapplication.interfaces.OnProductActionListener;
import com.example.myapplication.models.Student;
import com.example.myapplication.services.FirebaseDataService;
import com.google.firebase.auth.FirebaseAuth;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.example.myapplication.R;
import com.example.myapplication.models.DataModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RecycleView extends Fragment implements OnProductActionListener
{
    private ArrayList<DataModel>productSet;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private CustomeAdapterProducts adapter;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    TextView nameTextView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recycle_view, container, false);

        Button button = view.findViewById(R.id.showCartButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_RecycleView_to_Cart);
            }
        });

        // שליפת המוצרים מהשרת והצגתם
        FirebaseDataService.getAllProducts(new FirebaseDataService.FirestoreCallback() {
            @Override
            public void onSuccess(ArrayList<DataModel> products) {
                productSet.clear();
                productSet.addAll(products);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Exception e) {
                Toast.makeText(getContext(), "שגיאה בטעינת המוצרים" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        productSet = new ArrayList<>();
        recyclerView = view.findViewById(R.id.cartRecyclerView);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        // יצירת מתאם לרשימה
        adapter = new CustomeAdapterProducts(requireContext(), productSet, this);
        recyclerView.setAdapter(adapter);

        // הצגת שם המשתמש המחובר
        FirebaseAuth auth = FirebaseAuth.getInstance();
        nameTextView = view.findViewById(R.id.userNameTextView);
        if (auth.getCurrentUser() != null) {
            String email = auth.getCurrentUser().getEmail();
            nameTextView.setText("Hello, " + email);
        } else {
            nameTextView.setText("No user logged in");
        }

        return view;
    }

    public void SetQuantityOfProduct(String productName, int quantity, double price, String imageName) {
        String currentEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference usersRef = database.getReference("users");

        usersRef.orderByChild("email").equalTo(currentEmail).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    Student user = userSnapshot.getValue(Student.class);
                    String phone = user.getPhone();

                    // יצירת אובייקט מותאם אישית
                    Map<String, Object> productData = new HashMap<>();
                    productData.put("name", productName);
                    productData.put("quantity", quantity);
                    productData.put("price", price);
                    productData.put("imageName", imageName);

                    DatabaseReference productsRef = database.getReference("users").child(phone).child("products");
                    productsRef.child(productName).setValue(productData)
                            .addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    Toast.makeText(getContext(), "המוצר עודכן בהצלחה", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getContext(), "עדכון המוצר נכשל", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getContext(), "שגיאה בגישה לנתוני המשתמש", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void RemoveProductFromDatabase(String productName) {
        String currentEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference usersRef = database.getReference("users");

        usersRef.orderByChild("email").equalTo(currentEmail).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    Student user = userSnapshot.getValue(Student.class);
                    String phone = user.getPhone();

                    // הפניה לנתיב של המוצרים
                    DatabaseReference productsRef = database.getReference("users").child(phone).child("products");

                    productsRef.child(productName).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot productSnapshot) {
                            if (productSnapshot.exists()) {
                                // המוצר קיים - מחיקה
                                productsRef.child(productName).removeValue()
                                        .addOnCompleteListener(task -> {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(getContext(), "המוצר נמחק בהצלחה", Toast.LENGTH_SHORT).show();
                                            } else {
                                                Toast.makeText(getContext(), "מחיקת המוצר נכשלה", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            } else {
                                // המוצר לא קיים
                                Toast.makeText(getContext(), "מוצר זה לא בעגלה", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Toast.makeText(getContext(), "שגיאה בגישה לנתוני המוצר", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getContext(), "שגיאה בגישה לנתוני המשתמש", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onQuantityChange(DataModel product, int newQuantity) {
        // עדכון הכמות של המוצר
        product.setQuantity(newQuantity);
        // עדכון המחיר של המוצר
        product.setPrice(product.getOriginalPrice() * newQuantity);
        // עדכון התצוגה
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onSetQuantityOfProduct(DataModel product) {
        // הוספת המוצר למסד הנתונים
        SetQuantityOfProduct(product.getName(), product.getQuantity(), product.getPrice(), product.getImageName());
        // איפוס הכמות ל-1
        product.setQuantity(1);
        // איפוס המחיר לערך המקורי
        product.resetToOriginalPrice();
        // עדכון התצוגה
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onRemoveProduct(DataModel product) {
        RemoveProductFromDatabase(product.getName());
        adapter.notifyDataSetChanged();

    }
}