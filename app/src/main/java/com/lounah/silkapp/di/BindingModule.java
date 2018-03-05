package com.lounah.silkapp.di;

import com.lounah.silkapp.ui.MainActivity;
import com.lounah.silkapp.ui.SplashActivity;
import com.lounah.silkapp.ui.dialogs.DialogsFragment;
import com.lounah.silkapp.ui.dialogs.DialogsModule;
import com.lounah.silkapp.ui.information.InformationFragment;
import com.lounah.silkapp.ui.login.LoginActivity;
import com.lounah.silkapp.ui.login.LoginModule;
import com.lounah.silkapp.ui.products.ProductsFragment;
import com.lounah.silkapp.ui.products.ProductsModule;
import com.lounah.silkapp.ui.products.comments.CommentsFragment;
import com.lounah.silkapp.ui.products.comments.CommentsModule;
import com.lounah.silkapp.ui.settings.SettingsFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class BindingModule {

    @FragmentScoped
    @ContributesAndroidInjector(modules = DialogsModule.class)
    abstract DialogsFragment dialogsFragment();

    @FragmentScoped
    @ContributesAndroidInjector
    abstract InformationFragment informationFragment();

    @FragmentScoped
    @ContributesAndroidInjector(modules = ProductsModule.class)
    abstract ProductsFragment productsFragment();

    @FragmentScoped
    @ContributesAndroidInjector(modules = CommentsModule.class)
    abstract CommentsFragment commentsFragment();

    @FragmentScoped
    @ContributesAndroidInjector
    abstract SettingsFragment settingsFragment();

    @ActivityScoped
    @ContributesAndroidInjector
    abstract SplashActivity splashActivity();

    @ActivityScoped
    @ContributesAndroidInjector
    abstract MainActivity mainActivity();

    @ActivityScoped
    @ContributesAndroidInjector(modules = LoginModule.class)
    abstract LoginActivity loginActivity();

}
