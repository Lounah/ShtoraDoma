package com.lounah.silkapp.data.local.dialog;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.lounah.silkapp.data.local.BaseDao;
import com.lounah.silkapp.data.model.Dialog;
import com.lounah.silkapp.data.model.Response;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;

@Dao
public interface  DialogsDao extends BaseDao<Dialog> {

    @Query("SELECT * FROM dialogs WHERE participant_id=:userID")
    Single<List<Dialog>> getByUserID(String userID);

}
