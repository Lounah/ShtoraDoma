package com.lounah.silkapp.data.model;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.Objects;

@Entity(tableName = "dialogs")
public class Dialog {

    @NonNull
    @PrimaryKey
    private String id;
    private String lastMessage;
    private String lastMessageAuthor;
    private String date;

    public Dialog(String id, String lastMessage, String lastMessageAuthor, String date) {
        this.id = id;
        this.lastMessage = lastMessage;
        this.lastMessageAuthor = lastMessageAuthor;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public String getLastMessageAuthor() {
        return lastMessageAuthor;
    }

    public void setLastMessageAuthor(String lastMessageAuthor) {
        this.lastMessageAuthor = lastMessageAuthor;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dialog dialog = (Dialog) o;
        return Objects.equals(id, dialog.id) &&
                Objects.equals(lastMessage, dialog.lastMessage) &&
                Objects.equals(lastMessageAuthor, dialog.lastMessageAuthor) &&
                Objects.equals(date, dialog.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, lastMessage, lastMessageAuthor, date);
    }

}
