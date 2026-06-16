package com.example.stylish;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private Context context;
    private List<ProductModel> productList;

    public ProductAdapter(Context context, List<ProductModel> productList) {
        this.context = context;
        this.productList = productList;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // আমাদের বানানো item_product.xml কে এখানে লিংক করছি
        View view = LayoutInflater.from(context).inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        ProductModel product = productList.get(position);

        // ডেটাগুলো কার্ডের টেক্সটভিউ আর ইমেজভিউতে বসাচ্ছি
        holder.title.setText(product.getTitle());
        holder.description.setText(product.getDescription());
        holder.price.setText(product.getPrice());
        holder.ratingCount.setText(product.getRatingCount());
        holder.image.setImageResource(product.getImageResId());

        // প্রোডাক্টে ক্লিক করলে কী হবে তার লজিক পরে এখানে দেবো
        holder.itemView.setOnClickListener(v -> {
            // Toast.makeText(context, product.getTitle() + " Clicked!", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    // নতুন ডেটা (যেমন Mens থেকে Womens এ গেলে) আপডেট করার মেথড
    public void updateList(List<ProductModel> newList) {
        this.productList = newList;
        notifyDataSetChanged();
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView title, description, price, ratingCount;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            // item_product.xml এর আইডিগুলো ফাইন্ড করা
            image = itemView.findViewById(R.id.product_image);
            title = itemView.findViewById(R.id.product_title);
            description = itemView.findViewById(R.id.product_desc);
            price = itemView.findViewById(R.id.product_price);
            ratingCount = itemView.findViewById(R.id.product_rating_count);
        }
    }
}