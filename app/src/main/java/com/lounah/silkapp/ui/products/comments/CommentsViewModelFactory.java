package com.lounah.silkapp.ui.products.comments;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.lounah.silkapp.data.repository.CommentsRepository;
import com.lounah.silkapp.data.repository.ProductsRepository;
import com.lounah.silkapp.ui.products.ProductsViewModel;

import javax.inject.Inject;
import javax.inject.Named;

public class CommentsViewModelFactory implements ViewModelProvider.Factory {

    private final CommentsRepository repository;
    private final int id;

    @Inject
    public CommentsViewModelFactory(@NonNull final CommentsRepository repository, final int id) {
        this.repository = repository;
        this.id = id;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new CommentsViewModel(repository, id);
    }

}
