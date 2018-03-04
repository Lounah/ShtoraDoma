package com.lounah.silkapp.data.repository;


import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.lounah.silkapp.data.local.BaseDao;
import com.lounah.silkapp.data.local.dialog.DialogsDao;
import com.lounah.silkapp.data.model.Dialog;
import com.lounah.silkapp.data.model.Response;
import com.lounah.silkapp.data.remote.Api;
import com.lounah.silkapp.data.repository.BaseRepository;

import java.util.List;
import java.util.concurrent.Callable;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;

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
    public Single<Dialog> get(int id) {
        return null;
    }

    @Override
    public Observable<List<Dialog>> getAll(int... id) {
        return Observable.concat(getFromLocal(id[0]).toObservable(), getFromRemote(id[0]));
    }

    private Single<List<Dialog>> getFromLocal(final int id) {
        return dao.getByUserID(id)
                .subscribeOn(Schedulers.io());
    }

    private Observable<List<Dialog>> getFromRemote(final int id) {
        return api.getDialogsByUserID(id)
                .doOnNext(this::storeInDB);
    }

    private void storeInDB(@NonNull final List<Dialog> dialogs) {
        Completable.fromAction(() -> dao.insertAll(dialogs))
        .subscribeOn(Schedulers.io());
    }
}
