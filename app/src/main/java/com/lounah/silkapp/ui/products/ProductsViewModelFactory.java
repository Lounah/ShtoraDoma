package com.lounah.silkapp.ui.products;


import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.lounah.silkapp.data.repository.DialogsRepository;
import com.lounah.silkapp.data.repository.ProductsRepository;
import com.lounah.silkapp.ui.dialogs.DialogsViewModel;

import javax.inject.Inject;
import javax.inject.Named;

public class ProductsViewModelFactory implements ViewModelProvider.Factory {

    private final ProductsRepository repository;
    private final int uid;

    @Inject
    public ProductsViewModelFactory(@NonNull final ProductsRepository repository, @Named("uid") final int uid) {
        this.repository = repository;
        this.uid = uid;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new ProductsViewModel(repository, uid);
    }
}
