package com.lounah.silkapp.ui.conversation;


import android.content.Context;
import android.support.annotation.NonNull;

import com.lounah.silkapp.data.local.comment.CommentsDao;
import com.lounah.silkapp.data.local.message.MessagesDao;
import com.lounah.silkapp.data.remote.Api;
import com.lounah.silkapp.data.repository.CommentsRepository;
import com.lounah.silkapp.data.repository.MessagesRepository;
import com.lounah.silkapp.di.FragmentScoped;
import com.lounah.silkapp.ui.products.comments.CommentsFragment;
import com.lounah.silkapp.ui.products.comments.CommentsRvAdapter;
import com.lounah.silkapp.ui.products.comments.CommentsViewModelFactory;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public class ConversationModule {

    @Provides
    @FragmentScoped
    @Named("dialogId")
    String provideId(@NonNull final ConversationFragment fragment) {
        return fragment.getArguments().getString("id");
    }


    @Provides
    @FragmentScoped
    MessagesRepository provideRepository(@NonNull final Api api, @NonNull final MessagesDao dao) {
        return new MessagesRepository(api, dao);
    }

    @Provides
    @FragmentScoped
    ConversationViewModelFactory provideConversationViewModelFactory(@NonNull final MessagesRepository repository,
                                                             @Named("dialogId") final String id) {
        return new ConversationViewModelFactory(repository, id);
    }

    @Provides
    @FragmentScoped
    ConversationRvAdapter provideAdapter(@NonNull final Context context) {
        return new ConversationRvAdapter(context);
    }

}
