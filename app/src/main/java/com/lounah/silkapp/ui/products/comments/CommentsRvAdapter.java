package com.lounah.silkapp.ui.products.comments;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.lounah.silkapp.R;
import com.lounah.silkapp.data.model.Comment;
import com.lounah.silkapp.data.model.Product;
import com.lounah.silkapp.ui.ItemClickListener;
import com.lounah.silkapp.ui.products.ProductsRvAdapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CommentsRvAdapter extends RecyclerView.Adapter<CommentsRvAdapter.ViewHolder> {

    private List<Comment> comments = new ArrayList<>();

    private final Context context;

    @Override
    public long getItemId(int position) {
        return comments.get(position).getId();
    }

    public CommentsRvAdapter(@NonNull final Context context) {
        this.context = context;
    }

    @Override
    public CommentsRvAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
        return new CommentsRvAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CommentsRvAdapter.ViewHolder holder, int position) {


    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    public void updateDataSet(@NonNull final List<Comment> comments) {
        this.comments = comments;
        notifyDataSetChanged();
    }

}
