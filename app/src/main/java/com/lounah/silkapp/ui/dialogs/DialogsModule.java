package com.lounah.silkapp.ui.dialogs;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.lounah.silkapp.data.local.dialog.DialogsDao;
import com.lounah.silkapp.data.remote.Api;
import com.lounah.silkapp.data.repository.DialogsRepository;
import com.lounah.silkapp.di.FragmentScoped;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public class DialogsModule {

    @Provides
    @FragmentScoped
    DialogsRepository provideRepository(@NonNull final Api api, @NonNull final DialogsDao dao) {
        return new DialogsRepository(api, dao);
    }

    @Provides
    @FragmentScoped
    DialogsViewModelFactory provideDialogsViewModelFactory(@NonNull final DialogsRepository repository,
                                                           @Named("uid") final int uid) {
        return new DialogsViewModelFactory(repository, uid);
    }

    @Provides
    @FragmentScoped
    DialogsRvAdapter provideAdapter(@NonNull final Context context) {
        return new DialogsRvAdapter(context);
    }

}
