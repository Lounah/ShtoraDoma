package com.lounah.silkapp.ui.conversation;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.lounah.silkapp.data.model.Dialog;
import com.lounah.silkapp.data.model.Message;
import com.lounah.silkapp.data.model.Response;
import com.lounah.silkapp.data.repository.DialogsRepository;
import com.lounah.silkapp.data.repository.MessagesRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ConversationViewModel  extends ViewModel {

    private final MessagesRepository repository;

    private MutableLiveData<Response<List<Message>>> messages;

    private Disposable messagesDisposable;

    private final int uid;

    @Inject
    public ConversationViewModel(MessagesRepository repository, final int uid) {
        this.repository = repository;
        this.uid = uid;
    }


    private Observable<List<Message>> loadMessages(final int uid) {
        return repository.getAll(uid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(__ -> messages.setValue(Response.loading()));
    }

    protected LiveData<Response<List<Message>>> getMessages() {
        if (messages == null) {
            messages = new MutableLiveData<>();
            messagesDisposable = loadMessages(uid)
                    .subscribe(data -> messages.setValue(Response.success(data)),
                            throwable -> messages.setValue(Response.error(throwable)));
        }
        return messages;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        messagesDisposable.dispose();
    }

}
