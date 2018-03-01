package com.lounah.silkapp.ui;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import dagger.android.support.DaggerFragment;

public class BaseFragment extends DaggerFragment {

    public Navigation mFragmentNavigator;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof Navigation) {
            mFragmentNavigator = (Navigation) context;
        }
    }

    public interface Navigation {
        void pushFragment(@NonNull final Fragment fragment, @NonNull final boolean animationsAllowed);
    }

}
