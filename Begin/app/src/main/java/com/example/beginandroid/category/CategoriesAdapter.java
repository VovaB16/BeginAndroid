package com.example.beginandroid.category;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.beginandroid.R;

import com.example.beginandroid.dto.CategoryItemDTO;

import java.util.List;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoryCardViewHolder> {
    private List<CategoryItemDTO> items;


    public CategoriesAdapter(List<CategoryItemDTO> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public CategoryCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.category_view_item, parent, false);
        return new CategoryCardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryCardViewHolder holder, int position) {
        if(items!=null && position<items.size()) {
            CategoryItemDTO item = items.get(position);
            holder.getCategoryName().setText(item.getName());
            String url = com.example.simba.constants.Urls.BASE+item.getImagePath();

            Glide.with(holder.itemView.getContext())
                    .load(url)
                    //.apply(new RequestOptions().override(400))
                    .into(holder.getIvCategoryImage());

        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
