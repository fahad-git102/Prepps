package com.fahaddev.prepps.adapters;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.fahaddev.prepps.R;
import com.fahaddev.prepps.models.ArticlesModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ArticlesAdapter extends RecyclerView.Adapter<ArticlesAdapter.ArticlesVHolder> {

    List<ArticlesModel> list ;
    Context context;
    RecyclerView rv;

    public ArticlesAdapter(List<ArticlesModel> list, Context context, RecyclerView rv) {
        this.list = list;
        this.context = context;
        this.rv = rv;
    }

    @NonNull
    @Override
    public ArticlesVHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.articles_new_layout, parent, false);
        return new ArticlesVHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticlesVHolder holder, int position) {
        holder.tvTitle.setText(list.get(position).getTitle());
        Glide.with(context).load(list.get(position).getImage()).placeholder(R.drawable.default_thumbnail).into(holder.imgBack);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ArticlesVHolder extends RecyclerView.ViewHolder{

        ImageView imgBack;
        TextView tvTitle;

        public ArticlesVHolder(@NonNull View itemView) {
            super(itemView);
            imgBack = itemView.findViewById(R.id.background);
            tvTitle = itemView.findViewById(R.id.articleTitle);
        }
    }
}
