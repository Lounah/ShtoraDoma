package com.lounah.silkapp.ui;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.lounah.silkapp.R;
import com.lounah.silkapp.ui.login.LoginActivity;

import java.util.Random;

import javax.inject.Inject;
import javax.inject.Named;

import dagger.android.DaggerActivity;


public class SplashActivity extends DaggerActivity {

    @Inject
    @Named("uid")
    int userId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkIfFirstLaunch();
    }

    private void checkIfFirstLaunch() {
        Log.i("uid", userId + " ");
        if (userId == 0) {
            onStartLoginActivity();
        } else onStartMainActivity();
    }

    private void onStartLoginActivity() {
        Intent toLoginActivity = new Intent(this, LoginActivity.class);
        startActivity(toLoginActivity);
    }

    private void onStartMainActivity() {
        Intent toMainActivity = new Intent(this, MainActivity.class);
        startActivity(toMainActivity);
    }

}
