package com.lounah.silkapp.ui.dialogs;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.lounah.silkapp.data.repository.dialogs.DialogsRepository;

import javax.inject.Inject;

public class DialogsViewModelFactory implements ViewModelProvider.Factory {

    private final DialogsRepository repository;

    @Inject
    public DialogsViewModelFactory(@NonNull final DialogsRepository repository) {
        this.repository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new DialogsViewModel(repository);
    }
}
