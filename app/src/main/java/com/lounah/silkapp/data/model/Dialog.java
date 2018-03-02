package com.lounah.silkapp.data.model;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.lounah.silkapp.data.converters.TimeStampConverter;

import java.util.Date;
import java.util.Objects;

@Entity(tableName = "dialogs")
public class Dialog {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "participant_id")
    private String participant_id;

    @ColumnInfo(name = "last_message")
    private String last_message;

    @ColumnInfo(name = "last_message_author")
    private String last_message_author;

    @TypeConverters(TimeStampConverter.class)
    @ColumnInfo(name = "date")
    private Date date;

    @Ignore
    public Dialog() {}

    public Dialog(String participant_id, String last_message, String last_message_author, Date date) {
        this.participant_id = participant_id;
        this.last_message = last_message;
        this.last_message_author = last_message_author;
        this.date = date;
    }

    @NonNull
    public String getParticipant_id() {
        return participant_id;
    }

    public void setParticipant_id(@NonNull String participant_id) {
        this.participant_id = participant_id;
    }

    public String getLast_message() {
        return last_message;
    }

    public void setLast_message(String last_message) {
        this.last_message = last_message;
    }

    public String getLast_message_author() {
        return last_message_author;
    }

    public void setLast_message_author(String last_message_author) {
        this.last_message_author = last_message_author;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
