package com.lounah.silkapp.di;


import com.lounah.silkapp.data.remote.Api;
import com.lounah.silkapp.data.remote.FirebaseApi;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module class NetworkModule {
    @Provides
    @Singleton
    Api provideApi() {
        return new FirebaseApi();
    }
}
