package com.lounah.silkapp.ui.login;

import android.support.annotation.NonNull;

import com.lounah.silkapp.data.model.User;
import com.lounah.silkapp.data.repository.UserRepository;
import com.lounah.silkapp.ui.BasePresenter;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class LoginPresenter extends BasePresenter<LoginView> {

    private LoginView mView;

    private final UserRepository repository;

    private Disposable userDisposable;

    @Inject
    public LoginPresenter(@NonNull final UserRepository repository) {
        this.repository = repository;
    }

    public void saveUser(@NonNull final User user) {
        userDisposable = repository.add(user)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe(__ -> mView.onShowLoadingView())
            .subscribe(() -> mView.onHideLoadingView(), e -> {
                mView.onShowError(e);
                mView.onHideLoadingView();
            });
    }

    @Override
    public void setView(LoginView view) {
        mView = view;
    }

    @Override
    public void dropView() {
        mView = null;
        userDisposable.dispose();
    }
}
