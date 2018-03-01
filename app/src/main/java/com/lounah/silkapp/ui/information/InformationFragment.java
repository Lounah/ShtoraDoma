package com.lounah.silkapp.ui.information;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lounah.silkapp.R;
import com.lounah.silkapp.ui.BaseFragment;
import com.lounah.silkapp.ui.MainActivity;
import com.lounah.silkapp.ui.dialogs.DialogsFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InformationFragment extends BaseFragment {

    @BindView(R.id.information_toolbar)
    Toolbar toolbar;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_information, container, false);
        ButterKnife.bind(this, view);
        ((MainActivity) getActivity()).onUpdateToolbar(toolbar);
        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public static InformationFragment newInstance() {
        return new InformationFragment();
    }

}
