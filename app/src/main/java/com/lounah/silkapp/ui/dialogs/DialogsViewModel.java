package com.lounah.silkapp.ui.dialogs;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.lounah.silkapp.data.model.Dialog;
import com.lounah.silkapp.data.model.Response;
import com.lounah.silkapp.data.repository.DialogsRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class DialogsViewModel extends ViewModel {


    private final DialogsRepository repository;

    private MutableLiveData<Response<List<Dialog>>> dialogs;

    private Disposable dialogsDisposable;

    private final int uid;

    @Inject
    public DialogsViewModel(DialogsRepository repository, final int uid) {
        this.repository = repository;
        this.uid = uid;
    }


    private Observable<List<Dialog>> loadDialogs(final int uid) {
        return repository.getAll(uid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(__ -> dialogs.setValue(Response.loading()));
    }

    protected LiveData<Response<List<Dialog>>> getDialogs() {
        if (dialogs == null) {
            dialogs = new MutableLiveData<>();
            dialogsDisposable = loadDialogs(uid)
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
