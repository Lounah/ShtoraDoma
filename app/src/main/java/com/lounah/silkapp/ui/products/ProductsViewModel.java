package com.lounah.silkapp.ui.products;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import com.lounah.silkapp.data.model.Product;
import com.lounah.silkapp.data.model.Response;
import com.lounah.silkapp.data.repository.ProductsRepository;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ProductsViewModel extends ViewModel {

    private final ProductsRepository repository;
    private final int uid;

    private MutableLiveData<Response<List<Product>>> products;

    private Disposable productsDisposable;

    public ProductsViewModel(@NonNull final ProductsRepository repository, final int uid) {
        this.repository = repository;
        this.uid = uid;
    }

    private Observable<List<Product>> loadProducts(final int uid) {
        return repository.getAll(uid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(__ -> products.setValue(Response.loading()));
    }

    protected LiveData<Response<List<Product>>> getProducts() {
        if (products == null) {
            products = new MutableLiveData<>();
            productsDisposable = loadProducts(uid)
                    .subscribe(data -> products.setValue(Response.success(data)),
                            throwable -> products.setValue(Response.error(throwable)));
        }
        return products;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        productsDisposable.dispose();
    }
}
