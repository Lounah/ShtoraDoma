package com.lounah.silkapp.ui.conversation;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.lounah.silkapp.R;
import com.lounah.silkapp.data.model.Comment;
import com.lounah.silkapp.data.model.Message;
import com.lounah.silkapp.data.model.Response;
import com.lounah.silkapp.ui.BaseFragment;
import com.lounah.silkapp.ui.products.comments.CommentsRvAdapter;
import com.lounah.silkapp.ui.products.comments.CommentsViewModel;
import com.lounah.silkapp.ui.products.comments.CommentsViewModelFactory;
import com.squareup.picasso.Picasso;
import com.stfalcon.chatkit.commons.ImageLoader;
import com.stfalcon.chatkit.messages.MessageInput;
import com.stfalcon.chatkit.messages.MessagesList;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ConversationFragment extends BaseFragment {

    @BindView(R.id.message_input)
    MessageInput messageInput;

    @BindView(R.id.rv_messages)
    MessagesList rvMessages;


    @Inject
    ConversationViewModelFactory factory;

    @Inject
    ConversationRvAdapter adapter;


    private LinearLayoutManager linearLayoutManager;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ConversationViewModel viewModel = ViewModelProviders.of(this, factory).get(ConversationViewModel.class);
        viewModel.getMessages().observe(this, response -> {
            switch (response.getStatus()) {
                case ERROR:
                    processErrorState(response.getError());
                    break;

                case LOADING:
                    processLoadingState();
                    break;

                case SUCCESS:
                    processSuccessState(response);
                    break;
            }
        });
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvMessages.setAdapter(adapter);
        linearLayoutManager = new LinearLayoutManager(getContext());
        rvMessages.setLayoutManager(linearLayoutManager);
    }

    private void processLoadingState() {
        Toast.makeText(getContext(), "LOADING...", Toast.LENGTH_SHORT).show();
    }

    private void processErrorState(@Nullable final Throwable error) {
        Toast.makeText(getContext(), "ERROR", Toast.LENGTH_SHORT).show();
        Log.i("FIREBASE ERROR", error.getMessage());
    }

    private void processSuccessState(@NonNull final Response<List<Message>> messages) {
        Toast.makeText(getContext(), "SUCCESS" + messages.getData().size(), Toast.LENGTH_SHORT).show();
        adapter.updateDataSet(messages.getData());
    }

}
