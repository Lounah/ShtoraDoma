package com.lounah.silkapp.data.repository;


import android.arch.lifecycle.LiveData;

import com.lounah.silkapp.data.model.Response;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;

public interface BaseRepository<T> {

    Completable add(T data);

    Completable addAll(List<T> data);

    Single<T> get(String... args);

    Flowable<List<T>> getAll(String... args);

}
