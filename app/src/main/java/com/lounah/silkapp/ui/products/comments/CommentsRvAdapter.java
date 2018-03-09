package com.lounah.silkapp.ui.products.comments;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment, parent, false);
        return new CommentsRvAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CommentsRvAdapter.ViewHolder holder, int position) {
        final Comment comment = comments.get(position);

        holder.text.setText(comment.getText());

        holder.username.setText(comment.getAuthor_username());

    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        @Nullable
        @BindView(R.id.iv_comment_avatar)
        ImageView avatar;

        @BindView(R.id.tv_comment_text)
        TextView text;

        @BindView(R.id.tv_comment_username)
        TextView username;

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
