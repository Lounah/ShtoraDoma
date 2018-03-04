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
    private int participant_id;

    @ColumnInfo(name = "last_message")
    private String last_message;

    @ColumnInfo(name = "last_message_author")
    private String last_message_author;

    @ColumnInfo(name = "author_status")
    private String author_status;

    @ColumnInfo(name = "last_message_status")
    private String last_message_status;

    @ColumnInfo(name = "participant_avatar_url")
    private String participant_avatar_url;

    @TypeConverters(TimeStampConverter.class)
    @ColumnInfo(name = "date")
    private Date date;

    @Ignore
    public Dialog() {}

    public Dialog(int participant_id, String last_message, String last_message_author,
                  Date date, String author_status, String last_message_status, String participant_avatar_url) {
        this.participant_id = participant_id;
        this.last_message = last_message;
        this.last_message_author = last_message_author;
        this.author_status = author_status;
        this.last_message_status = last_message_status;
        this.participant_avatar_url = participant_avatar_url;
        this.date = date;
    }

    @NonNull
    public int getParticipant_id() {
        return participant_id;
    }

    public void setParticipant_id(@NonNull int participant_id) {
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

    public String getAuthor_status() {
        return author_status;
    }

    public void setAuthor_status(String author_status) {
        this.author_status = author_status;
    }

    public String getLast_message_status() {
        return last_message_status;
    }

    public void setLast_message_status(String last_message_status) {
        this.last_message_status = last_message_status;
    }

    public String getParticipant_avatar_url() {
        return participant_avatar_url;
    }

    public void setParticipant_avatar_url(String participant_avatar_url) {
        this.participant_avatar_url = participant_avatar_url;
    }

    @Override
    public String toString() {
        return participant_id + " " + last_message + " " + last_message_author + " "
                + last_message_status + " " + participant_avatar_url + " " + date +
                " " + author_status;
    }
}
