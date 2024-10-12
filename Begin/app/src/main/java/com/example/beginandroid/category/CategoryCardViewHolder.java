package com.example.beginandroid.category;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.beginandroid.R;

public class CategoryCardViewHolder extends RecyclerView.ViewHolder {
    private TextView categoryName;
    private ImageView ivCategoryImage;

    public CategoryCardViewHolder(@NonNull View itemView) {
        super(itemView);
        categoryName = itemView.findViewById(R.id.categoryName);
        ivCategoryImage = itemView.findViewById(R.id.ivCategoryImage);
    }

    public TextView getCategoryName() {
        return categoryName;
    }

    public ImageView getIvCategoryImage() {
        return ivCategoryImage;
    }

}