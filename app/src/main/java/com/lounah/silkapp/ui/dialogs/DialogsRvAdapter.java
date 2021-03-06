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
import com.lounah.silkapp.ui.ItemClickListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DialogsRvAdapter extends RecyclerView.Adapter<DialogsRvAdapter.ViewHolder> {

    private List<Dialog> dialogs = new ArrayList<>();

    private final Context context;

    private final ClickListener clickListener;

    public DialogsRvAdapter(@NonNull final Context context, @NonNull final ClickListener clickListener) {
        this.context = context;
        this.clickListener = clickListener;
    }

    @Override
    public DialogsRvAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dialog, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DialogsRvAdapter.ViewHolder holder, int position) {
        Dialog dialog = dialogs.get(position);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yy/ hh:mm");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+3"));
        String date = sdf.format(dialog.getLastMessageTime());

        holder.time.setText(date);
        holder.message.setText(dialog.getLastMessageText());
        holder.username.setText("ШтораДома");
        holder.avatar.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.home_main));


        if (dialog.getHostStatus().equals("Online"))
            holder.status.setVisibility(View.VISIBLE);

        if (dialog.getLastMessageAuthor().equals("ШтораДома")) {
            holder.myAvatar.setVisibility(View.GONE);
            if (dialog.getLastMessageStatus().equals("Unread"))
                holder.rlDialogs.setBackground(ContextCompat.getDrawable(context, R.drawable.rl_unread_background));
        } else {
            holder.myAvatar.setVisibility(View.VISIBLE);
            Picasso.with(context)
                    .load(Uri.parse(dialog.getParticipantAvatarUrl()))
                    .into(holder.myAvatar);
            if (dialog.getLastMessageStatus().equals("Unread"))
                holder.message.setBackground(ContextCompat.getDrawable(context, R.drawable.message_text_background));

        }


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

        @OnClick(R.id.rl_dialogs)
        public void onCommentsBtnClicked() {
            clickListener.onClick(dialogs.get(getAdapterPosition()).getDialogId());
            Log.i("ADAPTER ID", itemView.getId() + "  ");
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
