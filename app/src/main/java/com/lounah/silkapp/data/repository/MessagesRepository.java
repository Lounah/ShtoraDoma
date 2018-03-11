package com.lounah.silkapp.data.repository;

import android.support.annotation.NonNull;

import com.lounah.silkapp.data.local.message.MessagesDao;
import com.lounah.silkapp.data.local.product.ProductsDao;
import com.lounah.silkapp.data.model.Dialog;
import com.lounah.silkapp.data.model.Message;
import com.lounah.silkapp.data.remote.Api;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;


public class MessagesRepository implements BaseRepository<Message> {

    private final Api api;
    private final MessagesDao dao;

    public MessagesRepository(@NonNull final Api api, @NonNull final MessagesDao dao) {
        this.api = api;
        this.dao = dao;
    }

    @Override
    public Completable add(Message data) {
        return api.sendMessage(data).doOnComplete(() -> storeInDB(data));
    }

    @Override
    public Completable addAll(List<Message> data) {
        return null;
    }

    @Override
    public Single<Message> get(int id) {
        return null;
    }

    @Override
    public Observable<List<Message>> getAll(int... id) {
        return null;
    }

    public Observable<List<Message>> getAll(String... id) {
        return Observable.concat(getFromLocal(id[0]).toObservable(), getFromRemote(id[0]));
    }

    private Single<List<Message>> getFromLocal(final String id) {
        return dao.getMessagesById(id)
                .subscribeOn(Schedulers.io());
    }

    private Observable<List<Message>> getFromRemote(final String id) {
        return api.getMessagesByDialogId(id)
                .doOnNext(this::storeInDB);
    }

    private void storeInDB(@NonNull final List<Message> messages) {
        Completable.fromAction(() -> dao.insertAll(messages))
                .subscribeOn(Schedulers.io());
    }

    private void storeInDB(@NonNull final Message message) {
        Completable.fromAction(() -> dao.insert(message))
                .subscribeOn(Schedulers.io());
    }
}
