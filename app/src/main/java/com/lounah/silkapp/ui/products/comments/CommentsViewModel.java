package com.lounah.silkapp.ui.products.comments;


import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import com.lounah.silkapp.data.repository.CommentsRepository;

public class CommentsViewModel extends ViewModel {


    private final CommentsRepository repository;
    private final int id;

    public CommentsViewModel(@NonNull final CommentsRepository repository, final int id) {
        this.repository = repository;
        this.id = id;
    }
}
