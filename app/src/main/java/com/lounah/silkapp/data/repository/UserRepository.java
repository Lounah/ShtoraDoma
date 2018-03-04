package com.lounah.silkapp.data.repository;


import android.support.annotation.NonNull;

import com.lounah.silkapp.data.local.users.UsersDao;
import com.lounah.silkapp.data.model.User;
import com.lounah.silkapp.data.remote.Api;
import com.lounah.silkapp.data.repository.BaseRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public class UserRepository implements BaseRepository<User> {

    private final Api api;
    private final UsersDao dao;


    @Inject
    public UserRepository(@NonNull final Api api, @NonNull final UsersDao dao) {
        this.api = api;
        this.dao = dao;
    }

    @Override
    public Completable add(User user) {
        return saveRemote(user);
    }

    @Override
    public Completable addAll(List<User> data) {
        return null;
    }

    @Override
    public Single<User> get(int id) {
        return null;
    }

    @Override
    public Observable<List<User>> getAll(int... id) {
        return null;
    }

    private Completable saveRemote(@NonNull final User user) {
        return api.saveUser(user).doOnComplete(() -> saveLocally(user));
    }


    private Completable saveLocally(@NonNull final User user) {
        return Completable.fromAction(() -> dao.insert(user))
                .subscribeOn(Schedulers.io());
    }

}
