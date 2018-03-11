package com.lounah.silkapp.ui.conversation;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import com.lounah.silkapp.data.model.Dialog;
import com.lounah.silkapp.data.model.Message;
import com.lounah.silkapp.data.model.Response;
import com.lounah.silkapp.data.repository.DialogsRepository;
import com.lounah.silkapp.data.repository.MessagesRepository;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ConversationViewModel  extends ViewModel {

    private final MessagesRepository repository;

    private MutableLiveData<Response<List<Message>>> messages;

    private final MutableLiveData<Response<Message>> input = new MutableLiveData<>();


    private Disposable messagesDisposable;

    private Disposable inputDisposable;

    private final String dialogId;

    @Inject
    public ConversationViewModel(MessagesRepository repository, final String dialogId) {
        this.repository = repository;
        this.dialogId = dialogId;
    }


    private Observable<List<Message>> loadMessages(final String dialogId) {
        return repository.getAll(dialogId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(__ -> messages.setValue(Response.loading()));
    }

    protected LiveData<Response<List<Message>>> getMessages() {
        if (messages == null) {
            messages = new MutableLiveData<>();
            messagesDisposable = loadMessages(dialogId)
                    .subscribe(data -> messages.setValue(Response.success(data)),
                            throwable -> messages.setValue(Response.error(throwable)));
        }
        return messages;
    }

   private Completable sendMessage(@NonNull final Message message) {
        return repository.add(message)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(__ -> input.setValue(Response.loading()));
   }

   LiveData<Response<Message>> getInput() {
        return input;
   }

   void postMessage(@NonNull final Message message) {
       inputDisposable = sendMessage(message)
               .subscribe(() -> {
                   input.setValue(Response.success(message));
               }, throwable -> input.setValue(Response.error(throwable)));
   }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (messagesDisposable != null)
            messagesDisposable.dispose();
        if (inputDisposable != null)
            inputDisposable.dispose();
    }

}
