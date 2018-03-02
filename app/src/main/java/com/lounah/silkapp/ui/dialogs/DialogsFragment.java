package com.lounah.silkapp.ui.dialogs;

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
import com.lounah.silkapp.data.model.Dialog;
import com.lounah.silkapp.data.model.Response;
import com.lounah.silkapp.ui.BaseFragment;
import com.lounah.silkapp.ui.MainActivity;


import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DialogsFragment extends BaseFragment {

    @Inject
    DialogsViewModelFactory factory;

    @Inject
    DialogsRvAdapter adapter;

    @BindView(R.id.dialogs_toolbar)
    Toolbar toolbar;

    @BindView(R.id.rv_dialogs)
    RecyclerView rvDialogs;


    private LinearLayoutManager linearLayoutManager;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DialogsViewModel viewModel = ViewModelProviders.of(this, factory).get(DialogsViewModel.class);
        viewModel.getDialogs().observe(this, response -> {
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
        View view = inflater.inflate(R.layout.fragment_dialogs, container, false);
        ButterKnife.bind(this, view);
        ((MainActivity) getActivity()).onUpdateToolbar(toolbar);
        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvDialogs.setAdapter(adapter);
        linearLayoutManager = new LinearLayoutManager(getContext());
        rvDialogs.setLayoutManager(linearLayoutManager);
    }

    public static DialogsFragment newInstance() {
        return new DialogsFragment();
    }

    private void processLoadingState() {
        Toast.makeText(getContext(), "LOADING...", Toast.LENGTH_SHORT).show();
    }

    private void processErrorState(@Nullable final Throwable error) {
        Toast.makeText(getContext(), "ERROR", Toast.LENGTH_SHORT).show();
        Log.i("FIREBASE ERROR", error.getMessage());
    }

    private void processSuccessState(@NonNull final Response<List<Dialog>> dialogs) {
        Toast.makeText(getContext(), "SUCCESS" + dialogs.getData().size(), Toast.LENGTH_SHORT).show();
        adapter.updateDataSet(dialogs.getData());
    }

}
