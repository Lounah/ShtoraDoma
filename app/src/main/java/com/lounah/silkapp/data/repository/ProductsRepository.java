package com.lounah.silkapp.data.repository;

import android.support.annotation.NonNull;

import com.lounah.silkapp.data.local.product.ProductsDao;
import com.lounah.silkapp.data.model.Dialog;
import com.lounah.silkapp.data.model.Product;
import com.lounah.silkapp.data.remote.Api;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public class ProductsRepository implements BaseRepository<Product> {

    private final Api api;
    private final ProductsDao dao;

    public ProductsRepository(@NonNull final Api api, @NonNull final ProductsDao dao) {
        this.api = api;
        this.dao = dao;
    }

    @Override
    public Completable add(Product data) {
        return null;
    }

    @Override
    public Completable addAll(List<Product> data) {
        return null;
    }

    @Override
    public Single<Product> get(int id) {
        return null;
    }

    @Override
    public Observable<List<Product>> getAll(int... id) {
        return Single.concat(getFromLocal(), getFromRemote(id[0])).toObservable();
    }

    private Single<List<Product>> getFromLocal() {
        return dao.getProducts()
                .subscribeOn(Schedulers.io());
    }

    private Single<List<Product>> getFromRemote(final int id) {
        return api.getProducts(id)
                .doOnSuccess(this::storeInDB);
    }

    private void storeInDB(@NonNull final List<Product> products) {
        Completable.fromAction(() -> dao.insertAll(products))
                .subscribeOn(Schedulers.io());
    }
}
