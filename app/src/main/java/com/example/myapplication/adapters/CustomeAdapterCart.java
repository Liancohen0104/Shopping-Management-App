package com.example.myapplication.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.models.DataModel;

import java.util.List;

public class CustomeAdapterCart extends RecyclerView.Adapter<CustomeAdapterCart.ViewHolder> {
    private List<DataModel> productList;
    private Context context;

    public CustomeAdapterCart(Context context, List<DataModel> productList) {
        this.context = context;
        this.productList = productList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_card_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DataModel product = productList.get(position);

        holder.productName.setText(product.getName());
        holder.productPrice.setText(String.valueOf(product.getPrice()));
        holder.productQuantity.setText(String.valueOf(product.getQuantity()));

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
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView productName, productPrice, productNis, productQuantity;
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.productNameTextView);
            productPrice = itemView.findViewById(R.id.productPrice);
            productQuantity = itemView.findViewById(R.id.productQuantityTextView);
            productNis = itemView.findViewById(R.id.productNis);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }
}
