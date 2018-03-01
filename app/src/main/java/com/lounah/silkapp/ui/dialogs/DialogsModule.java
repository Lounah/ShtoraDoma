package com.lounah.silkapp.ui.dialogs;

import android.support.annotation.NonNull;

import com.lounah.silkapp.data.local.dialog.DialogsDao;
import com.lounah.silkapp.data.remote.Api;
import com.lounah.silkapp.data.repository.dialogs.DialogsRepository;
import com.lounah.silkapp.di.FragmentScoped;

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
    DialogsViewModelFactory provideDialogsViewModelFactory(@NonNull final DialogsRepository repository) {
        return new DialogsViewModelFactory(repository);
    }

}
