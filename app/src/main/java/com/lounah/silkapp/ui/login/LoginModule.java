package com.lounah.silkapp.ui.login;


import android.support.annotation.NonNull;

import com.lounah.silkapp.data.local.dialog.DialogsDao;
import com.lounah.silkapp.data.local.users.UsersDao;
import com.lounah.silkapp.data.remote.Api;
import com.lounah.silkapp.data.repository.DialogsRepository;
import com.lounah.silkapp.data.repository.UserRepository;
import com.lounah.silkapp.di.ActivityScoped;
import com.lounah.silkapp.di.FragmentScoped;

import dagger.Module;
import dagger.Provides;

@Module
public class LoginModule {

    @Provides
    @ActivityScoped
    UserRepository provideRepository(@NonNull final Api api, @NonNull final UsersDao dao) {
        return new UserRepository(api, dao);
    }

}
