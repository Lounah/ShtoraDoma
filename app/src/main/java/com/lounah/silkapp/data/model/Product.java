package com.lounah.silkapp.data.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class Product {

    @NonNull
    @PrimaryKey
    private final String id;

    public Product(@NonNull final String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
