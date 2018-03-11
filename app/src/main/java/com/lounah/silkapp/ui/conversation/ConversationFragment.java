package com.lounah.silkapp.ui.conversation;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ConversationFragment extends BaseFragment {

    @BindView(R.id.message_input)
    MessageInput messageInput;

    @BindView(R.id.rv_messages)
    RecyclerView rvMessages;

    @BindView(R.id.conversation_toolbar)
    Toolbar toolbar;

    @Inject
    ConversationViewModelFactory factory;

    @Inject
    ConversationRvAdapter adapter;

    @Inject
    @NonNull
    @Named("dialogId")
    String dialogId;

    @Inject
    @Named("uid")
    int uid;

    private ConversationViewModel viewModel;


    private LinearLayoutManager linearLayoutManager;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(this, factory).get(ConversationViewModel.class);
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

        viewModel.getInput().observe(this, response -> {
            switch (response.getStatus()) {
                case ERROR:

                    break;

                case LOADING:

                    break;

                case SUCCESS:
                 //   adapter.addMessage(response.getData());
                    break;
            }
        });



    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_conversation, container, false);
        ButterKnife.bind(this, view);
        initUI();
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

    private void initUI() {
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp));
        toolbar.setNavigationOnClickListener(v -> {
            getActivity().onBackPressed();
        });
//        toolbar.setLogo(getResources().getDrawable(R.drawable.home_main));

        messageInput.setInputListener(input -> {
            final Message message = new Message();
            message.setText(input.toString());
            message.setDialogId(dialogId);
            message.setFrom_id(uid);
            message.setDate(Calendar.getInstance().getTime());
            message.setStatus("Unread");
            message.setTo_id(1);
            viewModel.postMessage(message);
            return true;
        });

    }


}
