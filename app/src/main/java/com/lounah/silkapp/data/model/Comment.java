package com.lounah.silkapp.data.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.Objects;

@Entity
public class Comment {

    @NonNull
    @PrimaryKey
    private final String id;

    public Comment(@NonNull final String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment comment = (Comment) o;
        return Objects.equals(id, comment.id);
    }

//    @Override
//    public int hashCode() {
//        return Objects.hashCode(mId, mTitle, mDescription);
//    }

}
