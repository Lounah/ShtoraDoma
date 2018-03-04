package com.lounah.silkapp.ui.login;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jakewharton.rxbinding2.widget.RxTextView;
import com.lounah.silkapp.R;
import com.lounah.silkapp.data.model.User;
import com.lounah.silkapp.ui.MainActivity;
import com.lounah.silkapp.util.UserIdGenerator;
import com.lounah.silkapp.util.UsernameInputReviewer;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.support.DaggerAppCompatActivity;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

public class LoginActivity extends DaggerAppCompatActivity implements LoginView {

    @BindView(R.id.btn_continue)
    Button btnContinue;

    @BindView(R.id.et_username)
    EditText etUsername;

    @BindView(R.id.tv_hint)
    TextView tvHintError;

    @Inject
    SharedPreferences sharedPreferences;

    @Inject
    LoginPresenter presenter;


    private Disposable inputObserver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        presenter.setView(this);
        onListenUserInput();
    }

    @OnClick(R.id.btn_continue)
    public void onContinueButtonClicked() {
        if (hasNetworkConnection()) {
            final int uid = UserIdGenerator.generate();
            saveUser(uid, etUsername.getText().toString());
            final User user = new User(uid, etUsername.getText().toString(), null, "Online");
            presenter.saveUser(user);
        } else showNoConnectionToast();
    }

    @Override
    public void onStartMainActivity() {
        Intent toMainActivity = new Intent(this, MainActivity.class);
        startActivity(toMainActivity);
        finish();
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.setView(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        inputObserver.dispose();
        presenter.dropView();
    }

    private void onListenUserInput() {
        inputObserver = RxTextView.textChanges(etUsername)
                .subscribe(this::processUserInput);

    }

    private void processUserInput(@NonNull final CharSequence input) {
        if (UsernameInputReviewer.review(input)) {
            btnContinue.setEnabled(true);
            tvHintError.setVisibility(View.GONE);
        }
        else {
            btnContinue.setEnabled(false);
            if ((tvHintError.getVisibility() != View.VISIBLE) && (input.length() != 0))
                tvHintError.setVisibility(View.VISIBLE);
        }
    }

    private void saveUser(final int uid, @NonNull final String username) {
        sharedPreferences.edit().putInt("id", uid).apply();
        sharedPreferences.edit().putString("username", username).apply();
    }

    @Override
    public void onShowLoadingView() {

    }

    @Override
    public void onHideLoadingView() {

    }

    @Override
    public void onShowError(@NonNull Throwable error) {
        Toast.makeText(this, "ПРОИЗОШЛА ОШБИКА", Toast.LENGTH_SHORT).show();
        error.printStackTrace();
    }

    private void showNoConnectionToast() {
        Toast.makeText(this, "Произошла ошибка. Проверьте подключение к сети", Toast.LENGTH_SHORT).show();
    }

    private boolean hasNetworkConnection() {
        final ConnectivityManager conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo activeNetwork = conMgr.getActiveNetworkInfo();
        return (activeNetwork != null && activeNetwork.isConnected());
    }

}
