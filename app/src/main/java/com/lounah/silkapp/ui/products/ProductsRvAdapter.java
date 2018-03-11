package com.lounah.silkapp.ui.products;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lounah.silkapp.R;
import com.lounah.silkapp.data.model.Dialog;
import com.lounah.silkapp.data.model.Product;
import com.lounah.silkapp.ui.ItemClickListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProductsRvAdapter extends RecyclerView.Adapter<ProductsRvAdapter.ViewHolder> {

    private List<Product> products = new ArrayList<>();

    private final Context context;

    private final ItemClickListener listener;

    @Override
    public long getItemId(int position) {
      return products.get(position).getId();
    }

    @Override
    public int getItemViewType(int position) {
        return products.get(position).getId();
    }

    public ProductsRvAdapter(@NonNull final Context context, ItemClickListener listener) {
        this.context = context;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductsRvAdapter.ViewHolder holder, int position) {
        Product product = products.get(position);

        Picasso.with(context)
                .load(product.getImage_header_url())
                .networkPolicy(NetworkPolicy.OFFLINE)
                .placeholder(R.drawable.placeholder)
                .into(holder.ivHeader, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {
                        Picasso.with(context)
                                .load(product.getImage_header_url())
                                .placeholder(R.drawable.placeholder)
                                .into(holder.ivHeader);
                    }
                });

        holder.comments.setText(String.valueOf(product.getComments_quantity()));

        holder.tvHeader.setText(product.getHeader());

    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.iv_product)
        ImageView ivHeader;

        @BindView(R.id.tv_products_header)
        TextView tvHeader;

        @BindView(R.id.ib_like)
        ImageButton ibLike;

        @BindView(R.id.ib_comments)
        ImageButton ibComments;

        @BindView(R.id.ib_share)
        ImageButton ibShare;

        @BindView(R.id.tv_comments_quantity)
        TextView comments;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }


        @OnClick(R.id.ib_comments)
        public void onCommentsBtnClicked() {
            listener.onItemClicked(products.get(getAdapterPosition()).getId());
            Log.i("ADAPTER ID", itemView.getId() + "  ");
        }

    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public void updateDataSet(@NonNull final List<Product> products) {
        this.products = products;
        notifyDataSetChanged();
    }
}
