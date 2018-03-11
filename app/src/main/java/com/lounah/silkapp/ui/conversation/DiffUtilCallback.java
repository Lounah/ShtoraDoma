package com.lounah.silkapp.ui.conversation;

import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;

import com.lounah.silkapp.data.model.Message;

import java.util.List;

public class DiffUtilCallback extends DiffUtil.Callback {

    List<Message> oldMessages;
    List<Message> newMessages;

    public DiffUtilCallback(List<Message> newMessages, List<Message> oldMessages) {
        this.newMessages = newMessages;
        this.oldMessages = oldMessages;
    }

    @Override
    public int getOldListSize() {
        return oldMessages.size();
    }

    @Override
    public int getNewListSize() {
        return newMessages.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldMessages.get(oldItemPosition).getDate().getTime() == newMessages.get(newItemPosition).getDate().getTime();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldMessages.get(oldItemPosition).getText().equals(newMessages.get(newItemPosition).getText());
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        return super.getChangePayload(oldItemPosition, newItemPosition);
    }

}
