package com.lounah.silkapp.data.model;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverter;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.lounah.silkapp.data.converters.TimeStampConverter;
import com.stfalcon.chatkit.commons.models.IMessage;
import com.stfalcon.chatkit.commons.models.IUser;

import java.util.Date;
import java.util.Objects;

@Entity(tableName = "messages")
public class Message {


    private int from_id;

    private int to_id;

    private String text;

    private String dialogId;

    @NonNull
    @PrimaryKey
    @TypeConverters(TimeStampConverter.class)
    private Date date;

    private String status;

    @Ignore
    public Message() {}

    public Message(int from_id, int to_id, String text, Date date, String status,
                   String dialogId) {
        this.from_id = from_id;
        this.to_id = to_id;
        this.text = text;
        this.date = date;
        this.status = status;
        this.dialogId = dialogId;
    }

    public int getFrom_id() {
        return from_id;
    }

    public int getTo_id() {
        return to_id;
    }

    public String getText() {
        return text;
    }

    public Date getDate() {
        return date;
    }

    public String getStatus() {
        return status;
    }

    public String getDialogId() {
        return dialogId;
    }

    public void setDialogId(String dialogId) {
        this.dialogId = dialogId;
    }

    public void setFrom_id(int from_id) {
        this.from_id = from_id;
    }

    public void setTo_id(int to_id) {
        this.to_id = to_id;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setDate(@NonNull Date date) {
        this.date = date;
    }

    public void setStatus(String status) {
        this.status = status;
    }


}


