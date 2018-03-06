package com.lounah.silkapp.ui.products.comments;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import com.lounah.silkapp.data.model.Comment;
import com.lounah.silkapp.data.model.Product;
import com.lounah.silkapp.data.model.Response;
import com.lounah.silkapp.data.repository.CommentsRepository;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class CommentsViewModel extends ViewModel {


    private final CommentsRepository repository;
    private final int id;

    private MutableLiveData<Response<List<Comment>>> comments;

    private Disposable productsDisposable;

    public CommentsViewModel(@NonNull final CommentsRepository repository, final int id) {
        this.repository = repository;
        this.id = id;
    }

    private Observable<List<Comment>> loadComments(final int id) {
        return repository.getAll(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(__ -> comments.setValue(Response.loading()));
    }

    protected LiveData<Response<List<Comment>>> getComments() {
        if (comments == null) {
            comments = new MutableLiveData<>();
            productsDisposable = loadComments(id)
                    .subscribe(data -> comments.setValue(Response.success(data)),
                            throwable -> comments.setValue(Response.error(throwable)));
        }
        return comments;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        productsDisposable.dispose();
    }
}
