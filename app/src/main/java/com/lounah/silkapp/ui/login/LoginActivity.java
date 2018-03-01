package com.lounah.silkapp.ui.login;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jakewharton.rxbinding2.widget.RxTextView;
import com.lounah.silkapp.R;
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

public class LoginActivity extends DaggerAppCompatActivity {

    @BindView(R.id.btn_continue)
    Button btnContinue;

    @BindView(R.id.et_username)
    EditText etUsername;

    @BindView(R.id.tv_hint)
    TextView tvHintError;

    @Inject
    SharedPreferences sharedPreferences;


    private Disposable inputObserver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        onListenUserInput();
    }

    @OnClick(R.id.btn_continue)
    public void onContinueButtonClicked() {
        final String uid = String.valueOf(UserIdGenerator.generate());
        sharedPreferences.edit().putString("uid", uid).apply();
        sharedPreferences.edit().putString("username", etUsername.getText().toString()).apply();
        onStartMainActivity();
    }

    private void onStartMainActivity() {
        Intent toMainActivity = new Intent(this, MainActivity.class);
        startActivity(toMainActivity);
        finish();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        inputObserver.dispose();
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

}
