package com.lounah.silkapp.di;


import android.app.Application;

import com.lounah.silkapp.DomaShtoraApplication;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {
        ApplicationModule.class,
        BindingModule.class,
        NetworkModule.class,
        PersistenceModule.class,
        AndroidSupportInjectionModule.class,
        AndroidInjectionModule.class})
public interface AppComponent extends AndroidInjector<DomaShtoraApplication> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        AppComponent.Builder application(Application application);

        AppComponent build();
    }
}