package com.lounah.silkapp.data.repository.dialogs;


import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.lounah.silkapp.data.local.BaseDao;
import com.lounah.silkapp.data.local.dialog.DialogsDao;
import com.lounah.silkapp.data.model.Dialog;
import com.lounah.silkapp.data.model.Response;
import com.lounah.silkapp.data.remote.Api;
import com.lounah.silkapp.data.repository.BaseRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;

public class DialogsRepository implements BaseRepository<Dialog> {

    private final Api api;
    private final DialogsDao dao;

    @Inject
    public DialogsRepository(@NonNull final Api api, @NonNull final DialogsDao dao) {
        this.api = api;
        this.dao = dao;
    }

    @Override
    public Completable add(Dialog data) {
        return null;
    }

    @Override
    public Completable addAll(List<Dialog> data) {
        return null;
    }

    @Override
    public Single<Dialog> get(String... args) {
        return null;
    }

    @Override
    public Flowable<List<Dialog>> getAll(String... args) {
        return Single.concatArray(getFromLocal(args[0]), getFromRemote(args[0]));
    }

    private Single<List<Dialog>> getFromLocal(String... args) {
        return dao.getByUserID(args[0]);
    }

    private Single<List<Dialog>> getFromRemote(String... args) {
        return api.getDialogsByUserID(args[0]).doOnSuccess(dao::insertAll);
    }
}
