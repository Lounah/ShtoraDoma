package com.lounah.silkapp.ui.products.comments;


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
import android.widget.Toast;

import com.lounah.silkapp.R;
import com.lounah.silkapp.data.model.Comment;
import com.lounah.silkapp.data.model.Product;
import com.lounah.silkapp.data.model.Response;
import com.lounah.silkapp.ui.BaseFragment;
import com.lounah.silkapp.ui.products.ProductsRvAdapter;
import com.lounah.silkapp.ui.products.ProductsViewModel;
import com.lounah.silkapp.ui.products.ProductsViewModelFactory;
import com.stfalcon.chatkit.messages.MessageInput;
import com.stfalcon.chatkit.messages.MessagesList;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CommentsFragment extends BaseFragment {


    @BindView(R.id.comment_input)
    MessageInput commentInput;

    @BindView(R.id.rv_comments)
    RecyclerView rvComments;

    @Inject
    CommentsViewModelFactory factory;

    @Inject
    CommentsRvAdapter adapter;

    @BindView(R.id.comments_toolbar)
    Toolbar toolbar;

    private LinearLayoutManager linearLayoutManager;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CommentsViewModel viewModel = ViewModelProviders.of(this, factory).get(CommentsViewModel.class);
        viewModel.getComments().observe(this, response -> {
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
        View view = inflater.inflate(R.layout.fragment_comments, container, false);
        ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvComments.setAdapter(adapter);
        linearLayoutManager = new LinearLayoutManager(getContext());
        rvComments.setLayoutManager(linearLayoutManager);
    }

    private void processLoadingState() {
        Toast.makeText(getContext(), "LOADING...", Toast.LENGTH_SHORT).show();
    }

    private void processErrorState(@Nullable final Throwable error) {
        Toast.makeText(getContext(), "ERROR", Toast.LENGTH_SHORT).show();
        Log.i("FIREBASE ERROR", error.getMessage());
    }

    private void processSuccessState(@NonNull final Response<List<Comment>> comments) {
        Toast.makeText(getContext(), "SUCCESS" + comments.getData().size(), Toast.LENGTH_SHORT).show();
        adapter.updateDataSet(comments.getData());
    }

}
