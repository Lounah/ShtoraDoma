package com.lounah.silkapp.di;


import android.app.Application;
import android.arch.persistence.room.Room;

import com.lounah.silkapp.data.local.AppDatabase;
import com.lounah.silkapp.data.local.comment.CommentsDao;
import com.lounah.silkapp.data.local.dialog.DialogsDao;
import com.lounah.silkapp.data.local.message.MessagesDao;
import com.lounah.silkapp.data.local.product.ProductsDao;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module class PersistenceModule {

    @Singleton
    @Provides
    AppDatabase provideRoomDatabase(Application mApplication) {
        return Room.databaseBuilder(mApplication, AppDatabase.class, "app-db").fallbackToDestructiveMigration().build();
    }

    @Singleton
    @Provides
    MessagesDao provideMessagesDao(AppDatabase appDatabase) {
        return appDatabase.messagesDao();
    }

    @Singleton
    @Provides
    DialogsDao provideDialogsDao(AppDatabase appDatabase) {
        return appDatabase.dialogsDao();
    }

    @Singleton
    @Provides
    CommentsDao provideCommentsDao(AppDatabase appDatabase) {
        return appDatabase.commentsDao();
    }

    @Singleton
    @Provides
    ProductsDao provideProductsDao(AppDatabase appDatabase) {
        return appDatabase.productsDao();
    }


}
