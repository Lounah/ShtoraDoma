package com.lounah.silkapp.data.local.comment;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.lounah.silkapp.data.local.BaseDao;
import com.lounah.silkapp.data.model.Comment;

import java.util.List;

import io.reactivex.Single;

@Dao
public interface CommentsDao extends BaseDao<Comment> {

    @Query("SELECT * FROM comments WHERE product_id=:id")
    Single<List<Comment>> getComments(final int id);
}
