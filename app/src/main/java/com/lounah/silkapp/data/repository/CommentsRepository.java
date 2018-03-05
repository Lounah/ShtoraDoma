package com.lounah.silkapp.data.repository;

import android.support.annotation.NonNull;

import com.lounah.silkapp.data.local.comment.CommentsDao;
import com.lounah.silkapp.data.local.product.ProductsDao;
import com.lounah.silkapp.data.model.Comment;
import com.lounah.silkapp.data.model.Dialog;
import com.lounah.silkapp.data.remote.Api;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;


public class CommentsRepository implements BaseRepository<Comment> {


    private final Api api;
    private final CommentsDao dao;

    public CommentsRepository(@NonNull final Api api, @NonNull final CommentsDao dao) {
        this.api = api;
        this.dao = dao;
    }

    @Override
    public Completable add(Comment data) {
        return null;
    }

    @Override
    public Completable addAll(List<Comment> data) {
        return null;
    }

    @Override
    public Single<Comment> get(int id) {
        return null;
    }

    @Override
    public Observable<List<Comment>> getAll(int... id) {
        return Observable.concat(getFromLocal(id[0]).toObservable(), getFromRemote(id[0]));
    }

    private Single<List<Comment>> getFromLocal(int id) {
        return dao.getComments(id)
                .subscribeOn(Schedulers.io());
    }


    private Observable<List<Comment>> getFromRemote(final int id) {
        return api.getComments(id)
                .doOnNext(this::storeInDB);
    }

    private void storeInDB(@NonNull final List<Comment> comments) {
        Completable.fromAction(() -> dao.insertAll(comments))
                .subscribeOn(Schedulers.io());
    }
}
