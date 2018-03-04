package com.lounah.silkapp.data.remote;


import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.lounah.silkapp.data.model.Dialog;
import com.lounah.silkapp.data.model.Product;
import com.lounah.silkapp.data.model.Response;
import com.lounah.silkapp.data.model.User;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;

public interface Api {

    Observable<List<Dialog>> getDialogsByUserID(int id);

    Completable saveUser(@NonNull final User user);

    Single<List<Product>> getProducts(final int uid);
}
