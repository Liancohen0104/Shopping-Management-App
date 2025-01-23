package com.example.myapplication.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myapplication.R;
import com.example.myapplication.interfaces.OnProductActionListener;
import com.example.myapplication.models.DataModel;

import java.util.List;

public class CustomeAdapterProducts extends RecyclerView.Adapter<CustomeAdapterProducts.ViewHolder> {
    private List<DataModel> products;
    private OnProductActionListener listener;
    private Context context;

    public CustomeAdapterProducts(Context context, List<DataModel> products, OnProductActionListener listener) {
        this.products = products;
        this.listener = listener;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DataModel product = products.get(position);

        holder.productName.setText(product.getName());
        holder.productQuantity.setText(String.valueOf(product.getQuantity()));
        holder.productPrice.setText(String.valueOf(product.getPrice()));

        // קביעת תמונת המוצר
        if (product.getImageName() != null && !product.getImageName().isEmpty()) {
            int imageResId = context.getResources().getIdentifier(
                    product.getImageName(),
                    "drawable",
                    context.getPackageName()
            );

            if (imageResId != 0) {
                holder.imageView.setImageResource(imageResId);
            } else {
                holder.imageView.setImageResource(R.drawable.default_image);
            }
        } else {
            holder.imageView.setImageResource(R.drawable.default_image);
        }

        holder.increaseButton.setOnClickListener(v -> {
            int newQuantity = product.getQuantity() + 1;
            listener.onQuantityChange(product, newQuantity);
        });

        holder.decreaseButton.setOnClickListener(v -> {
            int newQuantity = Math.max(1, product.getQuantity() - 1);
            listener.onQuantityChange(product, newQuantity);
        });

        holder.setQuantityButton.setOnClickListener(v -> {
            listener.onSetQuantityOfProduct(product);
        });

        holder.removeButton.setOnClickListener(v -> {
            listener.onRemoveProduct(product);
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView productName, productQuantity, productPrice;
        ImageView imageView;
        Button increaseButton, decreaseButton, setQuantityButton, removeButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.productNameTextView);
            productQuantity = itemView.findViewById(R.id.productQuantityTextView);
            increaseButton = itemView.findViewById(R.id.increaseQuantityButton);
            decreaseButton = itemView.findViewById(R.id.decreaseQuantityButton);
            setQuantityButton = itemView.findViewById(R.id.setQuantity);
            imageView = itemView.findViewById(R.id.imageView);
            removeButton = itemView.findViewById(R.id.removeButton);
            productPrice = itemView.findViewById(R.id.productPrice);
        }
    }
}