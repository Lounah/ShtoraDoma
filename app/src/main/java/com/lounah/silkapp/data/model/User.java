package com.lounah.silkapp.data.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "users")
public class User {

    @PrimaryKey
    private int id;

    public User() {}

    public void setId(final int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

}
