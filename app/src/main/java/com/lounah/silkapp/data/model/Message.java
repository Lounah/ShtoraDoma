package com.lounah.silkapp.data.model;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

@Entity
public class Message {

    @NonNull
    @PrimaryKey
    private final String id;

    public Message(@NonNull final String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
