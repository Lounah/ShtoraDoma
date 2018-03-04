package com.lounah.silkapp.data.local.users;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;
import android.support.annotation.NonNull;

import com.lounah.silkapp.data.local.BaseDao;
import com.lounah.silkapp.data.model.User;

import io.reactivex.Single;

@Dao
public interface UsersDao extends BaseDao<User> {

    @Query("SELECT * FROM users WHERE id=:id")
    Single<User> getById(@NonNull final int id);
}
