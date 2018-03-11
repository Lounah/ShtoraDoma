package com.lounah.silkapp.ui.conversation;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lounah.silkapp.R;
import com.lounah.silkapp.data.model.Message;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ConversationRvAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int MESSAGE_RECEIVED_VIEW_TYPE = 0;
    private static final int MESSAGE_SENT_VIEW_TYPE = 1;

    private List<Message> messages = new ArrayList<>();

    private final Context context;


    public ConversationRvAdapter(@NonNull final Context context) {
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
        final SharedPreferences sharedPreferences = context
                .getSharedPreferences("AppData", Context.MODE_PRIVATE);
        final int uid = sharedPreferences.getInt("id", 0);
        if (messages.get(position).getFrom_id() == uid)
            return MESSAGE_SENT_VIEW_TYPE; else
                return MESSAGE_RECEIVED_VIEW_TYPE;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case MESSAGE_RECEIVED_VIEW_TYPE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message_income, parent, false);
                return new ConversationRvAdapter.ReceivedMessageViewHolder(view);
            case MESSAGE_SENT_VIEW_TYPE:
                 view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message_outcome, parent, false);
                return new ConversationRvAdapter.SentMessageViewHolder(view);
                default: return new ConversationRvAdapter.SentMessageViewHolder(null);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final Message message = messages.get(position);
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+3"));
        String date = sdf.format(message.getDate());

        switch (holder.getItemViewType()) {
            case MESSAGE_RECEIVED_VIEW_TYPE:
                ((ReceivedMessageViewHolder) holder).text.setText(message.getText());
                ((ReceivedMessageViewHolder) holder).date.setText(date);
                break;
            case MESSAGE_SENT_VIEW_TYPE:
                ((SentMessageViewHolder) holder).text.setText(message.getText());
                ((SentMessageViewHolder) holder).date.setText(date);

                if (message.getStatus().equals("read")) {
                    ((SentMessageViewHolder) holder).status.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_done_all_black_18dp));
                }

                if (message.getStatus().equals("unread")) {
                    ((SentMessageViewHolder) holder).status.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_done_black_18dp));
                }


                break;
        }


    }

    public class ReceivedMessageViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_message_income)
        TextView text;

        @BindView(R.id.tv_income_date)
        TextView date;

        public ReceivedMessageViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

    public class SentMessageViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_message_outcome)
        TextView text;

        @BindView(R.id.tv_outcome_date)
        TextView date;

        @BindView(R.id.tv_message_outcome_status)
        ImageView status;

        public SentMessageViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }


    @Override
    public int getItemCount() {
        return messages.size();
    }

    public void updateDataSet(@NonNull final List<Message> messages) {
        final DiffUtilCallback diffCallback = new DiffUtilCallback(messages, this.messages);
        final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);

        this.messages.clear();
        this.messages.addAll(messages);
        diffResult.dispatchUpdatesTo(this);
    }

    public void addMessage(@NonNull final Message message) {
        messages.add(message);
        notifyItemInserted(messages.size() - 1);
    }

}
