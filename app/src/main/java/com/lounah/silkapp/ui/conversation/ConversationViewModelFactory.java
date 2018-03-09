package com.lounah.silkapp.ui.conversation;


import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.lounah.silkapp.data.repository.DialogsRepository;
import com.lounah.silkapp.data.repository.MessagesRepository;
import com.lounah.silkapp.ui.dialogs.DialogsViewModel;

import javax.inject.Inject;
import javax.inject.Named;

public class ConversationViewModelFactory implements ViewModelProvider.Factory {

    private final MessagesRepository repository;
    private final int uid;

    @Inject
    public ConversationViewModelFactory(@NonNull final MessagesRepository repository, @Named("dialogId") final int uid) {
        this.repository = repository;
        this.uid = uid;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new ConversationViewModel(repository, uid);
    }
}
