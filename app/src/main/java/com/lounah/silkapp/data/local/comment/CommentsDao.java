package com.lounah.silkapp.data.local.comment;


import android.arch.persistence.room.Dao;

import com.lounah.silkapp.data.local.BaseDao;
import com.lounah.silkapp.data.model.Comment;

@Dao
public interface CommentsDao extends BaseDao<Comment> {
}
