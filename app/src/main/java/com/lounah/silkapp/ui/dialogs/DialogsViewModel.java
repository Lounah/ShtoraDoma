package com.lounah.silkapp.ui.dialogs;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import com.lounah.silkapp.data.model.Dialog;
import com.lounah.silkapp.data.model.Response;
import com.lounah.silkapp.data.repository.dialogs.DialogsRepository;

import org.reactivestreams.Subscription;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class DialogsViewModel extends ViewModel {


    private final DialogsRepository repository;

    private MutableLiveData<Response<List<Dialog>>> dialogs;

    private Disposable dialogsDisposable;


    @Inject
    public DialogsViewModel(DialogsRepository repository) {
        this.repository = repository;
    }


    private Flowable<List<Dialog>> loadDialogs(@NonNull final String... args) {
        return repository.getAll(args[0])
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(__ -> dialogs.setValue(Response.loading()));
    }

    protected LiveData<Response<List<Dialog>>> getDialogs() {
        if (dialogs == null) {
            dialogs = new MutableLiveData<>();
            dialogsDisposable = loadDialogs("1")
                    .subscribe(data -> dialogs.setValue(Response.success(data)),
                                throwable -> dialogs.setValue(Response.error(throwable)));
        }
        return dialogs;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        dialogsDisposable.dispose();
    }
}
