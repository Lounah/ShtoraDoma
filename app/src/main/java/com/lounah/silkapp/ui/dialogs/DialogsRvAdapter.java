package com.lounah.silkapp.ui.dialogs;


import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lounah.silkapp.R;
import com.lounah.silkapp.data.model.Dialog;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DialogsRvAdapter extends RecyclerView.Adapter<DialogsRvAdapter.ViewHolder> {

    private List<Dialog> dialogs = new ArrayList<>();

    private final Context context;

    public DialogsRvAdapter(@NonNull final Context context) {
        this.context = context;
    }

    @Override
    public DialogsRvAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dialog, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DialogsRvAdapter.ViewHolder holder, int position) {
        Dialog dialog = dialogs.get(position);

        holder.time.setText(dialog.getDate().toString());
        holder.message.setText(dialog.getLast_message());
        holder.username.setText("ШтораДома");
        holder.avatar.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.home_main));

        if (!dialog.getLast_message_author().equals("ШтораДома")) {
            holder.myAvatar.setVisibility(View.VISIBLE);
            Picasso.with(context)
                    .load(Uri.parse(dialog.getParticipant_avatar_url()))
                    .into(holder.myAvatar);

            if (!dialog.getLast_message_status().equals("read"))
                holder.message.setBackground(ContextCompat.getDrawable(context, R.drawable.message_text_background));

        } else {
            if (!dialog.getLast_message_status().equals("read"))
                holder.rlDialogs.setBackground(ContextCompat.getDrawable(context, R.drawable.rl_unread_background));
        }
// todo
//        if (dialog.getAuthor_status().equals("Online"))
            holder.status.setVisibility(View.VISIBLE);


    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_dialogs_avatar)
        ImageView avatar;

        @BindView(R.id.iv_dialogs_my_avatar)
        ImageView myAvatar;

        @BindView(R.id.iv_dialogs_status)
        ImageView status;

        @BindView(R.id.tv_dialogs_time)
        TextView time;

        @BindView(R.id.tv_dialogs_username)
        TextView username;

        @BindView(R.id.tv_last_message)
        TextView message;

        @BindView(R.id.rl_dialogs)
        RelativeLayout rlDialogs;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

    @Override
    public int getItemCount() {
        return dialogs.size();
    }

    public void updateDataSet(@NonNull final List<Dialog> dialogs) {
        this.dialogs = dialogs;
        notifyDataSetChanged();
    }
}
