package com.lounah.silkapp.data.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.Objects;

@Entity(tableName = "comments")
public class Comment {

    @NonNull
    @PrimaryKey
    private int product_id;

    private String author_username;

    private String text;


    @Ignore
    public Comment() {

    }

    public Comment(@NonNull final int product_id, String author_username, String text) {
        this.product_id = product_id;
        this.author_username = author_username;
        this.text = text;
    }

    public int getId() {
        return product_id;
    }


    @NonNull
    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(@NonNull int product_id) {
        this.product_id = product_id;
    }

    public String getAuthor_username() {
        return author_username;
    }

    public void setAuthor_username(String author_username) {
        this.author_username = author_username;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
