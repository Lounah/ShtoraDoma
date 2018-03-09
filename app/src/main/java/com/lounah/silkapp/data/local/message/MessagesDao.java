package com.lounah.silkapp.data.local.message;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.lounah.silkapp.data.local.BaseDao;
import com.lounah.silkapp.data.model.Comment;
import com.lounah.silkapp.data.model.Message;
import com.lounah.silkapp.data.model.Product;

import java.util.List;

import io.reactivex.Single;

@Dao
public interface MessagesDao extends BaseDao<Message> {

    @Query("SELECT * FROM messages WHERE dialog_id=:id")
    Single<List<Message>> getMessagesById(final int id);
}
