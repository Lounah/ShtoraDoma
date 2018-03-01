package com.lounah.silkapp.di;


import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module abstract class ApplicationModule {

    private static final String PREFERENCE_KEY = "AppData";
    private static final String USER_ID_KEY = "uid";

    @Singleton
    @Provides
    static SharedPreferences provideSharedPrefs(Context context) {
        return context.getSharedPreferences(PREFERENCE_KEY, Context.MODE_PRIVATE);
    }

    @Singleton
    @Provides
    static SharedPreferences.Editor provideSharedPrefsEditor(SharedPreferences sharedPreferences) {
        return sharedPreferences.edit();
    }

    @Nullable
    @Singleton
    @Provides
    @Named("uid")
    static String provideUserID(SharedPreferences sharedPreferences) {
        return sharedPreferences.getString(USER_ID_KEY, null);
    }

    @Binds
    abstract Context bindContext(Application application);

}
