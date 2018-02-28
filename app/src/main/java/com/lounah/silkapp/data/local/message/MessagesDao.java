package com.lounah.silkapp.data.local.message;


import android.arch.persistence.room.Dao;

import com.lounah.silkapp.data.local.BaseDao;
import com.lounah.silkapp.data.model.Comment;
import com.lounah.silkapp.data.model.Message;

@Dao
public interface MessagesDao extends BaseDao<Message> {
}
