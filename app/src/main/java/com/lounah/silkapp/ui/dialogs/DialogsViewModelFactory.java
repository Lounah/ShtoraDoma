package com.lounah.silkapp.ui.dialogs;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.lounah.silkapp.data.repository.DialogsRepository;

import javax.inject.Inject;
import javax.inject.Named;

public class DialogsViewModelFactory implements ViewModelProvider.Factory {

    private final DialogsRepository repository;
    private final int uid;

    @Inject
    public DialogsViewModelFactory(@NonNull final DialogsRepository repository, @Named("uid") final int uid) {
        this.repository = repository;
        this.uid = uid;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new DialogsViewModel(repository, uid);
    }
}
