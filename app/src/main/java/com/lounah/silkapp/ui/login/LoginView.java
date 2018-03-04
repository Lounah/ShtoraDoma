package com.lounah.silkapp.ui.login;

import android.support.annotation.NonNull;

public interface LoginView {

    void onShowLoadingView();

    void onHideLoadingView();

    void onShowError(@NonNull final Throwable error);

}
