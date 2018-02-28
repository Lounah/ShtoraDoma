package com.lounah.silkapp.di;

import com.lounah.silkapp.ui.SplashActivity;
import com.lounah.silkapp.ui.dialogs.DialogsFragment;
import com.lounah.silkapp.ui.dialogs.DialogsModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class BindingModule {

    @FragmentScoped
    @ContributesAndroidInjector(modules = DialogsModule.class)
    abstract DialogsFragment dialogsFragment();

    @ActivityScoped
    @ContributesAndroidInjector
    abstract SplashActivity splashActivity();

}
