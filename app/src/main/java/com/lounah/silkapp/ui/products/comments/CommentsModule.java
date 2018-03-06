package com.lounah.silkapp.ui.products.comments;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.lounah.silkapp.data.local.comment.CommentsDao;
import com.lounah.silkapp.data.local.dialog.DialogsDao;
import com.lounah.silkapp.data.remote.Api;
import com.lounah.silkapp.data.repository.CommentsRepository;
import com.lounah.silkapp.data.repository.DialogsRepository;
import com.lounah.silkapp.di.FragmentScoped;
import com.lounah.silkapp.ui.ItemClickListener;
import com.lounah.silkapp.ui.dialogs.DialogsRvAdapter;
import com.lounah.silkapp.ui.dialogs.DialogsViewModelFactory;
import com.lounah.silkapp.ui.products.ProductsFragment;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public class CommentsModule {

    @Provides
    @FragmentScoped
    @Named("productId")
    int provideId(@NonNull final CommentsFragment fragment) {
        return fragment.getArguments().getInt("id");
    }


    @Provides
    @FragmentScoped
    CommentsRepository provideRepository(@NonNull final Api api, @NonNull final CommentsDao dao) {
        return new CommentsRepository(api, dao);
    }

    @Provides
    @FragmentScoped
    CommentsViewModelFactory provideCommentsViewModelFactory(@NonNull final CommentsRepository repository,
                                                           @Named("productId") final int id) {
        return new CommentsViewModelFactory(repository, id);
    }

    @Provides
    @FragmentScoped
    CommentsRvAdapter provideAdapter(@NonNull final Context context) {
        return new CommentsRvAdapter(context);
    }

}
